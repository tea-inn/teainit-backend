package top.teainn.project.aop.log.login;

import top.teainn.project.common.BaseResponse;
import top.teainn.project.exception.BusinessException;
import top.teainn.project.model.dto.log.city.AdInfoData;
import top.teainn.project.model.dto.log.city.IpToAddressDto;
import top.teainn.project.model.dto.log.city.ResultData;
import top.teainn.project.model.dto.user.UserLoginRequest;
import top.teainn.project.model.entity.LoginLog;
import top.teainn.project.util.IPUtil;
import top.teainn.project.util.IpToAddressUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志切面
 * ①切面注解得到请求数据 -> ②发布监听事件 -> ③异步监听日志入库
 *
 * @author teainn
 * @date 2024/01/14 11:50
 */
@Slf4j
@Aspect
@Component
public class LoginLogAspect {


    private LoginLog loginLog = new LoginLog();
    /**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    @Autowired
    private ApplicationContext applicationContext;

    /***
     * 定义controller切入点拦截规则，拦截SysLog注解的方法
     */
    @Pointcut("@annotation(top.teainn.project.annotation.LoginLog)")
    public void loginLogAspect() {

    }

    /***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("loginLogAspect()")
    public void recordLog(JoinPoint joinPoint) throws Throwable {
        // 获取 request
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取 ip
        String ipAddr = IPUtil.getIpAddr(request);
        loginLog.setIp(ipAddr);
        // 获取 ip 所在城市地址
        IpToAddressDto addressDto = IpToAddressUtil.getCityInfo(ipAddr);
        String addressStr = "";
        if (addressDto.getStatus() == 0) {
            ResultData result = addressDto.getResult();
            AdInfoData adInfo = result.getAd_info();
            String nation = adInfo.getNation();
            String city = adInfo.getCity();
            String province = adInfo.getProvince();
            String district = adInfo.getDistrict();
            StringBuilder sb = new StringBuilder();
            sb.append(nation);
            sb.append("-");
            sb.append(province);
            sb.append(city);
            sb.append(district);
            addressStr = sb.toString();
        }
        if (StringUtils.isBlank(addressStr)) {
            loginLog.setLoginAddress("未知");
        } else {
            loginLog.setLoginAddress(addressStr);
        }
        // 获取浏览器信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        Browser browser = userAgent.getBrowser();
        String browserName = browser.getName();
        // 获取操作系统
        OperatingSystem os = userAgent.getOperatingSystem();
        String osName = os.getName();
        // 获取请求参数
        Object arg = joinPoint.getArgs()[0];
        UserLoginRequest userLoginRequest = (UserLoginRequest)arg;
        loginLog.setBrowser(browserName);
        loginLog.setOperSystem(osName);
        loginLog.setLoginAccount(userLoginRequest.getUserAccount());
    }

    /**
     * 返回通知
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "res", pointcut = "loginLogAspect()")
    public void doAfterReturning(BaseResponse res) {
        if (res.getErrCode() == 0) {
            loginLog.setLoginState(0);
            loginLog.setLoginInfo("登录成功");
        } else {
            loginLog.setLoginState(1);
            loginLog.setLoginInfo("登录失败");
        }
        // 发布事件
        applicationContext.publishEvent(new LoginLogEvent(loginLog));
        loginLog = new LoginLog();
    }

    /**
     * 异常通知
     * @param e
     */
    @AfterThrowing(pointcut = "loginLogAspect()",throwing = "e")
    public void doAfterThrowable(Throwable e){
        BusinessException be = (BusinessException) e;
        loginLog.setLoginState(1);
        if (StringUtils.isNotBlank(be.getErrMsg())) {
            loginLog.setLoginInfo(be.getErrMsg());
        }
        applicationContext.publishEvent(new LoginLogEvent(loginLog));
        loginLog = new LoginLog();
    }

}
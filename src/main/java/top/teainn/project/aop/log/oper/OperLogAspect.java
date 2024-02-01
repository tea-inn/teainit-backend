package top.teainn.project.aop.log.oper;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.exception.BusinessException;
import top.teainn.project.model.dto.log.city.AdInfoData;
import top.teainn.project.model.dto.log.city.IpToAddressDto;
import top.teainn.project.model.dto.log.city.ResultData;
import top.teainn.project.model.entity.OperLog;
import top.teainn.project.model.entity.User;
import top.teainn.project.service.system.UserService;
import top.teainn.project.util.IPUtil;
import top.teainn.project.util.IpToAddressUtil;

import javax.annotation.Resource;
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
public class OperLogAspect {


    private OperLog operLog = new OperLog();
    @Resource
    private UserService userService;

    /**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    @Resource
    private ApplicationContext applicationContext;

    /***
     * 定义controller切入点拦截规则，拦截SysLog注解的方法
     */
    @Pointcut("@annotation(top.teainn.project.annotation.OperLog)")
    public void operLogAspect() {

    }

    /***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("operLogAspect() && @annotation(operLogAnnotation)")
    public void recordLog(JoinPoint joinPoint, top.teainn.project.annotation.OperLog operLogAnnotation) throws Throwable {

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        System.out.println("监听开始");

        // 从 sa-token 中拿到当前登录 id
        Object loginIdObj = StpUtil.getLoginId();
        String loginIdStr = ObjUtil.toString(loginIdObj);
        Long loginId = Long.valueOf(loginIdStr);
        operLog.setUserId(loginId);
        // 查找用户信息
        User loginUser = userService.getById(loginId);
        operLog.setUserAccount(loginUser.getUserAccount());
        String module = operLogAnnotation.module();
        Integer type = operLogAnnotation.type().getValue();
        operLog.setOperModule(module);
        operLog.setOperType(type);
        String ipAddr = IPUtil.getIpAddr(request);
        operLog.setIp(ipAddr);
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
            operLog.setOperAddress("未知");
        } else {
            operLog.setOperAddress(addressStr);
        }
        // 获取请求方法
        String method = request.getMethod();
        // 获取请求路径
        StringBuffer requestURL = request.getRequestURL();
        String url = requestURL.substring(requestURL.indexOf("/api/"));
        // 获取请求参数
        Gson gson = new Gson();
        if (joinPoint.getArgs() != null) {
            // 有两个值时第一个值为请求参数，第二值为 request
            if (joinPoint.getArgs().length == 2) {
                Object arg = joinPoint.getArgs()[0];
                String paramJson = gson.toJson(arg);
                if (StringUtils.isNotBlank(paramJson)) {
                    operLog.setRequestParam(paramJson);
                }
            }

        }
        operLog.setOperMethod(method);
        operLog.setOperUrl(url);
        // 导入导出直接用 response，没有返回的数据，无法到doAfter，也就不能保存
        // 这里判断为导入导出，就发布事件
        if (type == 4 || type == 5) {
            operLog.setOperState(0);
            // 发布事件
            applicationContext.publishEvent(new OperLogEvent(operLog));
            operLog = new OperLog();
        }
    }

    /**
     * 返回通知
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "res", pointcut = "operLogAspect()")
    public void doAfterReturning(BaseResponse res) {
        System.out.println("监听完成");
        Gson gson = new Gson();
        String json = gson.toJson(res);
        operLog.setResponseInfo(json);
        if (res.getErrCode() == 0) {
            operLog.setOperState(0);
        } else {
            operLog.setOperState(1);
        }
        // 发布事件
        applicationContext.publishEvent(new OperLogEvent(operLog));
        operLog = new OperLog();
    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(pointcut = "operLogAspect()", throwing = "e")
    public void doAfterThrowable(Throwable e) {
        operLog.setOperState(1);
        BusinessException be = (BusinessException) e;
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(be.getMessage())) {
            sb.append(be.getMessage());
        }
        if (StringUtils.isNotBlank(be.getErrMsg())) {
            sb.append(" ( ");
            sb.append(be.getErrMsg());
            sb.append(" ) ");
        }
        operLog.setErrorInfo(sb.toString());
        applicationContext.publishEvent(new OperLogEvent(operLog));
        operLog = new OperLog();
    }

}
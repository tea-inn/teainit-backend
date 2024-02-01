package top.teainn.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.common.IdRequest;
import top.teainn.project.common.ResultUtils;
import top.teainn.project.constant.CommonConstant;
import top.teainn.project.exception.BusinessException;
import top.teainn.project.model.dto.log.LoginLogQueryRequest;
import top.teainn.project.model.dto.log.OperLogQueryRequest;
import top.teainn.project.model.entity.LoginLog;
import top.teainn.project.model.entity.OperLog;
import top.teainn.project.service.system.LoginLogService;
import top.teainn.project.service.system.OperLogService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 日志控制器
 *
 * @author teainn
 * @date 2024/01/28 23:15
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {

    @Resource
    private OperLogService operLogService;

    @Resource
    private LoginLogService loginLogService;

    // region 操作日志

    /**
     * 分页获取操作日志
     *
     * @param logQueryRequest 查询请求
     * @return BaseResponse<Page < OperLog>>
     */
    @SaCheckPermission("system:oper:list")
    @GetMapping("/page/oper")
    public BaseResponse<Page<OperLog>> pageOperLog(@Validated OperLogQueryRequest logQueryRequest) {
        OperLog operLog = new OperLog();
        BeanUtils.copyProperties(logQueryRequest, operLog);
        long current = logQueryRequest.getCurrent();
        long size = logQueryRequest.getPageSize();
        String sortField = logQueryRequest.getSortField();
        String sortOrder = logQueryRequest.getSortOrder();

        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR);
        }

        String operModule = operLog.getOperModule();
        Integer operType = operLog.getOperType();
        Integer operState = operLog.getOperState();
        String ip = operLog.getIp();
        String operAddress = operLog.getOperAddress();
        String userAccount = operLog.getUserAccount();
        Date startTime = logQueryRequest.getStartTime();
        Date endTime = logQueryRequest.getEndTime();

        QueryWrapper<OperLog> queryWrapper = new QueryWrapper<>();
        if (startTime != null && endTime != null) {
            queryWrapper.gt("createTime", startTime);
            queryWrapper.lt("createTime", endTime);
        }
        queryWrapper.eq(StringUtils.isNotBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StringUtils.isNotBlank(operModule), "operModule", operModule);
        queryWrapper.like(ObjectUtils.isNotEmpty(operType), "operType", operType);
        queryWrapper.like(StringUtils.isNotBlank(operAddress), "operAddress", operAddress);
        queryWrapper.eq(ObjectUtils.isNotEmpty(operState), "operState", operState);
        queryWrapper.eq(ObjectUtils.isNotEmpty(ip), "ip", ip);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_DESC), sortField);

        Page<OperLog> logPage = operLogService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(logPage);
    }

    //

    /**
     * 获取操作日志详情
     *
     * @param idRequest id
     * @return BaseResponse<OperLog>
     */
    @GetMapping("/get/oper")
    public BaseResponse<OperLog> getOperLogById(@Validated IdRequest idRequest) {
        OperLog operLog = operLogService.getById(idRequest.getId());
        return ResultUtils.success(operLog);
    }

    // endregion

    // region 登录日志

    /**
     * 分页获取登录日志
     *
     * @param logQueryRequest 查询信息
     * @return BaseResponse<Page < LoginLog>>
     */
    @SaCheckPermission("system:login:list")
    @GetMapping("/page/login")
    public BaseResponse<Page<LoginLog>> pageLoginLog(@Validated LoginLogQueryRequest logQueryRequest) {
        LoginLog loginLog = new LoginLog();
        BeanUtils.copyProperties(logQueryRequest, loginLog);
        long current = logQueryRequest.getCurrent();
        long size = logQueryRequest.getPageSize();
        String sortField = logQueryRequest.getSortField();
        String sortOrder = logQueryRequest.getSortOrder();

        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR);
        }

        Date startTime = logQueryRequest.getStartTime();
        Date endTime = logQueryRequest.getEndTime();
        String loginAddress = loginLog.getLoginAddress();
        String loginAccount = loginLog.getLoginAccount();
        String ip = loginLog.getIp();
        Integer loginState = loginLog.getLoginState();

        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        if (startTime != null && endTime != null) {
            queryWrapper.gt("createTime", startTime);
            queryWrapper.lt("createTime", endTime);
        }
        queryWrapper.eq(StringUtils.isNotBlank(loginAccount), "loginAccount", loginAccount);
        queryWrapper.like(StringUtils.isNotBlank(loginAddress), "loginAddress", loginAddress);
        queryWrapper.eq(ObjectUtils.isNotEmpty(loginState), "loginState", loginState);
        queryWrapper.eq(ObjectUtils.isNotEmpty(ip), "ip", ip);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_DESC), sortField);

        Page<LoginLog> logPage = loginLogService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(logPage);
    }

    // endregion

}

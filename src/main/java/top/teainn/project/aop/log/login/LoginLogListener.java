package top.teainn.project.aop.log.login;


import top.teainn.project.model.entity.LoginLog;
import top.teainn.project.service.system.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 注解形式的监听，异步监听日志事件
 *
 * @author teainn
 * @date 2024/01/14 11:49
 */
@Slf4j
@Component
public class LoginLogListener {

    @Autowired
    private LoginLogService loginLogService;

    @Async
    @Order
    @EventListener(LoginLogEvent.class)
    public void saveOperLog(LoginLogEvent event) {
        LoginLog loginLog = (LoginLog) event.getSource();
        // 保存日志
        loginLogService.save(loginLog);
    }
}
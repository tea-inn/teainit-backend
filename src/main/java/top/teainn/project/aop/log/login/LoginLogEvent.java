package top.teainn.project.aop.log.login;

import top.teainn.project.model.entity.LoginLog;
import org.springframework.context.ApplicationEvent;

public class LoginLogEvent extends ApplicationEvent {

    public LoginLogEvent(LoginLog source) {
        super(source);
    }
}

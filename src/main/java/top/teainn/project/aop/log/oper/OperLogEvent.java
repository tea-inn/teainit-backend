package top.teainn.project.aop.log.oper;

import top.teainn.project.model.entity.OperLog;
import org.springframework.context.ApplicationEvent;

public class OperLogEvent extends ApplicationEvent {

    public OperLogEvent(OperLog source) {
        super(source);
    }
}

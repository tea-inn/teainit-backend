package top.teainn.project.aop.log.oper;


import top.teainn.project.model.entity.OperLog;
import top.teainn.project.service.system.OperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author teainn
 */
@Slf4j
@Component
public class OperLogListener {

    @Autowired
    private OperLogService operLogService;

    @Async
    @Order
    @EventListener(OperLogEvent.class)
    public void saveOperLog(OperLogEvent event) {
        OperLog operLog = (OperLog) event.getSource();
        // 保存日志
        operLogService.save(operLog);
    }
}
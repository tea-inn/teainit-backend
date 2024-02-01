package top.teainn.project.model.common;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * redis 数据包装类
 *
 * @author teainn
 * @date 2023/11/03 15:34
 * @version V1.0.0
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
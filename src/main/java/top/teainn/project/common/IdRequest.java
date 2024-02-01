package top.teainn.project.common;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * id 请求封装类
 *
 * @author teainn
 * @date 2024/01/10 21:30
 */
@Data
public class IdRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Min(value = 1, message = "id 不符合要求")
    @NotNull(message = "id 不能为空")
    private Long id;

}
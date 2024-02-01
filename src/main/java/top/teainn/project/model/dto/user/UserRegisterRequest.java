package top.teainn.project.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author teainn
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotNull(message = "不允许为空")
    private String userAccount;

    private String userPassword;

    private String checkPassword;
}

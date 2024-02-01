package top.teainn.project.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author teainn
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @Length(max = 30,message = "账号不符合要求")
    @NotBlank(message = "账号不符合要求")
    private String userAccount;

    @Length(max = 30,message = "密码不符合要求")
    @NotBlank(message = "密码不符合要求")
    private String userPassword;
}

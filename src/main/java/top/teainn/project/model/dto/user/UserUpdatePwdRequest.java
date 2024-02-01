package top.teainn.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author teainn
 */
@Data
public class UserUpdatePwdRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long userId;

    private String oldUserPassword;

    private String newUserPassword;

    private String checkPassword;
}

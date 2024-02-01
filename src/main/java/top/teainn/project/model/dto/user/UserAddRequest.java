package top.teainn.project.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 用户创建请求
 *
 * @author teainn
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    @Length(max = 12, message = "用户名过长")
    private String userName;

    /**
     * 角色 id 列表
     */
    @NotNull(message = "角色不允许为空")
    private List<Long> roleIdList;

    /**
     * 账号
     */
    @Length(min = 5,max = 10, message = "账号长度在 5 - 10 ")
//    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "账号只允许小写英文或数字")
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 密码
     */
    private String userPassword;

    private static final long serialVersionUID = 1L;
}
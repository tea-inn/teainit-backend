package top.teainn.project.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 用户更新请求
 *
 * @author teainn
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * 用户 id
     */
    @NotNull(message = "id 不允许为空")
    private Long id;

    /**
     * 角色列表
     */
    @NotNull(message = "角色列表不允许为空")
    private List<Long> roleIdList;

    /**
     * 用户昵称
     */
    @Length(max = 12, message = "用户名过长")
    private String userName;

    /**
     * 账号
     */
    @Length(min = 5,max = 20, message = "账号长度在 5 - 10 ")
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
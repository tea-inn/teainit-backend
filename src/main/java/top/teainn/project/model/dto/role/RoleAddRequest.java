package top.teainn.project.model.dto.role;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName role
 */
@Data
public class RoleAddRequest implements Serializable {

    /**
     * 角色名称
     */
    @Length(max = 50,message = "角色名称不符合要求")
    @NotEmpty(message = "角色名称不允许为空")
    private String roleName;

    /**
     * 菜单id（该角色拥有菜单权限）
     */
    @NotNull(message = "角色权限不能为空")
    private List<Long> menuIdList;

}
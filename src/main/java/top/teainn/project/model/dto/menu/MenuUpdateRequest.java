package top.teainn.project.model.dto.menu;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 
 * @TableName menu
 */
@Data
public class MenuUpdateRequest implements Serializable {
    /**
     * 菜单 id
     */
    private Long id;

    /**
     * 父级 id，默认为 0
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    @Length(max = 20,message = "菜单名称不符合要求")
    @NotEmpty(message = "菜单名称不允许为空")
    private String menuName;

    /**
     * 排序号
     */
    @NotNull(message = "排序号不符合要求")
    private Integer sortNum;

    /**
     * 类型
     */
    @NotNull(message = "类型不符合要求")
    private Integer type;

    /**
     * 权限
     */
    @Length(max = 30,message = "权限不符合要求")
    private String perms;
}
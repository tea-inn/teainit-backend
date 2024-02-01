package top.teainn.project.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树 - 下拉菜单数据展示
 *
 * @author teainn
 * @date 2024/01/27 23:16
 */
@Data
public class MenuTreeSelectVO implements Serializable {
    /**
     * 菜单 id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 子菜单
     */
    private List<MenuTreeSelectVO> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
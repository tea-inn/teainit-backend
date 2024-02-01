package top.teainn.project.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单树 - 全部菜单数据展示
 *
 * @author teainn
 * @date 2024/01/27 23:16
 */
@Data
public class MenuTreeVO implements Serializable {
    /**
     * 菜单 id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 权限
     */
    private String perms;

    /**
     * 排序号
     */
    private Integer sortNum;

    /**
     * 类别
     */
    private Integer type;

    /**
     * 创建时间（查寝时间）
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 子菜单
     */
    private List<MenuTreeVO> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
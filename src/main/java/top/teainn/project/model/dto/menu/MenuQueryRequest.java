package top.teainn.project.model.dto.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import top.teainn.project.common.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author teainn
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuQueryRequest extends PageRequest implements Serializable {

    /**
     * 菜单名称
     */
    @Length(max = 20,message = "菜单名称不符合要求")
    private String menuName;

    /**
     * 权限
     */
    @Length(max = 30,message = "权限不符合要求")
    private String perms;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 起始时间
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    private static final long serialVersionUID = 1L;
}
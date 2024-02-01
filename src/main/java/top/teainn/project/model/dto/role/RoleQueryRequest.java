package top.teainn.project.model.dto.role;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RoleQueryRequest extends PageRequest implements Serializable {
    /**
     * 角色名称
     */
    @Length(max = 50,message = "角色名称不符合要求")
    private String roleName;

    /**
     * 起始时间
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    private static final long serialVersionUID = 1L;
}
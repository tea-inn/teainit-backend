package top.teainn.project.model.dto.log;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import top.teainn.project.common.PageRequest;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author teainn
 */
@Data
public class OperLogQueryRequest extends PageRequest implements Serializable {

    /**
     * 操作账号
     */
    private String userAccount;

    /**
     * 操作模块
     */
    private String operModule;

    /**
     * 操作类型
     */
    private Integer operType;

    /**
     * 操作状态（0-成功 1-失败）
     */
    private Integer operState;

    /**
     * 操作地址
     */
    private String ip;

    /**
     * 操作地点
     */
    private String operAddress;


    /**
     * 查询创建时间，起始
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 查询创建时间，末
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package top.teainn.project.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * @TableName oper_log
 */
@Data
public class OperLogVO implements Serializable {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 id
     */
    private Long userIdo;

    /**
     * 操作人
     */
    private String userAccount;

    /**
     * 操作模块
     */
    private String operModule;

    /**
     * 操作类型
     */
    private String operType;

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
     * 操作方法
     */
    private String operMethod;

    /**
     * 操作路径
     */
    private String operUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回信息
     */
    private String responseInfo;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 创建时间
     */
    private Date createTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
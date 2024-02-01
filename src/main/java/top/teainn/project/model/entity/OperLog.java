package top.teainn.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * @TableName oper_log
 */
@TableName(value ="oper_log")
@Data
public class OperLog implements Serializable {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作人
     */
    private Long userId;


    /**
     * 操作账号
     */
    private String userAccount;

    /**
     * 操作模块
     */
    private String operModule;

    /**
     * 操作类型（0-其他，1-新增，2-修改，3-删除，4-导入，5-导出）
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

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除（0-未删除 1-删除）
     */
    @TableLogic
    private Integer isDelete;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OperLog other = (OperLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOperModule() == null ? other.getOperModule() == null : this.getOperModule().equals(other.getOperModule()))
            && (this.getOperType() == null ? other.getOperType() == null : this.getOperType().equals(other.getOperType()))
            && (this.getOperState() == null ? other.getOperState() == null : this.getOperState().equals(other.getOperState()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getOperAddress() == null ? other.getOperAddress() == null : this.getOperAddress().equals(other.getOperAddress()))
            && (this.getOperMethod() == null ? other.getOperMethod() == null : this.getOperMethod().equals(other.getOperMethod()))
            && (this.getOperUrl() == null ? other.getOperUrl() == null : this.getOperUrl().equals(other.getOperUrl()))
            && (this.getRequestParam() == null ? other.getRequestParam() == null : this.getRequestParam().equals(other.getRequestParam()))
            && (this.getResponseInfo() == null ? other.getResponseInfo() == null : this.getResponseInfo().equals(other.getResponseInfo()))
            && (this.getErrorInfo() == null ? other.getErrorInfo() == null : this.getErrorInfo().equals(other.getErrorInfo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getUserAccount() == null ? other.getUserAccount() == null : this.getUserAccount().equals(other.getUserAccount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOperModule() == null) ? 0 : getOperModule().hashCode());
        result = prime * result + ((getOperType() == null) ? 0 : getOperType().hashCode());
        result = prime * result + ((getOperState() == null) ? 0 : getOperState().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getOperAddress() == null) ? 0 : getOperAddress().hashCode());
        result = prime * result + ((getOperMethod() == null) ? 0 : getOperMethod().hashCode());
        result = prime * result + ((getOperUrl() == null) ? 0 : getOperUrl().hashCode());
        result = prime * result + ((getRequestParam() == null) ? 0 : getRequestParam().hashCode());
        result = prime * result + ((getResponseInfo() == null) ? 0 : getResponseInfo().hashCode());
        result = prime * result + ((getErrorInfo() == null) ? 0 : getErrorInfo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getUserAccount() == null) ? 0 : getUserAccount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", operModule=").append(operModule);
        sb.append(", operType=").append(operType);
        sb.append(", operState=").append(operState);
        sb.append(", ip=").append(ip);
        sb.append(", operAddress=").append(operAddress);
        sb.append(", operMethod=").append(operMethod);
        sb.append(", operUrl=").append(operUrl);
        sb.append(", requestParam=").append(requestParam);
        sb.append(", responseInfo=").append(responseInfo);
        sb.append(", errorInfo=").append(errorInfo);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", userAccount=").append(userAccount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
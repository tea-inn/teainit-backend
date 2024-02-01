package top.teainn.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 * @TableName login_log
 * @author teainn
 */
@TableName(value ="login_log")
@Data
public class LoginLog implements Serializable {
    /**
     * 访问编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作人
     */
    private String loginAccount;

    /**
     * 操作状态（0-成功 1-失败）
     */
    private Integer loginState;

    /**
     * 操作状态（0-成功 1-失败）
     */
    private String loginInfo;

    /**
     * 操作地址
     */
    private String ip;

    /**
     * 操作地点
     */
    private String loginAddress;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String operSystem;

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
        LoginLog other = (LoginLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLoginAccount() == null ? other.getLoginAccount() == null : this.getLoginAccount().equals(other.getLoginAccount()))
            && (this.getLoginState() == null ? other.getLoginState() == null : this.getLoginState().equals(other.getLoginState()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getLoginAddress() == null ? other.getLoginAddress() == null : this.getLoginAddress().equals(other.getLoginAddress()))
            && (this.getBrowser() == null ? other.getBrowser() == null : this.getBrowser().equals(other.getBrowser()))
            && (this.getOperSystem() == null ? other.getOperSystem() == null : this.getOperSystem().equals(other.getOperSystem()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLoginAccount() == null) ? 0 : getLoginAccount().hashCode());
        result = prime * result + ((getLoginState() == null) ? 0 : getLoginState().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getLoginAddress() == null) ? 0 : getLoginAddress().hashCode());
        result = prime * result + ((getBrowser() == null) ? 0 : getBrowser().hashCode());
        result = prime * result + ((getOperSystem() == null) ? 0 : getOperSystem().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginAccount=").append(loginAccount);
        sb.append(", loginState=").append(loginState);
        sb.append(", ip=").append(ip);
        sb.append(", loginAddress=").append(loginAddress);
        sb.append(", browser=").append(browser);
        sb.append(", operSystem=").append(operSystem);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
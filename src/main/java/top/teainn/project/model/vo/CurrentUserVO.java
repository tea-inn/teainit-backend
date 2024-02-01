package top.teainn.project.model.vo;

import cn.dev33.satoken.stp.SaTokenInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 当前登录用户 VO
 * 
 * @author teainn
 * @date 2024/01/12 22:04
 */
@Data
public class CurrentUserVO implements Serializable {
    
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户权限集合
     */
    private Set<String> permSet;

    /**
     * 登录 token 内容
     */
    private SaTokenInfo tokenInfo;


    private static final long serialVersionUID = 1L;
}
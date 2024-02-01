package top.teainn.project.service.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.teainn.project.model.dto.user.UserQueryRequest;
import top.teainn.project.model.dto.user.UserUpdateRequest;
import top.teainn.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 用户服务
 *
 * @author teainn
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return Long 新用户 id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求
     * @return User 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户信息
     *
     * @param request 请求
     * @return User
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     * 修改用户密码
     *
     * @param userId 用户名
     * @param oldUserPassword 旧密码
     * @param newUserPassword 新密码
     * @return Boolean
     */
    Boolean updateUserPwd(Long userId, String oldUserPassword, String newUserPassword);


    /**
     * 更新用户信息以及角色信息
     *
     * @param userUpdateRequest 更新信息
     * @return Boolean
     */
    Boolean updateUserAndRole(UserUpdateRequest userUpdateRequest);

    /**
     * 根据用户 id 获取用户所有权限
     *
     * @param userId 用户 id
     * @return List<String>
     */
    Set<String> getUserAllPermByUserId(Long userId);

    /**
     * 检查用户账号是否存在
     *
     * @param userAccount 账号
     * @return Boolean
     */
    Boolean isExistUserAccount(String userAccount);

    /**
     * 添加用户，密码初始化为 12345678
     *
     * @param userAccount 用户账号
     * @param roleIdList  用户拥有的角色 id 列表
     * @return Long 新增用户 id
     */
    Long addUser(String userName, String userAccount, List<Long> roleIdList);

    /**
     * 删除用户以及用户角色关联信息
     *
     * @param userId 用户 id
     */
    void deleteUser(Long userId);

    /**
     * 分页
     *
     * @param userQueryRequest 用户查询参数
     * @return Page<User>
     */
    Page<User> pageUser(UserQueryRequest userQueryRequest);
}

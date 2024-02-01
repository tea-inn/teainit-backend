package top.teainn.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.teainn.project.annotation.LoginLog;
import top.teainn.project.annotation.OperLog;
import top.teainn.project.annotation.RateLimiter;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.common.IdRequest;
import top.teainn.project.common.ResultUtils;
import top.teainn.project.exception.BusinessException;
import top.teainn.project.exception.ThrowUtils;
import top.teainn.project.model.dto.user.*;
import top.teainn.project.model.entity.User;
import top.teainn.project.model.entity.UserRole;
import top.teainn.project.model.enums.BusinessTypeEnum;
import top.teainn.project.model.vo.CurrentUserVO;
import top.teainn.project.model.vo.UserVO;
import top.teainn.project.service.system.UserRoleService;
import top.teainn.project.service.system.UserService;
import top.teainn.project.util.TeaBeanUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户接口
 *
 * @author teainn
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    private static final String USER_MANAGE_MODULE = "用户管理";


    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册信息请求类
     * @return BaseResponse<Long>
     */
    @PostMapping("/register")
    @RateLimiter
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long userId = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(userId);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录信息
     * @param request          请求
     * @return BaseResponse<CurrentUserVO>
     */
    @LoginLog
    @PostMapping("/login")
    @RateLimiter
    public BaseResponse<CurrentUserVO> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        User user = userService.userLogin(userAccount, userPassword, request);
        Set<String> permSet = userService.getUserAllPermByUserId(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        CurrentUserVO currentUserVO = new CurrentUserVO();
        currentUserVO.setId(user.getId());
        currentUserVO.setUserName(user.getUserName());
        currentUserVO.setUserAccount(user.getUserAccount());
        currentUserVO.setUserAvatar(user.getUserAvatar());
        currentUserVO.setGender(user.getGender());
        currentUserVO.setPermSet(permSet);
        currentUserVO.setTokenInfo(tokenInfo);

        return ResultUtils.success(currentUserVO);
    }

    /**
     * 退出登录
     *
     * @return BaseResponse<Boolean>
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        StpUtil.logout();
        return ResultUtils.success();
    }

    /**
     * 获取当前登录用户信息和权限
     *
     * @param request 请求
     * @return BaseResponse<CurrentUserVO>
     */
    @GetMapping("/current")
    public BaseResponse<CurrentUserVO> getCurrentUserAndPerm(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        Set<String> permSet = userService.getUserAllPermByUserId(user.getId());

        CurrentUserVO currentUserVO = new CurrentUserVO();
        currentUserVO.setId(user.getId());
        currentUserVO.setUserName(user.getUserName());
        currentUserVO.setUserAccount(user.getUserAccount());
        currentUserVO.setUserAvatar(user.getUserAvatar());
        currentUserVO.setGender(user.getGender());
        currentUserVO.setPermSet(permSet);

        return ResultUtils.success(currentUserVO);
    }

    /**
     * 修改密码
     *
     * @param userUpdatePwdRequest 请求
     * @return BaseResponse<Boolean>
     */
    // todo 修改密码待完善
    @OperLog(module = "个人管理", type = BusinessTypeEnum.UPDATE)
    @PostMapping("/update/password")
    public BaseResponse<Boolean> updateUserPassword(@RequestBody UserUpdatePwdRequest userUpdatePwdRequest) {
        if (userUpdatePwdRequest == null) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR);
        }
        Long userId = userUpdatePwdRequest.getUserId();
        String oldUserPassword = userUpdatePwdRequest.getOldUserPassword();
        String newUserPassword = userUpdatePwdRequest.getNewUserPassword();
        String checkPassword = userUpdatePwdRequest.getCheckPassword();
        if (StringUtils.isBlank(oldUserPassword) || StringUtils.isBlank(newUserPassword) || StringUtils.isBlank(checkPassword)) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "参数不符合要求");
        }
        if (oldUserPassword.isEmpty() || newUserPassword.isEmpty() || checkPassword.isEmpty()) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "参数不符合要求");
        }
        if (!checkPassword.equals(newUserPassword)) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "两次密码不一致");
        }
        Boolean result = userService.updateUserPwd(userId, oldUserPassword, newUserPassword);
        return ResultUtils.success(result);
    }

    // endregion

    // region 增删改查

    /**
     * 创建用户
     *
     * @param userAddRequest 添加信息
     * @return BaseResponse<Long> 新增用户 id
     */
    @OperLog(module = USER_MANAGE_MODULE, type = BusinessTypeEnum.INSERT)
    @SaCheckPermission("system:user:add")
    @PostMapping("/add")
    public BaseResponse<Long> addUser(@Validated @RequestBody UserAddRequest userAddRequest) {
        String userName = userAddRequest.getUserName();
        String userAccount = userAddRequest.getUserAccount();
        List<Long> roleIdList = userAddRequest.getRoleIdList();

        Long addedUserId = userService.addUser(userName, userAccount, roleIdList);
        return ResultUtils.success(addedUserId);
    }

    /**
     * 修改用户
     *
     * @param updateRequest 修改信息
     * @return BaseResponse<Void>
     */
    @OperLog(module = USER_MANAGE_MODULE, type = BusinessTypeEnum.UPDATE)
    @SaCheckPermission("system:user:edit")
    @PostMapping("/update")
    public BaseResponse<Void> updateUser(@RequestBody @Validated UserUpdateRequest updateRequest) {
        boolean result = userService.updateUserAndRole(updateRequest);
        ThrowUtils.throwIf(!result, ErrorEnum.SYSTEM_ERROR);
        return ResultUtils.success();
    }

    /**
     * 删除用户
     *
     * @param idRequest id
     * @return BaseResponse<Void>
     */
    @OperLog(module = USER_MANAGE_MODULE, type = BusinessTypeEnum.DELETE)
    @SaCheckPermission("system:user:remove")
    @PostMapping("/delete")
    public BaseResponse<Void> deleteUser(@Validated @RequestBody IdRequest idRequest) {
        Long userId = idRequest.getId();
        userService.deleteUser(userId);
        return ResultUtils.success();
    }

    /**
     * 获取用户列表
     *
     * @param userQueryRequest 查询信息
     * @return BaseResponse<List<UserVO>>
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list")
    public BaseResponse<List<UserVO>> listUser(@Validated UserQueryRequest userQueryRequest) {
        User userQuery = new User();
        BeanUtils.copyProperties(userQueryRequest, userQuery);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(queryWrapper);

        List<UserVO> userVOList = TeaBeanUtils.copyListProperties(userList, UserVO::new);
        return ResultUtils.success(userVOList);
    }


    /**
     * 分页获取用户列表
     *
     * @param userQueryRequest 查询信息
     * @return BaseResponse<Page<UserVO>>
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/page")
    public BaseResponse<Page<UserVO>> pageUser(UserQueryRequest userQueryRequest) {
        Page<User> userPage = userService.pageUser(userQueryRequest);
        List<User> records = userPage.getRecords();

        // 获取用户信息包括角色信息
        List<UserVO> userVOList = records.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            List<Long> roleIdList = userRoleService.list(
                            new LambdaQueryWrapper<UserRole>()
                                    .eq(UserRole::getUserId, user.getId()))
                    .stream().map(UserRole::getRoleId).collect(Collectors.toList());
            userVO.setRoleIdList(roleIdList);
            return userVO;
        }).collect(Collectors.toList());

        Page<UserVO> pageUserVO = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        pageUserVO.setRecords(userVOList);
        return ResultUtils.success(pageUserVO);
    }

    // endregion

}

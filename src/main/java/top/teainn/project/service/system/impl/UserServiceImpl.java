package top.teainn.project.service.system.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.exception.BusinessException;
import top.teainn.project.exception.ThrowUtils;
import top.teainn.project.mapper.system.MenuMapper;
import top.teainn.project.mapper.system.RoleMenuMapper;
import top.teainn.project.mapper.system.UserMapper;
import top.teainn.project.mapper.system.UserRoleMapper;
import top.teainn.project.model.dto.user.UserQueryRequest;
import top.teainn.project.model.dto.user.UserUpdateRequest;
import top.teainn.project.model.entity.Menu;
import top.teainn.project.model.entity.RoleMenu;
import top.teainn.project.model.entity.User;
import top.teainn.project.model.entity.UserRole;
import top.teainn.project.model.enums.GenderEnum;
import top.teainn.project.service.system.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 用户实现类
 *
 * @author teainn
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuMapper menuMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "teainn@2023";

    /**
     * 初始密码
     */
    private static final String INIT_PASSWORD = "136cab82bfdab0e552715d9e79281371";

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户账号过短");
        }
        if (userAccount.length() > 20) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户账号过长");
        }
        String r = "^[A-Za-z]+$";
        Matcher matcher = Pattern.compile(r).matcher(userAccount);
        if (!matcher.find()) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户账号只能为英文");
        }

        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户密码过短");
        }
        if (userPassword.length() > 20 || checkPassword.length() > 20) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户密码过长");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorEnum.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorEnum.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验登录状态
        // 查询该账号信息，查询错误次数，如果次数大于 10 次，则提示账号已锁定，请联系管理员
        QueryWrapper<User> queryWrapperByAccount = new QueryWrapper<>();
        queryWrapperByAccount.eq("userAccount", userAccount);
        User accountUser = userMapper.selectOne(queryWrapperByAccount);
        // 账号不存在，提示
        if (accountUser == null) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户不存在或密码错误");
        }
        Integer errorCount = accountUser.getErrorCount();
        if (errorCount >= 10) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "该账号已锁定，请联系管理员");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            // 错误次数加 1
            User userForErrorCount = new User();
            errorCount++;
            userForErrorCount.setErrorCount(errorCount);
            userMapper.update(userForErrorCount, queryWrapperByAccount);
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户不存在或密码错误");
        }
        Long userId = user.getId();
        // 3. 记录用户的登录态
        // request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        // 登录
        StpUtil.login(userId);
        // 4. 记录用户是否自动登录
        // request.getSession().setAttribute(UserConstant.USER_AUTO_LOGIN, autoLogin);
        return user;
    }

    /**
     * 获取当前登录用户基本信息
     *
     * @param request 请求
     * @return 用户
     */
    @Override
    public User getCurrentUser(HttpServletRequest request) {
        // 判断是否登录
        boolean login = StpUtil.isLogin();
        ThrowUtils.throwIf(!login, ErrorEnum.NOT_LOGIN_ERROR);

        // 获取登录用户 id
        Object loginIdObj = StpUtil.getLoginId();
        Long userId = Long.valueOf(loginIdObj.toString());

        // 获取用户信息
        User currentUser = userMapper.selectById(userId);
        ThrowUtils.throwIf(ObjUtil.isEmpty(currentUser), ErrorEnum.NOT_FOUND_ERROR, "用户不存在");

        return currentUser;
    }

    @Override
    public Boolean updateUserPwd(Long userId, String oldUserPassword, String newUserPassword) {
        if (userId <= 0 || oldUserPassword.isEmpty() || newUserPassword.isEmpty()) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR);
        }
        User user = userMapper.selectById(userId);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "用户不存在");
        }
        String dbUserPassword = user.getUserPassword();
        // 给用户提供的旧密码加密
        String oldEncryptPassword = DigestUtils.md5DigestAsHex((SALT + oldUserPassword).getBytes());
        // 加密后的旧密码和数据库中的密码进行比对
        if (!oldEncryptPassword.equals(dbUserPassword)) {
            throw new BusinessException(ErrorEnum.PARAMS_ERROR, "密码错误");
        }
        // 给新密码加密后存入数据库
        String newEncryptPassword = DigestUtils.md5DigestAsHex((SALT + newUserPassword).getBytes());
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setUserPassword(newEncryptPassword);
        int updateRow = userMapper.updateById(updateUser);
        ThrowUtils.throwIf(updateRow < 1, ErrorEnum.SYSTEM_ERROR);
        return true;
    }


    @Override
    @Transactional
    public Boolean updateUserAndRole(UserUpdateRequest updateRequest) {
        Long userId = updateRequest.getId();
        List<Long> roleIdList = updateRequest.getRoleIdList();
        String userAccount = updateRequest.getUserAccount();

        // 校验
        ThrowUtils.throwIf(isExistUserAccount(userAccount), ErrorEnum.USER_ACCOUNT_ALREADY_EXIST);

        // 修改用户信息
        User user = new User();
        BeanUtils.copyProperties(updateRequest, user);
        int updateResult = userMapper.updateById(user);
        ThrowUtils.throwIf(updateResult <= 0, ErrorEnum.SYSTEM_ERROR, "修改用户信息失败");

        // 删除原有用户角色关联
        int deleteResult = userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        ThrowUtils.throwIf(deleteResult <= 0, ErrorEnum.SYSTEM_ERROR, "修改用户角色失败");
        // 新增用户角色关联
        roleIdList.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            int insertResult = userRoleMapper.insert(userRole);
            ThrowUtils.throwIf(insertResult <= 0, ErrorEnum.SYSTEM_ERROR, "新增用户角色失败");
        });

        return true;
    }

    /**
     * 根据用户角色 id 列表获取用户所有权限
     * 用户角色类型 -> 角色列表 [1,2,4]
     * 返回 [user:list:query,list...]
     *
     * @param roleIdList 角色 id 列表
     * @return 权限列表
     */
    private Set<String> getUserAllPermByRoleIdList(List<Long> roleIdList) {
        ThrowUtils.throwIf(CollUtil.isEmpty(roleIdList), ErrorEnum.PARAMS_ERROR, "角色列表为空");
        // 获取所有菜单 id
        List<RoleMenu> roleMenus = roleMenuMapper.selectBatchIds(roleIdList);
        Set<Long> menuSet = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toCollection(HashSet::new));

        // 获取所有菜单的权限名称
        List<Menu> menus = menuMapper.selectBatchIds(menuSet);
        Set<String> allPermSet = new HashSet<>();
        //menus.stream().flatMap(menu -> getPermByParentMenu(menu).stream()).forEach(allPermSet::add);
        menus.stream().map(Menu::getPerms).filter(perm -> !"-".equals(perm)).forEach(allPermSet::add);
        return allPermSet;
    }

    /**
     * 根据父菜单获取子菜单的权限
     *
     * @param menu 父菜单
     * @return List<String>
     */
    //private List<String> getPermByParentMenu(Menu menu) {
    //    List<String> permList = new ArrayList<>();
    //    String perm = menu.getPerms();
    //    if (!"-".equals(perm)) {
    //        permList.add(perm);
    //    }
    //
    //    List<Menu> sonMenus =
    //            menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, menu.getId()));
    //    sonMenus.forEach(sonMenu -> {
    //        List<String> permByParentMenu = getPermByParentMenu(sonMenu);
    //        permList.addAll(permByParentMenu);
    //    });
    //
    //    return permList;
    //}

    @Override
    public Set<String> getUserAllPermByUserId(Long userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getUserId, userId)
        );
        List<Long> roleIdList = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return getUserAllPermByRoleIdList(roleIdList);
    }

    @Override
    public Boolean isExistUserAccount(String userAccount) {
        ThrowUtils.throwIf(StrUtil.isBlank(userAccount), ErrorEnum.PARAMS_ERROR, "用户不允许为空");
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount));
        return userList.size() > 1;
    }

    @Override
    @Transactional
    public Long addUser(String userName, String userAccount, List<Long> roleIdList) {
        ThrowUtils.throwIf(isExistUserAccount(userAccount), ErrorEnum.PARAMS_ERROR);
        // 检查账号是否存在
        Boolean isExistUser = isExistUserAccount(userAccount);
        ThrowUtils.throwIf(isExistUser, ErrorEnum.USER_ACCOUNT_ALREADY_EXIST);

        User user = new User();
        user.setUserAccount(userAccount);
        user.setGender(GenderEnum.UNKNOWN.getValue());
        // 初始化用户名
        if (StrUtil.isBlank(userName)) {
            String uuidStr = UUID.randomUUID().toString().substring(0, 8);
            user.setUserName("用户_" + uuidStr);
        } else {
            user.setUserName(userName);
        }
        // 初始化密码为 12345678
        user.setUserPassword(INIT_PASSWORD);

        boolean saveResult = save(user);
        ThrowUtils.throwIf(!saveResult, ErrorEnum.SYSTEM_ERROR, "添加用户失败");

        roleIdList.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            int insertRow = userRoleMapper.insert(userRole);
            if (insertRow <= 0) {
                throw new BusinessException(ErrorEnum.SYSTEM_ERROR, "添加用户角色关联失败");
            }
        });
        return user.getId();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        ThrowUtils.throwIf(ObjUtil.isEmpty(userId), ErrorEnum.PARAMS_ERROR, "删除用户 id 为空");
        int deleteUserRow = userMapper.deleteById(userId);
        ThrowUtils.throwIf(deleteUserRow < 1, ErrorEnum.SYSTEM_ERROR, "删除用户信息失败");
        int deleteUserRoleRow = userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        ThrowUtils.throwIf(deleteUserRoleRow < 1, ErrorEnum.SYSTEM_ERROR, "删除用户角色关联失败");
    }

    @Override
    public Page<User> pageUser(UserQueryRequest queryRequest) {
        Long current = queryRequest.getCurrent();
        Long pageSize = queryRequest.getPageSize();
        String userName = queryRequest.getUserName();
        String userAccount = queryRequest.getUserAccount();
        Integer gender = queryRequest.getGender();
        Date startTime = queryRequest.getStartTime();
        Date endTime = queryRequest.getEndTime();

        User userQuery = new User();
        BeanUtils.copyProperties(queryRequest, userQuery);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotEmpty(gender), User::getGender, gender);
        queryWrapper.eq(StrUtil.isNotBlank(userAccount), User::getUserAccount, userAccount);
        queryWrapper.like(StrUtil.isNotBlank(userName), User::getUserName, userName);
        queryWrapper.between(ObjUtil.isAllNotEmpty(startTime, endTime), User::getCreateTime, startTime, endTime);

        return userMapper.selectPage(new Page<>(current, pageSize), queryWrapper);
    }

}





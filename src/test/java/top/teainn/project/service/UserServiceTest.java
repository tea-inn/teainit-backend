package top.teainn.project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.exception.BusinessException;
import top.teainn.project.mapper.system.UserMapper;
import top.teainn.project.model.entity.User;
import top.teainn.project.service.system.UserService;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务测试
 *
 * @author teainn
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void testAddUser() {
        User user = new User();
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    void userRegister() {
        String userAccount = "teainn";
        String userPassword = "";
        String checkPassword = "123456";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "yu";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "teainn";
            userPassword = "123456";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "yu pi";
            userPassword = "12345678";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            checkPassword = "123456789";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "dogYupi";
            checkPassword = "12345678";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "teainn";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }

    @Test
    void testAccount() {
        String userAccount = "teainn1111";
        String r = "^[A-Za-z]+$";
        Matcher matcher = Pattern.compile(r).matcher(userAccount);
        System.out.println(matcher.find());

    }
    private static final String SALT = "teainn";
    @Test
    void testUpdateUserPwd() {
        String oldUserPassword = "12345678";
        String newUserPassword = "123456789";
        Long userId = 1L;
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
        if (updateRow >= 1) {
            System.out.println("成功");
        }
    }

    @Test
    void userLogin() {
    }

    @Test
    void getCurrentUser() {
    }

    @Test
    void isAutoLogin() {
    }

    @Test
    void updateUserPwd() {
    }

    @Test
    void isHaveUserAccount() {
    }

    @Test
    void updateUserAndRole() {
    }

    @Test
    void listRoleMapByUserId() {
    }

    @Test
    void getUserAllPermByUserId() {
        Set<String> userAllPerm = userService.getUserAllPermByUserId(1L);
        System.out.println(userAllPerm);

    }

    @Test
    void userLogout() {
    }

}
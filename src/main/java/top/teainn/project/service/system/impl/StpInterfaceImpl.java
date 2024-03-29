package top.teainn.project.service.system.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;
import top.teainn.project.mapper.system.UserRoleMapper;
import top.teainn.project.model.entity.UserRole;
import top.teainn.project.service.system.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserService userService;

    /**
     * 返回一个账号所拥有的权限码集合 
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 获得角色列表
        List<UserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("userId", loginId));
        List<Long> roleList = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleList.add(userRole.getRoleId());
        }
        Set<Long> collect = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toCollection(HashSet::new));
        Set<String> permSet = userService.getUserAllPermByUserId(Long.parseLong(loginId.toString()));

        return new ArrayList<>(permSet);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<String>();    
        list.add("admin");
        list.add("super-admin");
        return list;
    }

}

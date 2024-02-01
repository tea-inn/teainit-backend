package top.teainn.project.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.exception.ThrowUtils;
import top.teainn.project.mapper.system.MenuMapper;
import top.teainn.project.mapper.system.RoleMapper;
import top.teainn.project.mapper.system.RoleMenuMapper;
import top.teainn.project.model.entity.Menu;
import top.teainn.project.model.entity.Role;
import top.teainn.project.model.entity.RoleMenu;
import top.teainn.project.model.enums.MenuTypeEnum;
import top.teainn.project.service.system.RoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * RoleServiceImpl
 *
 * @author teainn
 * @date 2024/01/28 22:42
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Boolean saveRolePerms(Long roleId, List<Long> menuIdList) {
        Integer row = roleMapper.insertRoleMenuPerms(roleId, menuIdList);
        ThrowUtils.throwIf(row < 1, ErrorEnum.SYSTEM_ERROR);
        return true;
    }

    @Override
    public List<Long> getRoleMenuPermsByRoleId(Long id) {
        return roleMapper.selectRolePerms(id);
    }

    @Override
    public List<Long> getRolePermsOnlyButtonType(Long roleId) {
        ThrowUtils.throwIf(roleId == null, ErrorEnum.PARAMS_ERROR, "角色 id 为空");
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper);
        List<Long> btnTypeMenuIdList =  new ArrayList<>();
        roleMenus.forEach(roleMenu -> {
           // 只要按钮级别权限
            Long menuId = roleMenu.getMenuId();
            Menu menu = menuMapper.selectById(menuId);
            Integer type = menu.getType();
            if (MenuTypeEnum.BUTTON.getValue() == type) {
                btnTypeMenuIdList.add(menu.getId());
            }
        });
        return btnTypeMenuIdList;
    }

    @Override
    @Transactional
    public Long saveRoleAndPerms(Role role, List<Long> menuIdList) {
        int insert = roleMapper.insert(role);
        ThrowUtils.throwIf(insert < 1, ErrorEnum.SYSTEM_ERROR, "保存用户信息失败");

        // 获取新增角色 id
        Long roleId = role.getId();
        // 保存角色与权限关联信息
        menuIdList.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            int insertRow = roleMenuMapper.insert(roleMenu);
            ThrowUtils.throwIf(insertRow < 1, ErrorEnum.SYSTEM_ERROR, "保存角色与权限关联信息失败");
        });

        return roleId;
    }

    @Override
    @Transactional
    public Boolean updateRoleAndPerms(Role role, List<Long> menuIdList) {
        // 判断角色是否存在
        Long roleId = role.getId();
        assertRoleExist(roleId);

        // 修改角色信息
        int updateRow = roleMapper.updateById(role);
        ThrowUtils.throwIf(updateRow < 1, ErrorEnum.SYSTEM_ERROR, "修改角色信息失败");

        // 修改角色和权限的关联信息
        Integer deleteResult = roleMapper.deleteRoleMenuByRoleId(role.getId());
        Boolean saveResult = saveRolePerms(roleId, menuIdList);
        ThrowUtils.throwIf(deleteResult < 1 || !saveResult, ErrorEnum.SYSTEM_ERROR, "角色权限关联信息修改失败");

        return true;
    }

    /**
     * 判断角色是否存在
     *
     * @param roleId 角色 id
     */
    private void assertRoleExist(Long roleId) {
        Role oldRole = roleMapper.selectById(roleId);
        ThrowUtils.throwIf(oldRole == null, ErrorEnum.NOT_FOUND_ERROR, "角色信息不存在");
    }

    @Override
    @Transactional
    public Boolean deleteRoleAndPerms(Long roleId) {
        assertRoleExist(roleId);
        int deleteRow = roleMapper.deleteById(roleId);
        ThrowUtils.throwIf(deleteRow < 1, ErrorEnum.SYSTEM_ERROR, "删除角色信息失败");

        // 删除角色权限关联信息
        Integer deleteResult = roleMapper.deleteRoleMenuByRoleId(roleId);
        ThrowUtils.throwIf(deleteResult < 1, ErrorEnum.SYSTEM_ERROR, "删除角色权限关联信息失败");

        return true;
    }

    /**
     * 过滤掉目录类型，因为目录没有权限
     *
     * @param menuIdList 待过滤菜单权限
     * @return 没有目录类型的菜单权限
     */
    //private List<Long> filterDirectoryInMenuIdList(List<Long> menuIdList) {
    //    return menuIdList.stream().filter(menuId -> {
    //        Menu menu = menuMapper.selectById(menuId);
    //        Integer type = menu.getType();
    //        return MenuTypeEnum.DIRECTORY.getValue() != type;
    //    }).collect(Collectors.toList());
    //}


    /**
     * 递归获取所有权限
     *
     * @param menuIdList    准备递归的权限列表
     * @param havePermsList 收集全部权限列表
     * @return List<Long>
     */
    //private List<Long> getHavePermsMenuIdList(List<Long> menuIdList, List<Long> havePermsList) {
    //    for (Long menuId : menuIdList) {
    //        Menu menu = menuMapper.selectById(menuId);
    //        Integer type = menu.getType();
    //        String perms = menu.getPerms();
    //        // 目录级别，找到下面所有菜单以及菜单下的按钮权限
    //        if (MenuTypeEnum.DIRECTORY.getValue() == type) {
    //            QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
    //            menuQueryWrapper.eq("parentId", menu.getId());
    //            List<Menu> menus = menuMapper.selectList(menuQueryWrapper);
    //            List<Long> childMenuIds = menus.stream().map(Menu::getId).collect(Collectors.toList());
    //            getHavePermsMenuIdList(childMenuIds, havePermsList);
    //            continue;
    //        }
    //        //菜单级别
    //        havePermsList.add(menuId);
    //    }
    //    return havePermsList;
    //}
}





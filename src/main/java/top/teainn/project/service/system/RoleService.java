package top.teainn.project.service.system;

import top.teainn.project.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * RoleService
 *
 * @author teainn
 * @date 2024/01/28 22:42
 */
public interface RoleService extends IService<Role> {

    /**
     * 保存角色的权限
     *
     * @param roleId 角色 id
     * @param menuIdList 权限列表
     * @return Boolean
     */
    Boolean saveRolePerms(Long roleId, List<Long> menuIdList);

    /**
     * 获取角色所有权限
     *
     * @param roleId 角色 id
     * @return List<Long> 权限 id 列表
     */
    List<Long> getRoleMenuPermsByRoleId(Long roleId);

    /**
     * 获取角色按钮级别权限
     *
     * @param roleId 角色 id
     * @return Long 新增角色 id
     */
    List<Long> getRolePermsOnlyButtonType(Long roleId);

    /**
     * 保存角色信息以及权限
     *
     * @param role 角色
     * @param menuIdList 权限列表
     * @return Long 新增角色 id
     */
    Long saveRoleAndPerms(Role role, List<Long> menuIdList);

    /**
     * 修改角色信息以及权限
     *
     * @param role 角色
     * @param menuIdList 权限列表
     * @return Long 新增角色 id
     */
    Boolean updateRoleAndPerms(Role role, List<Long> menuIdList);

    /**
     * 删除角色信息以及权限
     *
     * @param roleId 角色 id
     * @return Long 新增角色 id
     */
    Boolean deleteRoleAndPerms(Long roleId);


}

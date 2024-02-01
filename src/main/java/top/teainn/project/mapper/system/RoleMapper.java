package top.teainn.project.mapper.system;

import top.teainn.project.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity top.teainn.project.model.entity.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    Integer insertRoleMenuPerms(Long roleId, List<Long> havePermsMenuIdList);

    Integer deleteRoleMenuByRoleId(Long roleId);

    List<Long> selectRolePerms(Long id);
}





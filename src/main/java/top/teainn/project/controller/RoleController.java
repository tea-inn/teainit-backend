package top.teainn.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.teainn.project.annotation.OperLog;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.common.IdRequest;
import top.teainn.project.common.ResultUtils;
import top.teainn.project.constant.CommonConstant;
import top.teainn.project.exception.ThrowUtils;
import top.teainn.project.model.dto.role.RoleAddRequest;
import top.teainn.project.model.dto.role.RoleQueryRequest;
import top.teainn.project.model.dto.role.RoleUpdateRequest;
import top.teainn.project.model.entity.Role;
import top.teainn.project.model.enums.BusinessTypeEnum;
import top.teainn.project.model.vo.RoleSelectVO;
import top.teainn.project.service.system.RoleService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统管理 - 角色管理
 *
 * @author teainn
 * @date 2024/01/28 20:09
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Resource
    private RoleService roleService;

    private static final String ROLE_MANAGE_MODULE = "角色管理";

    // region 增删改查

    /**
     * 新增角色
     *
     * @param roleAddRequest 新增信息
     * @return Long 角色 id
     */
    @OperLog(module = ROLE_MANAGE_MODULE, type = BusinessTypeEnum.INSERT)
    @SaCheckPermission("system:role:add")
    @PostMapping("/add")
    public BaseResponse<Long> addRole(@Validated @RequestBody RoleAddRequest roleAddRequest) {
        List<Long> menuIdList = roleAddRequest.getMenuIdList();
        Role role = new Role();
        BeanUtils.copyProperties(roleAddRequest, role);
        Long roleId = roleService.saveRoleAndPerms(role,menuIdList);
        return ResultUtils.success(roleId);
    }

    /**
     * 修改角色
     *
     * @param roleUpdateRequest 修改信息
     * @return BaseResponse<Boolean>
     */
    @OperLog(module = ROLE_MANAGE_MODULE, type = BusinessTypeEnum.UPDATE)
    @SaCheckPermission("system:role:edit")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateRole(@Validated @RequestBody RoleUpdateRequest roleUpdateRequest) {
        List<Long> menuIdList = roleUpdateRequest.getMenuIdList();
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdateRequest, role);

        Boolean updateResult = roleService.updateRoleAndPerms(role,menuIdList);
        return ResultUtils.success(updateResult);
    }

    /**
     * 删除角色
     *
     * @param idRequest id
     * @return BaseResponse<Boolean>
     */
    @OperLog(module = ROLE_MANAGE_MODULE, type = BusinessTypeEnum.DELETE)
    @SaCheckPermission("system:role:remove")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteRole(@Validated @RequestBody IdRequest idRequest) {
        Long id = idRequest.getId();
        Boolean deleteResult = roleService.deleteRoleAndPerms(id);
        return ResultUtils.success(deleteResult);
    }

    /**
     * 获取列表数据
     *
     * @param roleQueryRequest 查询信息
     * @return List<Role>
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public BaseResponse<List<Role>> listRole(@Validated RoleQueryRequest roleQueryRequest) {
        Role roleQuery = new Role();
        BeanUtils.copyProperties(roleQueryRequest,roleQuery);
        String roleName = roleQuery.getRoleName();

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleName), "roleName", roleName);

        List<Role> roleList = roleService.list(queryWrapper);
        return ResultUtils.success(roleList);
    }

    /**
     * 分页获取列表
     *
     * @param roleQueryRequest 查询请求
     * @return Page<Role>
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/page")
    public BaseResponse<Page<Role>> pageRole(@Validated RoleQueryRequest roleQueryRequest) {
        Role roleQuery = new Role();
        BeanUtils.copyProperties(roleQueryRequest, roleQuery);

        long current = roleQueryRequest.getCurrent();
        long size = roleQueryRequest.getPageSize();
        String sortField = roleQueryRequest.getSortField();
        String sortOrder = roleQueryRequest.getSortOrder();
        Date startTime = roleQueryRequest.getStartTime();
        Date endTime = roleQueryRequest.getEndTime();
        String roleName = roleQuery.getRoleName();

        // 限制爬虫
        ThrowUtils.throwIf(size > 50,ErrorEnum.PARAMS_ERROR);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleName), "roleName", roleName);
        if (startTime != null && endTime != null) {
            queryWrapper.gt("createTime",startTime);
            queryWrapper.lt("createTime",endTime);
        }
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        // 分页查询
        Page<Role> rolePage = roleService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(rolePage);
    }

    /**
     * 获取下拉角色列表
     *
     * @return BaseResponse<List<RoleSelectVO>> 角色下拉列表
     */
    @GetMapping("/select/list")
    public BaseResponse<List<RoleSelectVO>> listRoleSelect() {
        List<Role> roleList = roleService.list();

        List<RoleSelectVO> roleSelectVOList = roleList.stream().map(role -> {
            RoleSelectVO roleSelectVO = new RoleSelectVO();
            roleSelectVO.setId(role.getId());
            roleSelectVO.setRoleName(role.getRoleName());
            return roleSelectVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(roleSelectVOList);
    }

    /**
     * 根据 id 获取角色权限列表
     *
     * @param idRequest id
     * @return BaseResponse<List<Long>> 角色的权限列表
     */
    @GetMapping("/get/perms")
    public BaseResponse<List<Long>> getRolePerms(@Validated IdRequest idRequest) {
        Long id = idRequest.getId();
        List<Long> menuIdlist = roleService.getRoleMenuPermsByRoleId(id);
        return ResultUtils.success(menuIdlist);
    }

    /**
     * 回显权限树，只有按钮类型的权限
     *
     * @param idRequest id
     * @return BaseResponse<List<Long>> 权限 id 列表
     */
    @GetMapping("/get/perms/echo")
    public BaseResponse<List<Long>> getRolePermsToEcho(@Validated IdRequest idRequest) {
        Long id = idRequest.getId();
        List<Long> menuIdlist = roleService.getRolePermsOnlyButtonType(id);
        return ResultUtils.success(menuIdlist);
    }

    // endregion

}

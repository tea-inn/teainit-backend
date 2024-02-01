package top.teainn.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.teainn.project.annotation.OperLog;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.common.IdRequest;
import top.teainn.project.common.ResultUtils;
import top.teainn.project.exception.ThrowUtils;
import top.teainn.project.model.dto.menu.MenuAddRequest;
import top.teainn.project.model.dto.menu.MenuQueryRequest;
import top.teainn.project.model.dto.menu.MenuUpdateRequest;
import top.teainn.project.model.entity.Menu;
import top.teainn.project.model.enums.BusinessTypeEnum;
import top.teainn.project.model.vo.MenuTreeSelectVO;
import top.teainn.project.model.vo.MenuTreeVO;
import top.teainn.project.service.system.MenuService;
import top.teainn.project.util.TeaBeanUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制类
 *
 * @author teainn
 * @date 2024/01/28 22:49
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 默认权限标识
     */
    public static final String DEFAULT_PERM = "-";

    private static final String MENU_MANAGE_MODULE = "菜单管理";

    // region 增删改查

    /**
     * 新增菜单
     *
     * @param menuAddRequest 请求
     * @return BaseResponse<Long>
     */
    @OperLog(module = MENU_MANAGE_MODULE, type = BusinessTypeEnum.INSERT)
    @SaCheckPermission("system:menu:add")
    @PostMapping("/add")
    public BaseResponse<Long> addMenu(@Validated @RequestBody MenuAddRequest menuAddRequest) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuAddRequest, menu);
        if (StrUtil.isEmpty(menu.getPerms())) {
            menu.setPerms(DEFAULT_PERM);
        }
        menuService.save(menu);
        Long id = menu.getId();
        return ResultUtils.success(id);
    }

    /**
     * 修改菜单
     *
     * @param menuUpdateRequest 修改信息
     * @return BaseResponse<Boolean>
     */
    @OperLog(module = MENU_MANAGE_MODULE, type = BusinessTypeEnum.UPDATE)
    @SaCheckPermission("system:menu:edit")
    @PostMapping("/update")
    public BaseResponse<Void> updateMenu(@Validated @RequestBody MenuUpdateRequest menuUpdateRequest) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuUpdateRequest, menu);
        Long menuId = menu.getId();
        menuService.assertMenuExist(menuId);
        boolean result = menuService.updateById(menu);
        ThrowUtils.throwIf(!result, ErrorEnum.SYSTEM_ERROR, "修改菜单信息失败");
        return ResultUtils.success();
    }

    /**
     * 删除菜单
     *
     * @param idRequest id
     * @return BaseResponse<Boolean>
     */
    @OperLog(module = MENU_MANAGE_MODULE, type = BusinessTypeEnum.DELETE)
    @SaCheckPermission("system:menu:remove")
    @PostMapping("/delete")
    public BaseResponse<Void> deleteMenu(@Validated @RequestBody IdRequest idRequest) {
        Long menuId = idRequest.getId();
        menuService.assertMenuExist(menuId);
        boolean result = menuService.removeById(menuId);
        ThrowUtils.throwIf(!result,ErrorEnum.SYSTEM_ERROR,"删除菜单信息失败");
        return ResultUtils.success();
    }

    /**
     * 获取所有菜单，以菜单树的格式返回给前端展示
     * 菜单管理表格展示
     *
     * @return BaseResponse<List<MenuTreeVO>>
     */
    @GetMapping("/tree/list")
    public BaseResponse<List<MenuTreeVO>> listMenuTree() {
        List<MenuTreeVO> menuTree = menuService.listMenuTree();
        return ResultUtils.success(menuTree);
    }

    /**
     * 根据查询条件获取菜单列表
     * 为了前端数据统一，这里用到 menuTreeVO，实际 children 属性用不到
     *
     * @return BaseResponse<List<MenuTreeVO>>
     */
    @GetMapping("/list")
    public BaseResponse<List<MenuTreeVO>> listMenu(@Validated MenuQueryRequest menuQueryRequest) {
        List<Menu> menuList = menuService.listMenu(menuQueryRequest);
        List<MenuTreeVO> menuTreeVO = TeaBeanUtils.copyListProperties(menuList, MenuTreeVO::new);
        return ResultUtils.success(menuTreeVO);
    }

    // endregion

    /**
     * 获取下拉父级菜单，只获取到目录和菜单类型，按钮不获取
     * 新建和修改时的上级菜单下拉获取
     *
     * @return BaseResponse<List < MenuTreeSelectVO>>
     */
    @GetMapping("/tree/select")
    public BaseResponse<List<MenuTreeSelectVO>> listMenuTreeBySelect() {
        List<MenuTreeSelectVO> menuTree = menuService.listMenuTreeNotButtonType();
        return ResultUtils.success(menuTree);
    }
}

package top.teainn.project.service.system.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.exception.ThrowUtils;
import top.teainn.project.mapper.system.MenuMapper;
import top.teainn.project.model.dto.menu.MenuQueryRequest;
import top.teainn.project.model.entity.Menu;
import top.teainn.project.model.enums.MenuTypeEnum;
import top.teainn.project.model.vo.MenuTreeSelectVO;
import top.teainn.project.model.vo.MenuTreeVO;
import top.teainn.project.service.system.MenuService;
import top.teainn.project.util.TeaBeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MenuServiceImpl
 *
 * @author teainn
 * @date 2024/01/28 23:13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    // 最高一级目录
    private static final Long TOP_MENU_PARENT_ID = 0L;

    @Override
    public List<MenuTreeVO> listMenuTree() {
        // 获得最顶层的菜单
        List<MenuTreeVO> topMenuList = menuMapper.listParentId(TOP_MENU_PARENT_ID);
        return listMenuChildren(topMenuList);
    }

    @Override
    public List<MenuTreeSelectVO> listMenuTreeNotButtonType() {
        // 获得最顶层的菜单
        List<MenuTreeVO> topMenuList = menuMapper.listParentId(TOP_MENU_PARENT_ID);
        List<MenuTreeSelectVO> topMenuTreeSelectVO = TeaBeanUtils.copyListProperties(topMenuList, MenuTreeSelectVO::new);
        return listMenuChildrenNotButtonType(topMenuTreeSelectVO);
    }

    @Override
    public void assertMenuExist(Long menuId) {
        // 判断是否菜单存在
        Menu menu = menuMapper.selectById(menuId);
        ThrowUtils.throwIf(menu == null, ErrorEnum.NOT_FOUND_ERROR, "菜单不存在");
    }

    @Override
    public List<Menu> listMenu(MenuQueryRequest menuQueryRequest) {
        String menuName = menuQueryRequest.getMenuName();
        String perms = menuQueryRequest.getPerms();
        Integer type = menuQueryRequest.getType();
        Date startTime = menuQueryRequest.getStartTime();
        Date endTime = menuQueryRequest.getEndTime();

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(menuName), Menu::getMenuName, menuName);
        queryWrapper.eq(StrUtil.isNotBlank(perms), Menu::getPerms, perms);
        queryWrapper.eq(ObjUtil.isNotEmpty(type), Menu::getType, type);
        queryWrapper.between(ObjUtil.isAllNotEmpty(startTime, endTime), Menu::getCreateTime, startTime, endTime);

        return menuMapper.selectList(queryWrapper);
    }

    public List<MenuTreeVO> listMenuChildren(List<MenuTreeVO> menuList) {
        return menuList.stream().peek(menu -> {
            Long id = menu.getId();
            List<MenuTreeVO> childMenuList = menuMapper.listParentId(id);
            if (!childMenuList.isEmpty()) {
                listMenuChildren(childMenuList);
            }
            menu.setChildren(childMenuList);
        }).collect(Collectors.toList());
    }

    public List<MenuTreeSelectVO> listMenuChildrenNotButtonType(List<MenuTreeSelectVO> menuList) {
        return menuList.stream()
                .peek(menu -> {
                    Long id = menu.getId();
                    List<MenuTreeVO> childMenuList = menuMapper.listParentId(id);
                    List<MenuTreeSelectVO> childMenuSelectList = TeaBeanUtils.copyListProperties(childMenuList,
                            MenuTreeSelectVO::new);
                    if (!childMenuSelectList.isEmpty()) {
                        listMenuChildrenNotButtonType(childMenuSelectList);
                        // 过滤掉是按钮的类型
                        List<MenuTreeSelectVO> notButtonTypeCollect = childMenuSelectList.stream()
                                .filter(childMenu -> childMenu.getType() != MenuTypeEnum.BUTTON.getValue())
                                .collect(Collectors.toList());
                        menu.setChildren(notButtonTypeCollect);
                    } else {
                        // 统一返回 []
                        menu.setChildren(childMenuSelectList);
                    }
                }).collect(Collectors.toList());
    }
}





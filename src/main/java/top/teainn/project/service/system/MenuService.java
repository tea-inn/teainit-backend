package top.teainn.project.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import top.teainn.project.model.dto.menu.MenuQueryRequest;
import top.teainn.project.model.entity.Menu;
import top.teainn.project.model.vo.MenuTreeSelectVO;
import top.teainn.project.model.vo.MenuTreeVO;

import java.util.List;

/**
 * MenuService
 *
 * @author teainn
 * @date 2024/01/28 23:12
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树列表
     *
     * @return List<MenuTreeVO>
     */
    List<MenuTreeVO> listMenuTree();

    /**
     * 获取下拉菜单，没有按钮类型
     *
     * @return List<MenuTreeSelectVO>
     */
    List<MenuTreeSelectVO> listMenuTreeNotButtonType();

    /**
     * 判断菜单是否存在
     *
     * @param menuId 菜单 id
     */
    void assertMenuExist(Long menuId);

    /**
     * 根据查询条件获取菜单列表
     *
     * @param menuQueryRequest 查询信息
     * @return List<Menu>
     */
    List<Menu> listMenu(MenuQueryRequest menuQueryRequest);
}

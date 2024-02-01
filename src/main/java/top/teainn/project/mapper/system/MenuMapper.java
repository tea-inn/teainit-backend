package top.teainn.project.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import top.teainn.project.model.vo.MenuTreeVO;
import top.teainn.project.model.entity.Menu;

import java.util.List;

/**
 * @Entity top.teainn.project.model.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> listParentMenu(IPage<Menu> page,String menuName);

    List<MenuTreeVO> listParentId(Long parentId);
}





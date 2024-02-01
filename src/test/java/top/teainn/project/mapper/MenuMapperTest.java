package top.teainn.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.teainn.project.MyApplication;
import top.teainn.project.mapper.system.MenuMapper;
import top.teainn.project.model.vo.MenuTreeVO;
import top.teainn.project.model.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daqi
 * @date 2023/2/17
 */
@SpringBootTest(classes = MyApplication.class)
@RunWith(SpringRunner.class)
public class MenuMapperTest {
    @Resource
    private MenuMapper menuMapper;

    @Test
    public void testMenuParent() {
        List<Menu> menus = menuMapper.listParentMenu(new Page<Menu>(1, 10), null);
        System.out.println(menus);
    }

    @Test
    public void testMenuMapper() {
        List<MenuTreeVO> topMenuList = menuMapper.listParentId(0L);
        List<MenuTreeVO> menuTreeVOs = listMenuChildren(topMenuList);
        System.out.println(menuTreeVOs);

    }

    public List<MenuTreeVO> listMenuChildren(List<MenuTreeVO> menuList) {
        List<MenuTreeVO> collect = menuList.stream().map(menu -> {
            Long id = menu.getId();
            List<MenuTreeVO> menuTreeVOs = menuMapper.listParentId(id);
            if (menuTreeVOs != null) {
                listMenuChildren(menuTreeVOs);
            }
            menu.setChildren(menuTreeVOs);
            return menu;
        }).collect(Collectors.toList());
        return collect;
    }
}
package top.teainn.project.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.teainn.project.MyApplication;
import top.teainn.project.mapper.system.RoleMapper;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author daqi
 * @date 2023/2/17
 */
@SpringBootTest(classes = MyApplication.class)
@RunWith(SpringRunner.class)
public class RoleMapperTest {
    @Resource
    private RoleMapper roleMapper;

    @Test
    public void testRoleMapper() {
        Integer integer = roleMapper.insertRoleMenuPerms(1L, Arrays.asList(1L, 2L));
        Assert.assertNotNull(integer);
    }


}
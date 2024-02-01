package top.teainn.project.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * session 工具类
 *
 * @author teainn
 * @date 2023/11/03 15:11
 */
public class SessionUtil {

    public static void findAllSessionAttribute(HttpServletRequest request) {
        //获取session
        HttpSession session = request.getSession();
        // 获取session中所有的键值
        Enumeration<?> enumeration = session.getAttributeNames();
        // 遍历enumeration
        while (enumeration.hasMoreElements()) {
            // 获取session的属性名称
            String name = enumeration.nextElement().toString();
            // 根据键值取session中的值
            Object value = session.getAttribute(name);
            // 打印结果
            System.out.println("==================== session ==================");
            System.out.println("name:" + name + ",value:" + value);
            System.out.println("==================== session ==================");
        }

    }

}


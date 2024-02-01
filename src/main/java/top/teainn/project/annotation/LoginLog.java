package top.teainn.project.annotation;

import java.lang.annotation.*;

/**
 * 登录日志注解
 *
 * @author teainn
 * @date 2024/01/10 21:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})  //定义了注解声明在哪些元素之前
@Documented
public @interface LoginLog {

}
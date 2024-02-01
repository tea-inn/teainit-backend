package top.teainn.project.annotation;

import top.teainn.project.model.enums.BusinessTypeEnum;

import java.lang.annotation.*;

/**
 * 用户操作日志注解
 *
 * @author teainn
 * @date 2024/01/10 21:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})  //定义了注解声明在哪些元素之前
@Documented
public @interface OperLog {

    // 模块
    String module() default "";

    // 类型
    BusinessTypeEnum type() default BusinessTypeEnum.OTHER;

}
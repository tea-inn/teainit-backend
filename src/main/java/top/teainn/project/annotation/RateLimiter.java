package top.teainn.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流注解
 *
 * @author teainn
 * @date 2024/01/10 21:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiter {

    /**
     * 限流时间，单位秒
     */
    int time() default 5;

    /**
     * 限流次数
     */
    int count() default 10;

}
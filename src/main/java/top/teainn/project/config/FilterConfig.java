package top.teainn.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.teainn.project.filter.BusExceptionFilter;

import javax.annotation.Resource;

/**
 * 过滤配置
 *
 * @author teainn
 * @date 2024/01/10 22:05
 */
@Configuration
public class FilterConfig {

    @Resource
    private BusExceptionFilter busExceptionFilter;

    @Bean
    public FilterRegistrationBean<BusExceptionFilter> registerBusExceptionFilter() {
        FilterRegistrationBean<BusExceptionFilter> registration = new FilterRegistrationBean();
        registration.setFilter(busExceptionFilter);
        registration.addUrlPatterns("/*");
        registration.setName("busExceptionFilter");
        //设置Filter优先级
        registration.setOrder(-1);
        return registration;
    }

}

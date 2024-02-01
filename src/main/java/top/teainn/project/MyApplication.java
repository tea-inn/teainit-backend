package top.teainn.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 启动类
 *
 * @author teainn
 * @date 2024/01/12 23:35
 */
@SpringBootApplication
@MapperScan("top.teainn.project.mapper")
@EnableAsync
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class MyApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MyApplication.class, args);
    }

}

package cn;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
/*@Configuration*
 */
@SpringBootApplication
/*@MapperScan(basePackages = "com.allpackage.persistence")*/
/*@EnableScheduling*/
public class Application
{
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);

        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
        logger.debug("启动新闻微服务...");
    }
}

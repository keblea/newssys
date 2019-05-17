package cn.com.oceansoft.osc.ms.config;

import cn.com.oceansoft.osc.ms.domain.ApiQueryResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author wuzy
 * @created 2017-02-20-10:38
 * @email wzy@oceansoft.com.cn
 */
@Configuration
public class MvcConfig  extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ApiQueryResolver());
    }
}
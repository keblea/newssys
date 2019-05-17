package cn.com.oceansoft.osc.ms.domain;

import java.lang.annotation.*;

/**
 * Created by zhangxh on 2016/11/8.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiQuery {
    String value() default "";
}

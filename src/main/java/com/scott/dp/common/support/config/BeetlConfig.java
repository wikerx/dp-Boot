package com.scott.dp.common.support.config;

import com.scott.dp.common.support.properties.BeetlProperties;
import com.scott.dp.common.support.properties.GlobalProperties;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * beetl配置
 * @author Mr.薛
 */
@Configuration("beetlConfiguration")
public class BeetlConfig {

    @Autowired
    BeetlProperties beetlProperties;

    @Autowired
    GlobalProperties globalProperties;

    /**
     * beetl的配置
     */
    @Bean(name="beetlConfig", initMethod = "init")
    public BeetlGroupUtilConfiguration beetlConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        beetlGroupUtilConfiguration.setResourceLoader(new ClasspathResourceLoader(BeetlConfig.class.getClassLoader(), beetlProperties.getPrefix()));
        beetlGroupUtilConfiguration.setConfigProperties(beetlProperties.getProperties());
        beetlGroupUtilConfiguration.setSharedVars(globalProperties.getBeetlGlobalVars());
        return beetlGroupUtilConfiguration;
    }

    /**
     * beetl的视图解析器
     */
    @Bean
    public BeetlSpringViewResolver beetlViewResolver() {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setConfig(beetlConfiguration());
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        return beetlSpringViewResolver;
    }

}

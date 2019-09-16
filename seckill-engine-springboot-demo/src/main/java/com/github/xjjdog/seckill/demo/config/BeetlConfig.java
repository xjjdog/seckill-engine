package com.github.xjjdog.seckill.demo.config;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.util.Properties;

@Configuration
public class BeetlConfig {

    @Bean(initMethod = "init")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlConfiguration beetlGroupUtilConfiguration = new BeetlConfiguration();
        ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

        ClasspathResourceLoader classPathLoader = new ClasspathResourceLoader("/templates");
        beetlGroupUtilConfiguration.setResourceLoader(classPathLoader);

        Properties extProperties = new Properties();
        extProperties.put("RESOURCE.autoCheck", "true");
        beetlGroupUtilConfiguration.setConfigProperties(extProperties);
        beetlGroupUtilConfiguration.setResourceLoader(classPathLoader);

        return beetlGroupUtilConfiguration;
    }

    @Bean
    public BeetlSpringViewResolver getBeetlSpringViewResolver(BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

    @Bean(name="groupTemplate")
    public GroupTemplate getGroupTemplate(BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {

        return beetlGroupUtilConfiguration.getGroupTemplate();
    }

}

package com.scott.dp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 应用启动器
 * @author Mr.薛
 */
@SpringBootApplication
public class DpApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DpApplication.class);

    /**
     * jar启动
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DpApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        LOGGER.info("The Dp application has been started successfully!");
    }

    /**
     * war启动
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.bannerMode(Banner.Mode.OFF);
        SpringApplicationBuilder applicationBuilder = builder.sources(DpApplication.class);
        LOGGER.info("The Dp application has been started successfully!");
        return applicationBuilder;
    }

}

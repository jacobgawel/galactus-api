package com.galactus;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GalactusApiApplication {

    static void main(String[] args) {
        var application = new SpringApplication(GalactusApiApplication.class);
        application.setBannerMode(Banner.Mode.LOG);
        application.run(args);
    }

}

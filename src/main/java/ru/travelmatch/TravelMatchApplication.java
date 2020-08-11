package ru.travelmatch;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 09.06.2020
 * @link https://github.com/Centnerman
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class TravelMatchApplication {

//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize("5120KB");
//        factory.setMaxRequestSize("5120KB");
//        return factory.createMultipartConfig();
//    }

    public static void main(String[] args) {
        SpringApplication.run(TravelMatchApplication.class, args);
    }

}

package com.sunnyws.license.creator;

import com.sunnyws.license.creator.param.LicenseCreatorParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * - @Description:  CreatorApplication
 * - @Author qinlang
 * - @Date 2021/8/18
 * - @Version 1.0
 **/

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(LicenseCreatorParam.class)
public class CreatorApplication implements WebMvcConfigurer {
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(CreatorApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Dict is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "\n\t" +
                "Swagger-UI: \t\thttp://" + ip + ":" + port + path + "swagger-ui.html\n" +
                "----------------------------------------------------------");
    }

}

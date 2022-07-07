package com.yozdemir.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.yozdemir.config.DataSourceConfig;
import com.yozdemir.config.SwaggerConfig;

/**
 * 
 * @author yusuf ozdemir
 * Comments rules
 * Always try to explain yourself in code.
 * Don't be redundant.
 * Don't add obvious noise.
 * Don't use closing brace comments.
 * Don't comment out code. Just remove.
 * Use as explanation of intent.
 * Use as clarification of code.
 * Use as warning of consequences.
 *
 */
@SpringBootApplication(scanBasePackages = { "com.yozdemir.controller", "com.yozdemir.facade", "com.yozdemir.service",
		"com.yozdemir.repository", "com.yozdemir.config" })
@Import({ DataSourceConfig.class, SwaggerConfig.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}

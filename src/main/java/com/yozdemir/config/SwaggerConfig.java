package com.yozdemir.config;

import java.io.FileReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 * @author yusuf ozdemir
 *
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() throws Exception {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().paths(PathSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error.*"))).build();
	}

	@Bean
	public ApiInfo apiInfo() throws Exception {
		final ApiInfoBuilder builder = new ApiInfoBuilder();

		String projectName = "";
		String projectDesc = "";

		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model = reader.read(new FileReader("pom.xml"));

		projectName = model.getName();
		projectDesc = model.getDescription();

		builder.title(projectName).description(projectDesc);
		return builder.build();
	}
}

package com.zcpure.foreign.trade.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	
	@Bean
	public Docket swaggerSpringfoxDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zcpure.foreign.trade.user.controller"))
				.paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("客户 供应商 用户接口文档")
				.description("客户 供应商 用户管理服务")
				.termsOfServiceUrl("http://zcpure.com/")
				.version("1.0")
				.build();
	}
}

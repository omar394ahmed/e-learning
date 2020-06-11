package com.example.demo;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.*;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class DemoApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
    @Bean
	public Docket swaggerConfiguration(){
		 return new Docket(DocumentationType.SWAGGER_2)
				 .select()
				 .paths(paths())
				 .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
				 .build()
				  .apiInfo(apiInfo);

	}

	private Predicate<String> paths() {
		return or(
				regex("/courses.*"),
				regex("/users.*"));
	}

	Contact contact = new Contact(
			"Omar Hassanin",
			"",
			"omar394ahmed@gmail.com"
	);

	List<VendorExtension> vendorExtensions = new ArrayList<>();

	ApiInfo apiInfo = new ApiInfo(
			"Et3lm app",
			"This pages documents Et3lm app RESTful Web Service endpoints", "1.0",
			"http://www.appsdeveloperblof.com/service.html", contact,
			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",vendorExtensions);
}

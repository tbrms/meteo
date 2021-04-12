package toma.meteo.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()     
                .apis(RequestHandlerSelectors.basePackage("toma.meteo.controller"))
                .paths(PathSelectors.any())
                .build();
    }
	
	// Swagger entete custom
	  private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	    	.title("Spring Boot REST API Documentation pour projet meteo")
	        .description("REST APIs pour projet meteo")
	        .version("1.0").build();
	  }

}

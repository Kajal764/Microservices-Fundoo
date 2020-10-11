package com.fundoo.user.configuration;


import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fundoo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }


//    @Bean
//    public Docket api() throws IOException, XmlPullParserException {
//        MavenXpp3Reader reader = new MavenXpp3Reader();
//        Model model = reader.read(new FileReader("pom.xml"));
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("pl.piomin.microservices.advanced.account.api"))
//                .paths(PathSelectors.any())
//                .build().apiInfo(new ApiInfo("Account Service Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, new Contact("Piotr Mińkowski", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null));
//    }

    //    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.fundoo"))
//                .paths(regex("/fundoo/user.*"))
//                .build()
//                .apiInfo(metaData());
//    }
//
    private ApiInfo metaData() {
        return new ApiInfo(
                "Fundoo note api",

                "Fundoo note is An online note project containing various notes along with other details ." +
                        "This project is developed using java for Back-end and Angular for Front-end. " +
                        "The user may create desired note ." +
                        "User may even collaborate another user for note ." +
                        "User can set reminder for particular note . and also set label",
                "1.0",
                "http://www.bridgelabz.com",
                new Contact("Bridgelabz", "http://www.bridgelabz.com", "contactus@bridgelabz.com"),
                "License of API", "http://www.bridgelabz.com", Collections.emptyList());
    }

}

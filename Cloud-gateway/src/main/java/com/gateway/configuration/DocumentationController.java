//package com.gateway.configuration;
//
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Primary
//@EnableAutoConfiguration
//public class DocumentationController implements SwaggerResourcesProvider {
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
////        resources.add(swaggerResource("user-service", "/api/fundoo/user/v2/api-docs", "2.3.4"));
//        resources.add(swaggerResource("note-service", "/api/fundoo/note/v2/api-docs", "2.3.4"));
//
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location, String version) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion(version);
//        return swaggerResource;
//    }
//
//}
//

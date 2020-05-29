package com.lab8.control;




import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;


@ApplicationPath("/service")
public class Controllers extends Application {
    public Controllers(@Context ServletConfig servletConfig) {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Todo API");
        beanConfig.setBasePath("/todo/service");
        beanConfig.setResourcePackage("com.synaptik.javaee");
        beanConfig.setScan(true);
    }

}

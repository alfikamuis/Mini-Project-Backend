package com.alfika.backendecommerce.configuration;

import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestControllerConfig implements RepositoryRestConfigurer {

    /*
    //disable method POST,PUT,DELETE for product and product_category
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] dissableActions = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(dissableActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(dissableActions)));

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(dissableActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(dissableActions)));
    }

     */
}

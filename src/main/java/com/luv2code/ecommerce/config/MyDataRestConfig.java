package com.luv2code.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] theUnsupportedActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
		
		// disable HTTP methods for Product: PUT, POST and DELETE
		config.getExposureConfiguration()
		.forDomainType(Product.class)
		.withItemExposure((metdata, httpmethods) -> httpmethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata, httpmethods) -> httpmethods.disable(theUnsupportedActions));

		// disable HTTP methods for ProductCategory: PUT, POST and DELETE
		config.getExposureConfiguration()
		.forDomainType(ProductCategory.class)
		.withItemExposure((metdata, httpmethods) -> httpmethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata, httpmethods) -> httpmethods.disable(theUnsupportedActions));
		
		exposeIds(config);
	
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {
		Set<EntityType<?>> entities= entityManager.getMetamodel().getEntities();
		
		List<Class> entityClasses = new ArrayList<Class>();
		
		for(EntityType tempEntityType: entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		Class[] domainTypes=entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}
	
	
	
	

}

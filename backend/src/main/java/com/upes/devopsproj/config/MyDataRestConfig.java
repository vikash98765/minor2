package com.upes.devopsproj.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.upes.devopsproj.entity.Product;
import com.upes.devopsproj.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	private EntityManager entityManager;

	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}



	public void configureRepositoryConfiguration(RepositoryRestConfiguration config)
	{
		  HttpMethod[] theUnsupportedActions= {HttpMethod.PUT,HttpMethod.POST, HttpMethod.DELETE,HttpMethod.PATCH};
		  
		  config.getExposureConfiguration()
		  .forDomainType(Product.class )
		  .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
		  .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
		  
//product category
		  config.getExposureConfiguration()
		  .forDomainType(ProductCategory.class )
		  .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
		  .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));


		exposeIds(config);

	}
	private void exposeIds(RepositoryRestConfiguration config) {

		// expose entity ids
		//

		// - get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		// - create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();

		// - get the entity types for the entities
		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}

		// - expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}
	

}

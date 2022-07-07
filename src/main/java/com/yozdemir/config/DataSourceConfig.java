package com.yozdemir.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import liquibase.integration.spring.SpringLiquibase;

/**
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 * 
 * @author yusuf ozdemir 
 * Design rules 
 * Keep configurable data at high levels.
 * Prefer polymorphism to if/else or switch/case. 
 * Separate multi-threading code. 
 * Prevent over-configurability. 
 * Use dependency injection. 
 * Follow Law of Demeter. A class should know only its direct dependencies.
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = ApplicationConstants.ENTITY_MANAGER_FACTORY, transactionManagerRef = ApplicationConstants.TRANSACTION_MANAGER, basePackages = "com.yozdemir")
public class DataSourceConfig {

	@Autowired
	private Environment env; // Properties will be available through env

	@Bean
	public DataSource getAuthorizationDataSource() {
		return DataSourceBuilder.create().driverClassName(env.getProperty("spring.datasource.driverClassName"))
				.url(env.getProperty("spring.datasource.url")).username(env.getProperty("spring.datasource.username"))
				.password(env.getProperty("spring.datasource.password")).build();
	}

	@Bean(name = ApplicationConstants.TRANSACTION_MANAGER)
	public PlatformTransactionManager baseTransactionManager() {
		EntityManagerFactory factory = getBaseEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean(name = ApplicationConstants.ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean getBaseEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(getAuthorizationDataSource());
		factory.setPackagesToScan("com.yozdemir.domain");
		factory.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		factory.setPersistenceUnitName(ApplicationConstants.PERSISTENCE_UNIT_NAME);

		Properties jpaProperties = new Properties();
		jpaProperties.put("eclipselink.weaving", "false");
		jpaProperties.put(ApplicationConstants.JPA_PROPERTIES_DATABASE_ACTION,
				env.getProperty(ApplicationConstants.JPA_PROPERTIES_DATABASE_ACTION));

		factory.setJpaProperties(jpaProperties);
		return factory;
	}

	@Bean
	public DataSourceInitializer authorizationDataSourceInitializer() {
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(getAuthorizationDataSource());
		dataSourceInitializer.setEnabled(true);
		return dataSourceInitializer;
	}

//	@Bean
//	public SpringLiquibase liquibase() {
//		SpringLiquibase liquibase = new SpringLiquibase();
//		liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
//		liquibase.setDataSource(getAuthorizationDataSource());
//		return liquibase;
//	}

}
/**
 * 
 */
package com.madoka.sunb0002.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Sun Bo
 *
 */
@Configuration
@EnableJpaRepositories(transactionManagerRef = "sbshop-txnmgr", basePackages = { "com.madoka.sunb0002.repositories" })
@EnableTransactionManagement
public class RepositoryConfig {

	@Value("${app.database.type}")
	private Database databaseType = null;

	@Value("${app.database.showsql}")
	private boolean databaseShowSql = false;

	@Value("${app.database.generateddl}")
	private boolean databaseGenerateDdl = false;

	@Value("${app.database.jndi}")
	private String databaseJndi = null;

	/**
	 * JNDI is configured in Tomcat server.xml, context section.
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource(databaseJndi);
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		// JpaVendorAdapter
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(databaseType);
		vendorAdapter.setShowSql(databaseShowSql);
		vendorAdapter.setGenerateDdl(databaseGenerateDdl);

		// EntityManagerFactory. Entities for scanning
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.madoka.sunb0002.repositories.entities");
		factory.setDataSource(dataSource());
		if (databaseGenerateDdl) {
			Properties jpaProperties = new Properties();
			jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
			factory.setJpaProperties(jpaProperties);
		}
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean("sbshop-entitymgr")
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Bean("sbshop-txnmgr")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

}

package com.springboot.testWithHsql;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.springboot.ceshi.reporisty",
        entityManagerFactoryRef = "secondEntityManagerFactoryBean",
transactionManagerRef = "secondTransactionManager")
public class ASecondHqslConfig {

    @Bean("secondTransactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);
        return jpaTransactionManager;
    }

    @Bean("secondEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(@Qualifier("dataSource1") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setPersistenceUnitName("default");
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory.setPackagesToScan("com.springboot.ceshi.model");

        entityManagerFactory.setJpaPropertyMap(hibernateJpaProperties());

        return entityManagerFactory;
    }

    @Bean("dataSource1")
    @Qualifier("dataSource1")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private Map<String, ?> hibernateJpaProperties() {

        HashMap<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
properties.put("hibernate.format_sql", "false");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.c3p0.min_size", "2");
        properties.put("hibernate.c3p0.max_size", "5");
        properties.put("hibernate.c3p0.timeout", "300"); // 5mins


        return properties;
    }
}

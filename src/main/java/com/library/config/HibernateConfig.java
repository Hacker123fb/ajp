package com.library.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * HibernateConfig - Hibernate and DataSource configuration.
 *
 * Responsibilities:
 * - Configures MySQL DataSource (Spring's DriverManagerDataSource)
 * - Configures Hibernate SessionFactory
 * - Configures Spring's HibernateTransactionManager
 *
 * NOTE: The DataSource is used ONLY by Hibernate internally.
 * Application code must NEVER use JDBC directly.
 */
@Configuration
public class HibernateConfig {

    /**
     * MySQL DataSource using Spring's standard DriverManagerDataSource.
     * Change the URL, username, and password to match your MySQL setup.
     */

@Bean
public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl(
        "jdbc:mysql://localhost:3306/library_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC"
    );
    dataSource.setUsername("root");
    dataSource.setPassword("pavan");
    return dataSource;
}

    /**
     * Hibernate SessionFactory.
     * Scans com.library.entity for @Entity classes.
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.library.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /**
     * Hibernate properties.
     * hbm2ddl.auto = update  -> creates/updates tables automatically.
     *                          Change to "validate" in production.
     */
    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.show_sql", "false");
        props.setProperty("hibernate.format_sql", "false");
        return props;
    }

    /**
     * Spring Transaction Manager that uses the Hibernate SessionFactory.
     * This enables @Transactional to work on service methods.
     */
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}

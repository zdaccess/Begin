package school21.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver.name}")
    private String driver;

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username,
                                                           password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return hikariConfig;
    }

    @Bean
    public HikariDataSource dataSourceHikari() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public UsersRepositoryJdbcImpl usersRepositoryJdbc() {
        return new UsersRepositoryJdbcImpl(dataSourceHikari());
    }

    @Bean
    public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl(dataSource());
    }

    @Bean
    public UsersServiceImpl usersServiceImpl() {
        return new UsersServiceImpl(usersRepositoryJdbc());
    }

}

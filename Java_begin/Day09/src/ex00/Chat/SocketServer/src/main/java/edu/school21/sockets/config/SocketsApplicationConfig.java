package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {
    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver.name}")
    private String driver;

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
    public UsersRepositoryImpl usersRepositoryJdbcTemplate() {
        return new UsersRepositoryImpl(dataSourceHikari());
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsersServiceImpl usersServiceImpl() {
        return new UsersServiceImpl(usersRepositoryJdbcTemplate(), bCryptPasswordEncoder());
    }

}
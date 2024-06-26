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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean("usersRepositoryJdbc")
    public UsersServiceImpl usersServiceImplForJdbc() {
        return new UsersServiceImpl(new UsersRepositoryJdbcImpl(dataSource()));
    }

    @Bean("usersRepositoryJdbcTemplate")
    public UsersServiceImpl usersServiceImplForJdbcTemplate() {
        return new UsersServiceImpl(new UsersRepositoryJdbcTemplateImpl(dataSource()));
    }

}

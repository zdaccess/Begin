package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest{
    private UsersService usersServiceImpl;
    private UsersService usersServiceImplTemplate;

    @BeforeEach
    void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext
                (TestApplicationConfig.class);
        DataSource dataSource = context.getBean("dataSource",
                DataSource.class);
        String command = "DROP TABLE IF EXISTS users;\n" +
                "CREATE TABLE IF NOT EXISTS users(" +
                "id serial PRIMARY KEY,\n" +
                "email VARCHAR(50) NOT NULL,\n" +
                "password VARCHAR(50) NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement resultSetThird =
                    connection.prepareStatement(command);
            resultSetThird.executeUpdate();
            resultSetThird.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }

        usersServiceImpl = context.getBean("usersServiceImplForJdbc",
                UsersService.class);
        usersServiceImplTemplate = context.getBean("usersServiceImplForJdbcTemplate",
                UsersService.class);
    }

    @Test
     public void checkUsersServiceImpl() {
        assertNotNull (usersServiceImpl.signUp("test1@mail.ru"));
    }

    @Test
    public void checkUsersServiceImplTemplate() {
        assertNotNull (usersServiceImpl.signUp("test2@mail.ru"));
    }
}

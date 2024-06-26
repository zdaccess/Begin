package school21.spring.service.repositories;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.models.User;
@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private List<User> users;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource")DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        int countUser = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM users WHERE id = ?",
                new Object[] {id}, Integer.class
        );
        if (countUser == 0)
            return Optional.empty();
        User user = jdbcTemplate.queryForObject(
                "SELECT id, email, password FROM users WHERE id = ?",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setEmail(resultSet.getString("email"));
                    newuser.setPassword(
                            UUID.fromString(resultSet.getString("password")));
                    return newuser;
                },
                id
        );
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        users = this.jdbcTemplate.query(
                "SELECT id, email, password FROM users",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setEmail(resultSet.getString("email"));
                    newuser.setPassword(
                            UUID.fromString(resultSet.getString("password")));
                    return newuser;
                });
        return users;
    }

    @Override
    public void save(User entity) {
        if (entity.getIdentifier() == null || entity.getEmail().isEmpty()
        || String.valueOf(entity.getPassword()).isEmpty()) {
            System.out.println("Ошибка! Необходимо заполнить все поля для этого пользователя");
        } else {
            if (entity.getIdentifier() != null) {
                this.jdbcTemplate.update(
                        "INSERT INTO users(id, email, password) VALUES (?, ?, ?)",
                        entity.getIdentifier(), entity.getEmail(), entity.getPassword());
            }
        }
    }

    @Override
    public void update(User entity) {
        if (entity.getEmail().isEmpty() || String.valueOf(entity.getPassword()).isEmpty()
        || entity.getIdentifier() == null) {
            System.out.println("Ошибка! Необходимо заполнить все поля для этого пользователя");
        } else {
            this.jdbcTemplate.update(
                    "UPDATE users SET email = ?, password = ? WHERE id = ?",
                    entity.getEmail(), entity.getPassword(), entity.getIdentifier());
        }
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(
                "DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        int countUser = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM users WHERE email = ?",
                new Object[] {email}, Integer.class
        );
        if (countUser == 0)
            return Optional.empty();
        User user = jdbcTemplate.queryForObject(
                "SELECT id, email, password FROM users WHERE email = ?",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setEmail(resultSet.getString("email"));
                    newuser.setPassword(
                            UUID.fromString(resultSet.getString("password")));
                    return newuser;
                },
                email
        );
        return Optional.of(user);
    }
}

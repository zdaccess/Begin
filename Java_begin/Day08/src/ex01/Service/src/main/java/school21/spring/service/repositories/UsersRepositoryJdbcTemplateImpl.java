package school21.spring.service.repositories;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import school21.spring.service.models.User;
import school21.spring.service.models.User;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private List<User> users;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
                "SELECT id, email FROM users WHERE id = ?",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setEmail(resultSet.getString("email"));
                    return newuser;
                },
                id
        );
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        users = this.jdbcTemplate.query(
                "SELECT id, email FROM users",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setEmail(resultSet.getString("email"));
                    return newuser;
                });
        return users;
    }

    @Override
    public void save(User entity) {
        this.jdbcTemplate.update(
                "INSERT INTO users(id, email) VALUES (?, ?)",
                entity.getIdentifier(), entity.getEmail());
    }

    @Override
    public void update(User entity) {
        this.jdbcTemplate.update(
                "UPDATE users SET email = ? WHERE id = ?",
                entity.getEmail(), entity.getIdentifier());
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
                "SELECT id, email FROM users WHERE email = ?",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setEmail(resultSet.getString("email"));
                    return newuser;
                },
                email
        );
        return Optional.of(user);
    }
}

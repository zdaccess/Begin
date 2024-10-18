package edu.school21.sockets;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private List<User> users;

    @Autowired
    public UsersRepositoryImpl(@Qualifier("dataSourceHikari")
                                   DataSource dataSource) {
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
                "SELECT id, name, password FROM users WHERE id = ?",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setUserName(resultSet.getString("name"));
                    newuser.setPassword(resultSet.getString("password"));
                    return newuser;
                }, id
        );
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        users = this.jdbcTemplate.query(
                "SELECT id, name, password FROM users",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setUserName(resultSet.getString("name"));
                    newuser.setPassword(resultSet.getString("password"));
                    return newuser;
                });
        return users;
    }

    @Override
    public void save(User entity) {
        if (!entity.getUserName().isEmpty() && !entity.getPassword().isEmpty()) {
            this.jdbcTemplate.update(
                    "INSERT INTO users(name, password) VALUES (?, ?)",
                    entity.getUserName(), entity.getPassword());
        }
    }

    @Override
    public void update(User entity) {
        this.jdbcTemplate.update(
                "UPDATE users SET name = ? WHERE id = ?",
                entity.getUserName(), entity.getIdentifier());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(
                "DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public Optional<User> findByName(String name) {
        int countUser = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM users WHERE name = ?",
                new Object[] {name}, Integer.class
        );
        if (countUser == 0)
            return Optional.empty();
        User user = jdbcTemplate.queryForObject(
                "SELECT id, name, password FROM users WHERE name = ?",
                (resultSet, rowNum) -> {
                    User newuser = new User();
                    newuser.setIdentifier(resultSet.getLong("id"));
                    newuser.setUserName(resultSet.getString("name"));
                    newuser.setPassword(resultSet.getString("password"));
                    return newuser;
                },
                name
        );
        return Optional.of(user);
    }
}

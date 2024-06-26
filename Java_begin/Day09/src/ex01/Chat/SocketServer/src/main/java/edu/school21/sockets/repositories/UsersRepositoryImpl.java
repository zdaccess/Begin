package edu.school21.sockets.repositories;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private List<User> users;

    @Autowired
    public UsersRepositoryImpl(@Qualifier("dataSourceHikari")DataSource dataSource) {
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
                },
                id
        );
        return Optional.of(user);
    }

//    @Override
//    public List<User> findAllName(String name) {
//        users = this.jdbcTemplate.query(
//                "SELECT id, name, password FROM users WHERE name = ?",
//                (resultSet, rowNum) -> {
//                    User newuser = new User();
//                    newuser.setIdentifier(resultSet.getLong("id"));
//                    newuser.setUserName(resultSet.getString("name"));
//                    newuser.setPassword(resultSet.getString("password"));
//                    return newuser;
//                }, name);
//        return users;
//    }

    @Override
    public void save(User entity) {

        if (entity.getIdentifier() != null) {
            this.jdbcTemplate.update(
                    "INSERT INTO users(id, name, password) VALUES (?, ?, ?)",
                    entity.getIdentifier(), entity.getUserName(), entity.getPassword());
        } else {
            int countUser = jdbcTemplate.queryForObject(
                    "SELECT count(*) FROM users", Integer.class
            );
            this.jdbcTemplate.update(
                    "INSERT INTO users(id, name, password) VALUES (?, ?, ?)",
                    Long.valueOf(countUser + 1), entity.getUserName(),
                    entity.getPassword());
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

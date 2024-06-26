package school21.spring.service.repositories;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import school21.spring.service.models.User;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource    dataSource;
    private List<User>    users;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        users = new ArrayList<>();
        String command = "DROP TABLE IF EXISTS users;\n" +
                        "CREATE TABLE IF NOT EXISTS users(" +
                        "id serial PRIMARY KEY,\n" +
                        "email VARCHAR(50) NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement resultSetThird =
                    connection.prepareStatement(command);
            resultSetThird.executeUpdate();
            resultSetThird.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = new User();
        String command = "SELECT * FROM users WHERE id = " + id;
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            int count = 0;
            while (resultSet.next()) {
                try {
                    user.setIdentifier(resultSet.getLong(1));
                    user.setEmail(resultSet.getString(2));
                    count++;
                } catch (NullPointerException e) {}
            }
            if (count == 0)
                return Optional.empty();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        int countUser = 0;
        String command = "SELECT * FROM users\n" +
                        "ORDER BY id ASC";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSetFirst = statement.executeQuery(command);
            while (resultSetFirst.next()) {
                try {
                    countUser++;
                } catch (NullPointerException e) {}
            }
            if (countUser == 0)
                return null;
            resultSetFirst.close();
            ResultSet resultSetSecond = statement.executeQuery(command);
            User[] user = new User[countUser];
            countUser = 0;
            while (resultSetSecond.next()) {
                try {
                    user[countUser] = new User();
                    user[countUser].setIdentifier(resultSetSecond.getLong(1));
                    user[countUser].setEmail(resultSetSecond.getString(2));
                    users.add(user[countUser]);
                    countUser++;
                } catch (NullPointerException e) {}
            }
            resultSetSecond.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void save(User entity) {
        String command = "INSERT INTO users(id, email) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement resultSet =
                    connection.prepareStatement(command);
            resultSet.setLong(1, entity.getIdentifier());
            resultSet.setString(2, entity.getEmail());
            resultSet.executeUpdate();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        String command = "UPDATE users SET email = '" + entity.getEmail()
                        + "' WHERE id = " + entity.getIdentifier();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement resultSet =
                    connection.prepareStatement(command);
            resultSet.executeUpdate();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String command = "DELETE FROM users WHERE id = " + id;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement resultSet =
                    connection.prepareStatement(command);
            resultSet.executeUpdate();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = new User();
        String command = "SELECT * FROM users WHERE email = '" + email + "'";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            if (resultSet.wasNull())
                return Optional.empty();
            while (resultSet.next()) {
                try {
                    user.setIdentifier(resultSet.getLong(1));
                    user.setEmail(resultSet.getString(2));
                } catch (NullPointerException e) {}
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(user);
    }
}

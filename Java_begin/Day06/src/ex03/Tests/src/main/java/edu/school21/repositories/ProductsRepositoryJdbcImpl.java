package edu.school21.numbers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{
    private List<Product> list;
    private DataSource data;

    public ProductsRepositoryJdbcImpl(DataSource data) {
        this.data = data;
    }

    public List<Product> findAll() {
        list = new ArrayList<>();
        String command = "SELECT * FROM products.list";
        try (Connection connection = data.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                list.add(new Product(
                        Long.valueOf(resultSet.getInt(1)),
                        resultSet.getString(2),
                        resultSet.getInt(3))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Optional<Product> findById(Long id) {
        Product product = null;
        String command = "SELECT * FROM products.list WHERE id = " + id;
        try (Connection connection = data.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                product = new Product(
                        (long)resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(product);
    }

    public void update(Product product) {
        String command = "UPDATE products.list SET name = '"
                + product.getName() + "', price = "
                + product.getPrice() + " WHERE "
                + "id = " + product.getIdentifier();
        try (Connection connection = data.getConnection()) {
            PreparedStatement preStatement = connection.prepareStatement(command);
            preStatement.executeUpdate();
            preStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void save(Product product) {
        String command = "INSERT INTO products.list(name, price) VALUES (?, ?)";
        try (Connection connection = data.getConnection()) {
            PreparedStatement resultSetThird =
                    connection.prepareStatement(command);
            resultSetThird.setString(1, product.getName());
            resultSetThird.setLong(2,product.getPrice());
            resultSetThird.executeUpdate();
            resultSetThird.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Long id) {
        String command = "DELETE FROM products.list WHERE id = " + id;
        try (Connection connection = data.getConnection()) {
            PreparedStatement preStatement = connection.prepareStatement(command);
            preStatement.executeUpdate();
            preStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

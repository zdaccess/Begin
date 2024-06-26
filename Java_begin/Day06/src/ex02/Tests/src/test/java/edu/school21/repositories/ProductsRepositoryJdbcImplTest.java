package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "apple", 100),
            new Product(1L, "cookie", 200),
            new Product(2L, "juice", 300),
            new Product(3L, "cake", 400),
            new Product(4L, "candies", 500)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "juice", 300);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "candies", 780);

    @BeforeEach
    void init() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder =
                new EmbeddedDatabaseBuilder();
        DataSource dataSource = embeddedDatabaseBuilder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void checkFindAll() {
        List<Product> list = productsRepositoryJdbc.findAll();
        Assertions.assertEquals(list, EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    void checkFindId() {
        Optional<Product> product = productsRepositoryJdbc.findById(2L);
        Assertions.assertEquals(product,
                                Optional.of(EXPECTED_FIND_BY_ID_PRODUCT));
    }

    @Test
    void checkUpdateProduct() {
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> product =
                productsRepositoryJdbc
                        .findById(EXPECTED_UPDATED_PRODUCT.getIdentifier());
        Assertions.assertEquals(product,
                                Optional.of(EXPECTED_UPDATED_PRODUCT));
    }

    @Test
    void checkSaveProduct() {
        List<Product> list = productsRepositoryJdbc.findAll();
        Product addedProduct = new Product(Long.valueOf(list.size()),
                                           "snickers", 900);
        productsRepositoryJdbc.save(addedProduct);
        List<Product> listCheck = productsRepositoryJdbc.findAll();
        Assertions.assertEquals(addedProduct,
                                listCheck.get(listCheck.size() - 1));
    }

    @Test
    void checkDeleteProduct() {
        List<Product> list = productsRepositoryJdbc.findAll();
        Optional<Product> product =
                productsRepositoryJdbc.findById(Long.valueOf(list.size() - 1));
        productsRepositoryJdbc.delete(product.get().getIdentifier());
        List<Product> listCheck = productsRepositoryJdbc.findAll();
        Assertions.assertNotEquals(
                product.get().getIdentifier(),listCheck.get(listCheck.size() - 1)
                        .getIdentifier()
        );
    }
}

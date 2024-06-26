package edu.school21.numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    private EmbeddedDatabaseBuilder embeddedDatabaseBuilder;
    @BeforeEach
    void init() {
        embeddedDatabaseBuilder =
                new EmbeddedDatabaseBuilder();
        embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL).addScript(
                "schema.sql").addScript("data.sql").build();
    }

    @Test
    void getConnection() throws SQLException {
        Assertions.assertNotNull(embeddedDatabaseBuilder.build().getConnection());
    }
}

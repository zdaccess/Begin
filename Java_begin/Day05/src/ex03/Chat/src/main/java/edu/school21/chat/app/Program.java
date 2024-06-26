package edu.school21.chat;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = connectData();

        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        File[] files = new File("src/main/resources").listFiles();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            for (int i = files.length - 1; i >= 0; i--) {
                File file = files[i];
                System.out.println(file.getName());
                if (file.isFile() && file.getName().endsWith(
                        ".sql")) {
                    try (FileInputStream inputStream =
                                 new FileInputStream(file)) {
                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        String command = "";
                        while ((line = reader.readLine()) != null) {
                            command = command + line + "\n";
                        }
                        statement.execute(command);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database " + e.getMessage());
        }
        Optional<Message> messageOptional = messagesRepository.findById(4L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDateTime(null);
            messagesRepository.update(message);
        }
    }

    public static DataSource connectData() {
        HikariConfig connectData = new HikariConfig();
        connectData.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        connectData.setUsername("postgres");
        connectData.setPassword("123456");

        return new HikariDataSource(connectData);
    }
}
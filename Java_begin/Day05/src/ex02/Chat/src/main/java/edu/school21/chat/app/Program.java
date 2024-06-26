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
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        HikariConfig connectData = new HikariConfig();
        connectData.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        connectData.setUsername("postgres");
        connectData.setPassword("123456");

        DataSource dataSource = new HikariDataSource(connectData);
        MessagesRepositoryJdbcImpl repositoryJdbc =
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
        User creator = new User(1L, "anna", "123456", new ArrayList<>(),
                       new ArrayList<>());
        User author = creator;
        Room room = new Room(1L, "room_1", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello!",
                          LocalDateTime.now());
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        messagesRepository.save(message);
    }
}

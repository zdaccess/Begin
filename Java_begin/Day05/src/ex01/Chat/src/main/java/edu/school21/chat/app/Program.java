package edu.school21.chat;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message ID");
        String str = scanner.nextLine();
        String[] splitStr = str.split(" ");
        if (splitStr.length == 1 && splitStr[0].matches("[0-9]+")) {
            System.out.println(repositoryJdbc.findById(
                    Long.valueOf(splitStr[0])));
        } else {
            System.out.println("Error! Enter only one positive number!");
        }
    }
}

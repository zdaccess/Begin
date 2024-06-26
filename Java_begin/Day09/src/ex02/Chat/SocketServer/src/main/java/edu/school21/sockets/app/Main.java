package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            String[] splitArgs = args[0].split("=");
            if (splitArgs[0].equals("--port")) {
                if (splitArgs[1].matches("\\d+")) {
                    try {
                        Server server = new Server();
                        server.run(Integer.parseInt(splitArgs[1]));
                    } catch (IOException | NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Ошибка!");
                }
            } else {
                System.out.println("Ошибка!");
            }
        } else {
            System.out.println("Ошибка!");
        }
    }
}

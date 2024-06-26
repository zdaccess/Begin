package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            String[] splitArgs = args[0].split("=");
            if (splitArgs[0].equals("--port")) {
                if (splitArgs[1].matches("\\d+")) {
                    Server server = new Server(Integer.parseInt(splitArgs[1]));
                    server.startServer();
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

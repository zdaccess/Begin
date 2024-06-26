package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class Server {
    private  JdbcTemplate               jdbcTemplate;
    private  ApplicationContext         context;
    private HashMap<String, Integer>    checkUsers;

    public Server() {
        checkUsers = new HashMap<>();
    }



    public void run(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            addDB();
            int indexList = 0;
            while (true) {
                Socket socketClient = serverSocket.accept();
                    Sockets socket = new Sockets(
                            socketClient, context, checkUsers, indexList);
                    indexList++;
            }
        }
    }

    public void addDB() {
        SocketsApplicationConfig applicationConfig = new SocketsApplicationConfig();
        context = new AnnotationConfigApplicationContext(
                applicationConfig.getClass());
        DataSource dataSource = context.getBean("dataSourceHikari",
                DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS messages;\n" +
                "DROP TABLE IF EXISTS users;\n" +
                "CREATE TABLE IF NOT EXISTS users(" +
                "id serial PRIMARY KEY,\n" +
                "name VARCHAR(50) NOT NULL,\n" +
                "password VARCHAR NOT NULL);\n" +
                "CREATE TABLE IF NOT EXISTS messages(" +
                "sender bigint NOT NULL,\n" +
                "message text NOT NULL,\n" +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "FOREIGN KEY (sender) REFERENCES users(id));");
    }
}

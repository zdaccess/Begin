package edu.school21.sockets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private  JdbcTemplate                   jdbcTemplate;
    private  ApplicationContext             context;
    private static HashMap<Sockets, String> userList;
    private HashMap<String, Integer>        userData;
    private static List<Room>               roomList;
    private ServerSocket                    serverSocket;

    public Server() {
        userData = new HashMap<>();
        userList = new HashMap<>();
        roomList = new ArrayList<>();
    }

    public void run(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            addDB();
            int indexList = 0;
            while (true) {
                removeList("delete");
                Socket socketClient = serverSocket.accept();
                userList.put(
                        new Sockets(socketClient, context, userData,
                                indexList, roomList), "off"
                );
                indexList++;
            }
        } catch (IOException e) {
            serverSocket.close();
        }
    }

    public void addDB() {
        SocketsApplicationConfig applicationConfig = new SocketsApplicationConfig();
        context = new AnnotationConfigApplicationContext(
                applicationConfig.getClass());
        DataSource dataSource = context.getBean("dataSourceHikari",
                DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS messages CASCADE;\n" +
                "DROP TABLE IF EXISTS rooms CASCADE;\n" +
                "DROP TABLE IF EXISTS users CASCADE;\n" +
                "CREATE TABLE IF NOT EXISTS users(" +
                "id serial PRIMARY KEY,\n" +
                "name VARCHAR(50) NOT NULL,\n" +
                "password VARCHAR NOT NULL);\n" +
                "CREATE TABLE IF NOT EXISTS rooms(" +
                "id serial PRIMARY KEY,\n" +
                "name VARCHAR(50) NOT NULL);\n" +
                "CREATE TABLE IF NOT EXISTS messages(" +
                "id serial PRIMARY KEY,\n" +
                "chat bigint NOT NULL, \n" +
                "sender bigint NOT NULL,\n" +
                "message text NOT NULL,\n" +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "FOREIGN KEY (chat) REFERENCES rooms(id),\n" +
                "FOREIGN KEY (sender) REFERENCES users(id));");
    }

    public static HashMap<Sockets, String> getUserList() {
        return userList;
    }

    public static void addRooms(String name) {
        roomList.add(new Room(name));
    }

    public static void setUserList(Sockets socket, String str) {
        userList.forEach((key, value) -> {
            if (key.equals(socket)) {
                userList.put(socket, str);
            }
        });
    }

    public void removeList(String str) {
        userList.entrySet().removeIf(entry -> str.equals(entry.getValue()));
    }
}

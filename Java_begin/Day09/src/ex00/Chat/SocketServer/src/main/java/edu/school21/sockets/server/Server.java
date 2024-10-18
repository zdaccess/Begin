package edu.school21.sockets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private Socket clientSocket;
    private ServerSocket server;
    private BufferedReader in;
    private PrintWriter out;
    private UsersService usersServiceImpl;

    public Server(int port) {
        this.port = port;
        init();
    }

    public void init() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        SocketsApplicationConfig applicationConfig =
                new SocketsApplicationConfig();
        ApplicationContext context = new AnnotationConfigApplicationContext(
                applicationConfig.getClass()
        );
        usersServiceImpl = context.getBean(
                "usersServiceImpl", UsersService.class
        );
        DataSource dataSource = context.getBean(
                "dataSourceHikari", DataSource.class
        );
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS users;\n"
                + "CREATE TABLE IF NOT EXISTS users("
                + "id serial PRIMARY KEY,\n"
                + "name VARCHAR(50) NOT NULL,\n"
                + "password VARCHAR NOT NULL);");
    }

    public void startServer() throws IOException {
        try {
            server = new ServerSocket(port);
            while (true) {
                try {
                    clientSocket = server.accept();
                    System.out.println("The client has entered!");
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Hello from Server!");
                    String clientMessage;
                    String userName = null;
                    int step = 0;
                    while (true) {
                        clientMessage = in.readLine();
                        if (step == 0) {
                            if (clientMessage.equals("signUp")) {
                                out.println("Enter username:");
                                step++;
                            } else {
                                out.println("Enter the command signUp");
                            }
                        } else if (step == 1) {
                            if (clientMessage != null
                            && !clientMessage.trim().isEmpty()) {
                                userName = clientMessage;
                                out.println("Enter password:");
                                step++;
                            } else {
                                out.println("Empty value! Enter " + "username:");
                            }
                        } else if (step == 2) {
                            if (clientMessage != null
                            && !clientMessage.trim().isEmpty()) {
                                usersServiceImpl.signUp(userName, clientMessage);
                                out.println("Successful!");
                                System.out.println("The client has left!");
                                break;
                            } else {
                                out.println("Empty value! Enter password:");
                            }
                        }
                    }
                } catch (IOException | NullPointerException e){
                    System.out.println("The client has left!");
                } finally {
                    out.close();
                    in.close();
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            server.close();
        }
    }
}

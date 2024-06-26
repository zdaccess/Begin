package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
    private UsersService  usersServiceImpl;

    public Server(int port) {
        this.port = port;
        init();
    }

    public void init () {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        SocketsApplicationConfig applicationConfig = new SocketsApplicationConfig();
        ApplicationContext context = new AnnotationConfigApplicationContext(
                applicationConfig.getClass());
        usersServiceImpl = context.getBean("usersServiceImpl", UsersService.class);
        DataSource dataSource = context.getBean("dataSourceHikari",
                                                DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS users;\n" +
                                     "CREATE TABLE IF NOT EXISTS users(" +
                                     "id serial PRIMARY KEY,\n" +
                                     "name VARCHAR(50) NOT NULL,\n" +
                                     "password VARCHAR NOT NULL);");
    }

    public void startServer() throws BeanNotOfRequiredTypeException {
        try {
            try {
                server = new ServerSocket(port);
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
                                out.println("Введите комманду signUp");
                            }
                        } else if (step == 1) {
                            if (clientMessage != null && !clientMessage.trim().isEmpty()) {
                                userName = clientMessage;
                                out.println("Enter password:");
                                step++;
                            } else {
                                out.println("Пустое значение! Enter " +
                                                    "username:");
                            }
                        } else if (step == 2) {
                            if (clientMessage != null && !clientMessage.trim().isEmpty()) {
                                usersServiceImpl.signUp(userName,
                                                        clientMessage);
                                out.println("Successful!");
                                break;
                            } else {
                                out.println("Пустое значение! Enter password:");
                            }
                        }
                    }
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                server.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода вывода!");
        } catch (NullPointerException e) {
            System.out.println("Сервер завершил работу!");
        }
    }
}

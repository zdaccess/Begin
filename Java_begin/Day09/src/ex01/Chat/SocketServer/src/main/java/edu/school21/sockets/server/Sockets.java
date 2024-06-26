package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.src.main.java.edu.school21.sockets.models.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Sockets extends Thread{
    private BCryptPasswordEncoder       passwordEncoder;
    private HashMap<String, Integer>    checkUsers;
    private final Socket                clientSocket;
    private BufferedReader              in;
    private PrintWriter                 out;
    private UsersService                usersServiceImpl;
    private JdbcTemplate                jdbcTemplate;
    private UsersRepository             usersRepository;
    private String                      userName;
    private final int                   countUser;
    private Optional<User>              user;
    private String                      chatMessage;


    public Sockets(Socket clientSocket, ApplicationContext context,
                   HashMap<String, Integer> checkUsers, int countUser) {
        this.clientSocket = clientSocket;
        DataSource dataSource = context.getBean("dataSourceHikari",
                DataSource.class);
        usersServiceImpl = context.getBean(
                "usersServiceImpl", UsersService.class
        );
        usersRepository = context.getBean(
                "usersRepositoryJdbcTemplate", UsersRepository.class
        );
        passwordEncoder = context.getBean(
                "bCryptPasswordEncoder", BCryptPasswordEncoder.class
        );
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.checkUsers = checkUsers;
        this.countUser = countUser;
        this.chatMessage = "Enter signUp, signIn или Exit";
        start();
    }

    public void outClient (String message) {
        out.println(message);
        out.flush();
    }

    public void stopSocket() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
                in.close();
                out.close();
                for (Sockets socket : Server.getList()) {
                    if (socket.equals(this))
                        socket.interrupt();
                    Server.getList().remove(this);
                }
            }
        } catch (IOException ignored) {}
    }

    public Boolean signIn(String userName, String password) {
        Boolean access = null;
        if (checkUsers.containsKey(userName)) {
            user = usersRepository.findByName(userName);
        } else {
            outClient("Error! A user with this name does not exist!");
            outClient(chatMessage);
            access = false;
        }
        if (passwordEncoder.matches(password, user.get().getPassword())) {
            if (checkUsers.get(userName).equals(0)) {
                checkUsers.put(userName, 1);
                access = true;
            } else {
                outClient("Error! A user with the same name " +
                        "is already in the chat!");
                outClient(chatMessage);
                access = false;
            }
        } else {
            outClient("Error! Password is incorrect!");
            outClient(chatMessage);
            access = false;
        }
        return access;
    }

    public void addBDMessage(String text) {
        if (!text.equals("Exit")) {
            Message message = new Message(
                    user.get().getIdentifier(), text, LocalDateTime.now()
            );
            jdbcTemplate.update(
                    "INSERT INTO messages(sender, message, time) " +
                            "VALUES (?, ?, ?)",
                    message.getSender(), message.getText(),
                    Timestamp.valueOf(message.getDateTime()));
        }

    }

    public void startMessageUser() throws IOException {
        String clientMessage = in.readLine();
        addBDMessage(clientMessage);
        if (clientMessage.equals("Exit"))
            stopSocket();
        for (Sockets find : Server.getList()) {
            if (find != this  && !clientMessage.equals("Exit")) {
                find.outClient(userName + ": " + clientMessage);
            } else if  (find != this && clientMessage.equals("Exit")){
                find.outClient(userName + ": Left the chat!");
            }
        }
    }

    public Boolean startCommandUser () throws IOException {
        String command = null;
        String clientMessage = null;
        int step = 0;
        System.out.println("User #" + countUser +
                ": An unknown user has logged into the server.");
        outClient("Hello from Server!");
        while (true) {
            clientMessage = in.readLine();
            if (step == 0) {
                userName = null;
                if (clientMessage.equals("signUp") ||
                        clientMessage.equals("signIn")) {
                    command = clientMessage;
                    outClient("Enter username:");
                    step++;
                } else {
                    outClient(chatMessage);
                }
            } else if (step == 1) {
                if (clientMessage != null && !clientMessage.trim().isEmpty()) {
                    if (command.equals("signUp")
                    && checkUsers.containsKey(clientMessage)) {
                        outClient("Error! A user with this name exists!");
                        step = 0;
                    } else {
                        userName = clientMessage;
                        outClient("Enter password:");
                        step++;
                    }
                } else {
                    outClient("Blank value! Enter " +
                            "username:");
                }
            } else if (step == 2) {
                if (clientMessage != null && !clientMessage.trim().isEmpty()) {
                    if (command.equals("signUp")) {
                        usersServiceImpl.signUp(userName, clientMessage);
                        checkUsers.put(userName, 0);
                        System.out.println("User #" + countUser +
                                ": Registered a new user - " + userName);
                        outClient("Registration complete!");
                        step = 0;
                    } else {
                        if (signIn(userName, clientMessage)) {
                            System.out.println("User #" + countUser +
                                    ": Logged in as user - " + userName);
                            outClient("Start messaging");
                            return true;
                        } else {
                            step = 0;
                        }
                    }
                } else {
                    outClient("Blank value! Enter password:");
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );
            out = new PrintWriter(
                    new PrintWriter(clientSocket.getOutputStream(), true)
            );
            Boolean access = false;
            while (true) {
                if (access) {
                    startMessageUser();
                } else {
                    access = signUser();
                }
            }
        } catch (IOException e) {
            stopSocket();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            if (userName == null) {
                System.out.println("User #" + countUser +
                        ": Unknown user logged out without authorization.");
            } else {
                System.out.println("User #" + countUser +
                        ": User with name " + userName + " has left.");
            }
        }
    }
}

package edu.school21.sockets;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sockets extends Thread{
    private BCryptPasswordEncoder       passwordEncoder;
    private Socket                      clientSocket;
    private BufferedReader              in;
    private PrintWriter                 out;
    private UsersService                usersServiceImpl;
    private JdbcTemplate                jdbcTemplate;
    private UsersRepository             usersRepository;
    private String                      userName;
    private int                         countUser;
    private Optional<User>              user;
    private String                      chatMessage;
    private HashMap<String, Integer>    userData;


    public Sockets(Socket clientSocket, HashMap<String, Integer> userData,
                   ApplicationContext context, int countUser) {
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
        this.countUser = countUser;
        this.chatMessage = "Enter signUp, signIn or Exit";
        this.userData = userData;
        start();
    }

    public void outClient (String message) {
        out.println(message);
        out.flush();
    }

    public void stopSocket() {
        userData.forEach((key, value) -> {
            if (key.equals(userName)) {
                userData.put(userName, 0);
            }
        });
        try {
                clientSocket.close();
                in.close();
                out.close();
        } catch (IOException ignored) {}
    }

    public boolean signIn(String password) {
        if (userData.containsKey(userName)) {
            user = usersRepository.findByName(userName);
        } else {
            outClient("Error! A user with this name does not exist!");
            stopSocket();
            outClient(chatMessage);
             return false;
        }
        if (passwordEncoder.matches(password, user.get().getPassword())) {
            if (userData.get(userName).equals(0)) {
                userData.put(userName, 1);
                return true;
            } else {
                outClient("Error! A user with the same name " +
                        "is already in the chat!");
                outClient(chatMessage);
                return false;
            }
        } else {
            outClient("Error! Password is incorrect!");
            outClient(chatMessage);
            return false;
        }
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

    public boolean startMessageUser(String str) throws IOException {
        String clientMessage = in.readLine();
        AtomicBoolean checkstatus = new AtomicBoolean(false);
        addBDMessage(clientMessage);
        Server.getUserList().forEach((key, value) -> {
            if (str.equals("start")) {
                if (key != this && "on".equals(value)) {
                    key.outClient("User " + userName + " has entered the chat!");
                }
            }
            if (clientMessage.equals("Exit")) {
                if (key != this && "on".equals(value)) {
                    key.outClient("User " + userName + " left the chat!");
                }
                Server.setUserList(this, "delete");
                checkstatus.set(true);
            } else {
                if (key != this && "on".equals(value)) {
                    key.outClient(userName + ": " + clientMessage);
                }
                checkstatus.set(false);
            }
        });
        return checkstatus.get();
    }

    public void sendClientExit(String msg) {
        if (msg.equals("Exit")) {
            stopSocket();
        }
    }

    public Boolean startCommandUser() throws IOException {
        String command = null;
        String clientMessage = null;
        int step = 0;
        System.out.println("User #" + countUser +
                ": An unknown user has logged into the server.");
        outClient("Hello from Server!");
        while (true) {
            clientMessage = in.readLine();
            sendClientExit(clientMessage);
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
                    && userData.containsKey(clientMessage)) {
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
                        userData.put(userName, 0);
                        System.out.println("User #" + countUser +
                                ": Registered a new user - " + userName);
                        outClient("Registration complete!");
                        step = 0;
                    } else {
                        if (signIn(clientMessage)) {
                            System.out.println("User #" + countUser +
                                    ": Logged in as user - " + userName);
                            outClient("Start messaging");
                            startMessageUser("start");
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
            Boolean exitSocket = false;
            while (true) {
                if (exitSocket) {
                    System.out.println("User #" + countUser + ": Logout");
                    stopSocket();
                    break;
                } else if (access && !exitSocket) {
                    Server.setUserList(this, "on");
                    exitSocket = startMessageUser("");
                } else {
                    access = startCommandUser();
                }
            }
        } catch (IOException | NullPointerException e) {
            if (userName == null) {
                System.out.println("User #" + countUser +
                        ": Unknown user logged out without authorization.");
            } else {
                System.out.println("User #" + countUser +
                        ": User with name " + userName + " has left.");
            }
            stopSocket();
        }
    }
}

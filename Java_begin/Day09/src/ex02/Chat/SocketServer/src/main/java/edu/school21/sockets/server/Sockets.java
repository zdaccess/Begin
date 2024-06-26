package edu.school21.sockets.server;

import edu.school21.sockets.Converter;
import edu.school21.sockets.models.Room;
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
    private Converter                   converter;
    private List<Room>                  roomList;
    private Room                        chooseRoom;


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
        this.chatMessage = "Enter signUp, signIn or Exit";
        this.converter = new Converter();
        this.roomList = new ArrayList<>();
        start();
    }

    public void outClient (String message, String choose) {
        String textJSON = converter.outDeterminerMsg(message, choose);
        System.out.println("server " + textJSON);
        out.println(textJSON);
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
            outClient("Error! A user with this name does not exist!", "");
            outClient(chatMessage, "");
            access = false;
        }
        if (passwordEncoder.matches(password, user.get().getPassword())) {
            if (checkUsers.get(userName).equals(0)) {
                checkUsers.put(userName, 1);
                access = true;
            } else {
                outClient("Error! A user with the same name " +
                        "is already in the chat!", "");
                outClient(chatMessage, "");
                access = false;
            }
        } else {
            outClient("Error! Password is incorrect!", "");
            outClient(chatMessage, "");
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
                find.outClient(userName + ": " + clientMessage, "");
            } else if  (find != this && clientMessage.equals("Exit")){
                find.outClient(userName + ": Left the chat!", "");
            }
        }
    }

    public Boolean signUser() throws IOException {
        String command = null;
        String clientMessage = "";
        String parseMessage = "";
        int step = 0;
        System.out.println("User #" + countUser +
                ": An unknown user has logged into the server.");
        outClient("Hello from Server!", "");
        outClient("1. signIn", "");
        outClient("2. SignUp", "");
        outClient("3. Exit", "");
        while (true) {
            clientMessage = in.readLine();
            System.out.println("client " + clientMessage);
            parseMessage = converter.inMessage(clientMessage, 1);
            System.out.println("parse client " + parseMessage);
            if (step == 0) {
                userName = null;
                if (parseMessage.equals("signUp")
                || parseMessage.equals("signIn")) {
                    command = parseMessage;
                    outClient("Enter username:", "");
                    step++;
                } else {
                    outClient(chatMessage, "");
                }
            } else if (step == 1) {
                if (parseMessage != null && !parseMessage.trim().isEmpty()) {
                    if (command.equals("signUp")
                    && checkUsers.containsKey(parseMessage)) {
                        outClient("Error! A user with this name exists!", "");
                        step = 0;
                    } else {
                        userName = parseMessage;
                        outClient("Enter password:", "");
                        step++;
                    }
                } else {
                    outClient("Blank value! Enter " +
                            "username:", "");
                }
            } else if (step == 2) {
                if (parseMessage != null && !parseMessage.trim().isEmpty()) {
                    if (command.equals("signUp")) {
                        usersServiceImpl.signUp(userName, parseMessage);
                        checkUsers.put(userName, 0);
                        System.out.println("User #" + countUser +
                                ": Registered a new user - " + userName);
                        outClient("Registration complete!", "");
                        step = 0;
                    } else {
                        if (signIn(userName, parseMessage)) {
                            System.out.println("User #" + countUser +
                                    ": Logged in as user - " + userName);
                            outClient("Start messaging", "");
                            return true;
                        } else {
                            step = 0;
                        }
                    }
                } else {
                    outClient("Blank value! Enter password:", "");
                }
            }
        }
    }

//    public Boolean roomChats() throws IOException {
//        Boolean access = false;
//        String command = null;
//        String clientMessage = null;
//        String parseMessage = null;
//        String choose = null;
//        int step = 0;
//        int count = 0;
//        System.out.println("User #" + countUser +
//                                   ": An unknown user has logged into the server.");
//        outClient("1. Create room\n" +
//                "2. Choose room\n" +
//                "3. Exit\n");
//
//        while (true) {
//            clientMessage = in.readLine();
//            System.out.println(clientMessage);
//            parseMessage = converter.inMessage(clientMessage, 2);
//            if (step == 0) {
//                command = parseMessage;
//                outClient(converter.outMessage(command, ""));
//                step++;
//            } else if (step == 1) {
//                if (command.equals("Create room")) {
//                    outClient(converter.outMessage(command, "Создайте " +
//                            "комнату"));
//                } else if (command.equals("Choose room")) {
//                    choose = "Выберите одну из комнат:\n";
//                    for (Room room : roomList) {
//                        count++;
//                        choose = count + ". " + room.getName() + "\n";
//                    }
//                    choose = (count + 1) + ". Exit";
//                }
//                    outClient(converter.outMessage(command, choose));
//            } else if (step == 2) {
//                if (command.equals("Create room")) {
//                    if (roomList.stream().anyMatch(item -> item.getName().equals(parseMessage))) {
//                        outClient(converter.outMessage(command,
//                                                       parseMessage + " " +
//                                                               "такой чат " +
//                                                               "уже " +
//                                                               "существует"));
//                    } else {
//                        roomList.add(new Room(parseMessage));
//                        outClient(converter.outMessage(command, "Room " + parseMessage + " save"));
//                    }
//                } else if (command.equals("Choose room")) {
//
//                }
//                outClient(converter.outMessage(parseMessage));
//                return false;
//            }
//        return access;
//    }

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
            Boolean checkSign = false;
            while (true) {
//                if (access) {
//                    startMessageUser();
//                } else if (checkSign) {
//                    access = roomChats();
//                } else {
                    checkSign = signUser();
//                }
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

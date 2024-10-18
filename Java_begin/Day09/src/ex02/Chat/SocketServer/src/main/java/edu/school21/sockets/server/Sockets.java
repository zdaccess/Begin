package edu.school21.sockets;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import java.io.*;
import java.net.Socket;
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
    private Optional<Room>              room;
    private Converter                   converter;
    private List<Room>                  roomList;
    private HashMap<String, Integer>    userData;
    private Long                        roomId;
    private Integer                     chooseRoomId;
    private int                         blockNmr;
    private RoomsRepository             roomsRepository;
    private MessagesRepositoryImpl      messagesRepository;

    public Sockets(Socket clientSocket, ApplicationContext context,
                   HashMap<String, Integer> userData, int countUser,
                   List<Room> roomList) {
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
        roomsRepository = context.getBean(
                "roomsRepositoryJdbcTemplate", RoomsRepository.class
        );
        messagesRepository = context.getBean(
                "messagesRepositoryJdbcTemplate", MessagesRepositoryImpl.class
        );
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.countUser = countUser;
        this.converter = new Converter();
        this.roomList = roomList;
        this.userData = userData;
        this.chooseRoomId = null;
        this.blockNmr = 1;
        start();
    }

    public void outClient (String message) {
        String textJSON = converter.outDeterminerMsg(message);
        out.println(textJSON);
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

    public Boolean signIn(String userName, String password) {
        if (userData.containsKey(userName)) {
            user = usersRepository.findByName(userName);
        } else {
            outClient("Error! A user with this name does not exist!");
            return false;
        }
        if (passwordEncoder.matches(password, user.get().getPassword())) {
            if (userData.containsKey(userName)
            && userData.get(userName).equals(0)) {
                converter.setUserId(user.get().getIdentifier());
                userData.put(userName, 1);
                blockNmr++;
                return true;
            } else {
                outClient("Error! A user with the same name " +
                        "is already in the chat!");
                return false;
            }
        } else {
            outClient("Error! Password is incorrect!");
            return false;
        }
    }

    public void addBDMessage(String text) {
        if (!text.equals("Exit")) {
            Message message = new Message(
                    room.get().getId(), user.get().getIdentifier(),
                    text, LocalDateTime.now()
            );
            messagesRepository.save(message);
        }

    }

    public boolean showThirtyMessages() {
        List<String> thirtyMessages= messagesRepository.findMessagesRoom(
                room.get().getId(), user.get().getIdentifier()
        );
        if (thirtyMessages == null)
            return true;
        Server.getUserList().forEach((key, value) -> {
            if (key == this && "on".equals(value)
                    && key.chooseRoomId.equals(chooseRoomId)) {
                if (!thirtyMessages.isEmpty()) {
                    for (String msg : thirtyMessages) {
                        key.outClient(msg);
                    }
                }
            }
        });
        return true;
    }

    public void sendClientExit() {
        if (converter.getCommand().equals("Exit")) {
            outClient("Exit");
        }
    }

    public boolean startMessageUser(String str) throws IOException {
        String clientMessage = converter.inMessage(in.readLine(), 3);
        AtomicBoolean checkstatus = new AtomicBoolean(false);
        addBDMessage(clientMessage);
        Server.getUserList().forEach((key, value) -> {
            if (str.equals("start")) {
                if (key != this && "on".equals(value)
                && key.chooseRoomId.equals(chooseRoomId)) {
                    key.outClient("User " + userName + " has entered the chat!");
                }
            }
            if (converter.getCommand().equals("Exit")) {
                if (!key.equals(this) && "on".equals(value)
                && key.chooseRoomId.equals(chooseRoomId)) {
                    key.outClient(userName + ": Left the chat!");
                }
                if (key.equals(this)) {
                    key.outClient("Exit");
                    Server.setUserList(this, "delete");
                    checkstatus.set(true);
                    stopSocket();
                }
            } else {
                if (key != this && "on".equals(value)
                 && key.chooseRoomId.equals(chooseRoomId)) {
                    key.outClient(userName + ": " + clientMessage);
                }
                checkstatus.set(false);
            }
        });
        return checkstatus.get();
    }

    public void printCommands(int nmb) {
        converter.setNullUserData();
        if (nmb == 1) {
            outClient("1. signIn");
            outClient("2. signUp");
            outClient("3. Exit");
        } else if (nmb == 2) {
            outClient("1. Create room");
            outClient("2. Choose room");
            outClient("3. Exit");
        } else if (nmb == 3) {
            outClient(roomList.get(chooseRoomId).getName() + ". Start messaging");
        }
    }

    public Boolean signUser() throws IOException {
        String command = null;
        String clientMessage = "";
        int step = 0;
        System.out.println("User #" + countUser +
                ": An unknown user has logged into the server.");
        outClient("Hello from Server!");
        printCommands(1);
        while (true) {
            clientMessage = converter.inMessage(in.readLine(), blockNmr);
            sendClientExit();
            if (step == 0) {
                if (converter.getCommand().equals("signUp")
                || converter.getCommand().equals("signIn")) {
                    outClient("Enter username:");
                    step++;
                } else {
                    printCommands(1);
                }
            } else if (step == 1) {
                if (clientMessage != null && !clientMessage.trim().isEmpty()) {
                    if (converter.getCommand().equals("signUp")
                    && userData.containsKey(clientMessage)) {
                        outClient("Error! A user with this name exists!");
                        printCommands(1);
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
                    if (converter.getCommand().equals("signUp")) {
                        usersServiceImpl.signUp(userName, clientMessage);
                        userData.put(userName, 0);
                        System.out.println("User #" + countUser +
                                ": Registered a new user - " + userName);
                        outClient("Registration complete!");
                        printCommands(1);
                        step = 0;
                    } else {
                        if (signIn(userName, clientMessage)) {
                            System.out.println("User #" + countUser +
                                    ": Logged in as user - " + userName);
                            return true;
                        } else {
                            printCommands(1);
                            step = 0;
                        }
                    }
                } else {
                    outClient("Blank value! Enter password:");
                }
            }
        }
    }

    public Boolean roomChats() throws IOException {
        Boolean access = false;
        String clientMessage = null;
        int step = 0;
        int count = 0;
        printCommands(2);
        while (true) {
            clientMessage = converter.inMessage(in.readLine(), blockNmr);
            sendClientExit();
            if (step == 0) {
                if (converter.getCommand().equals("Create room")) {
                    outClient("Create a room");
                    step++;
                } else if (converter.getCommand().equals("Choose room")) {
                      if (roomList.isEmpty()) {
                          outClient("There are no rooms in the list");
                          printCommands(2);
                      } else {
                          outClient("Rooms:");
                          count = 0;
                          for (Room room : roomList) {
                              count++;
                              outClient(count + ". " + room.getName());
                          }
                          outClient((count + 1) + ". Exit");
                          step++;
                      }
                } else {
                    printCommands(2);
                }
            } else if (step == 1) {
                if (converter.getCommand().equals("Create room")) {
                    if (clientMessage.length() == 0) {
                        outClient("You have not entered a room name! " +
                                "Create a new one");
                        step = 0;
                        printCommands(2);
                    } else if (findRoom(clientMessage)) {
                        outClient("A room with this name exists! " +
                                "Create another room");
                        roomId = null;
                        printCommands(2);
                    } else {
                        Server.addRooms(clientMessage);
                        roomsRepository.save(new Room(clientMessage));
                        outClient("The room is created");
                        step = 0;
                        printCommands(2);
                    }
                } else if (converter.getCommand().equals("Choose room")) {
                    if (clientMessage.matches("\\d+")) {
                        chooseRoomId = Integer.parseInt(clientMessage) - 1;
                        if (chooseRoomId >= 0 && chooseRoomId < roomList.size()) {
                            room = roomsRepository.findById(
                                    Long.valueOf(chooseRoomId) + 1
                            );
                            printCommands(3);
                            blockNmr++;
                            return true;
                        } else if (chooseRoomId == roomList.size()) {
                            converter.setCommand("Exit");
                            outClient("Exit");
                        } else {
                            outClient("Such a room does not exist!");
                            roomId = null;
                            step = 0;
                            printCommands(2);
                        }
                    } else {
                        outClient("You must enter a number to select a room!");
                        roomId = null;
                        step = 0;
                        printCommands(2);
                    }
                }
            }
        }
    }

    public Boolean findRoom(String message) {
        for (Room room : roomList) {
            if (room.getName().equals(message))
                return true;
        }
        return false;
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
            Boolean checkSign = false;
            Boolean showMessages = false;
            while (true) {
                if (exitSocket) {
                    System.out.println("User #" + countUser + ": Logout");
                    stopSocket();
                    break;
                } else if (showMessages && access && !exitSocket) {
                    exitSocket = startMessageUser("");
                } else if (access && !exitSocket) {
                    Server.setUserList(this, "on");
                    showMessages = showThirtyMessages();
                } else if (checkSign) {
                    access = roomChats();
                } else {
                    checkSign = signUser();
                }
            }
        } catch (IOException | NullPointerException e) {
            if (userName == null) {
                System.out.println("User #" + countUser +
                        ": Unknown user logged out without authorization.");
            } else {
                System.out.println("User #" + countUser +
                        ": User with name " + userName + " has left.");
                stopSocket();
            }
        }
    }
}

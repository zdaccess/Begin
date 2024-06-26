package edu.school21.sockets.services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Writer extends Thread  {
    private BufferedReader      reader;
    private PrintWriter         out;
    private String              check;
    private String              userName;
    private String              chat;
    private Long                 roomId;
    private String              command;
    private int                 step;
    private DateTimeFormatter   formatter;
    private String              status;
    private Long                userId;


    public Writer(BufferedReader reader, PrintWriter out) {
        this.reader = reader;
        this.out = out;
        this.check = "run";
        this.userName = "";
        this.chat = "";
        this.roomId = null;
        this.userId = null;
        this.command = "";
        this.step = 0;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        this.status = "";
        start();
    }

    public void close(String str) {
        check = str;
    }


    public String commandJSON(String message) {
        String str = null;
        if (message.equals("signUp") || message.equals("signIn")
        || command.equals("signUp") || command.equals("signIn")) {
            if (step == 0) {
                command = message;
                str = "{\"command\" : \"" + command + "\", " +
                        "\"userName\" : \"\", " +
                        "\"password\" : \"\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + userId + "\", " +
                        "\"time\" : " + LocalDateTime.now().format(formatter) + "}";
                step++;
            } else if (step == 1) {
                userName = message;
                str = "{\"command\" : \"" + command + "\", " +
                        "\"userName\" : \"" + userName + "\", " +
                        "\"password\" : \"\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + userId + "\", " +
                        "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
                step++;
            } else if (step == 2) {
                str = "{\"command\" : \"" + command + "\", " +
                        "\"userName\" : \"" + userName + "\", " +
                        "\"password\" : \"" + message + "\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + userId + "\", " +
                        "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
                command = "";
                step = 0;
            }
        } else if (message.equals("Create room") || command.equals("Create room")
                || message.equals("Choose room") || command.equals("Choose room")) {
            if (step == 0) {
                command = message;
                str = "{\"command\" : \"" + command + "\", " +
                        "\"chat\" : \"\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + roomId + "\", " +
                        "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
                step++;
            } else if (step == 1) {
                chat = message;
                str = "{\"command\" : \"" + command + "\", " +
                        "\"chat\" : \"" + message + "\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + roomId + "\", " +
                        "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
                command = "";
                step = 0;
            }
        }
        return str;
    }

    public String messageJSON(String message) {
        return ("{\"message\" : \"" + message + "\", " +
                "\"fromId\" : \"" + userId + "\", " +
                "\"roomId\" : \"" + roomId + "\", " +
                "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}");
    }



    public String determinerMsg(String message) {
        String  str = null;
        if (message.equals("signIn")
        || message.equals("signUp") || message.equals("Create room")
        || message.equals("Choose room") || command.equals("Choose room")
        || command.equals("signIn") || command.equals("signUp")
        || command.equals("Create room")) {
            str = commandJSON(message);
        } else
            str = messageJSON(message);
        return str;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public void run() {
        String parseMessage = null;
        String clientMessage;
        try {
            while(true) {
                clientMessage = reader.readLine();
                if (clientMessage.equals("Exit")) {
                    System.out.println("You have left the chat.");
                    System.exit(0);
                } else if (check.equals("stop")) {
                    System.out.println("Error! Server is not available!");
                    break;
                } else {
                    System.out.println("client " + clientMessage);
                    out.println(determinerMsg(clientMessage));
                }
            }
        } catch (IOException ignored) {}
    }
}

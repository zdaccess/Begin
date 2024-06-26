package edu.school21.sockets.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConverterClient {
    private String[] firstParsing;
    private String[][] secondParsing;
    private String[] parsingMessage;
    private DateTimeFormatter formatter;
    private String              userName;
    private String              chat;
    private Long                 roomId;
    private String              command;
    private int                 step;
    private String              password;
    private String              status;
    private Long                userId;

    public ConverterClient() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        this.userName = "";
        this.chat = "";
        this.roomId = null;
        this.userId = null;
        this.command = "";
        this.password = "";
        this.status = "";
        this.step = 0;
    }


    public String inCommandJSON(String[] parsingMessage) {
        String  str = null;
        command = parsingMessage[1];
        if (command.equals("signUp") || command.equals("signIn")) {
            if (parsingMessage[9] != null)
                userId = Long.parseLong(parsingMessage[9]);
            str = parsingMessage[7];
        }
        else if (command.equals("Create room")
        || command.equals("Choose room")) {
            if (parsingMessage[9] != null)
                userId = Long.parseLong(parsingMessage[9]);
            str = parsingMessage[5];
        }
        return str;
    }

    public String getCommand() {
        return command;
    }


    public Long getRoomId() {
        return roomId;
    }

    public Long getUserId() {
        return userId;
    }


    public String inDeterminerMsg(String[] parsingMessage) {
        String  str = null;
        if (parsingMessage[1].equals("signIn")
        || parsingMessage[1].equals("Choose room")
        || parsingMessage[1].equals("signUp")
        || parsingMessage[1].equals("Create room")) {
            str = inCommandJSON(parsingMessage);
        } else
            str = parsingMessage[1];
        return str;
    }

    public String inMessage(String message)  {
        firstParsing = message.trim().substring(1, message.trim().length() - 1).split("(?=(?:[^\"]|\"[^\"]*\")*$),");
        secondParsing = new String[firstParsing.length][2];
        parsingMessage = new String[firstParsing.length * 2];
        String output = null;
        int i = 0;
        int x = 0;
        for (String msg : firstParsing) {
            secondParsing[i] = msg.split("(?=(?:[^\"]|\"[^\"]*\")*$):");
            for (int y = 0; y < secondParsing[i].length; y++) {
                parsingMessage[x] = secondParsing[i][y].trim().substring(1, secondParsing[i][y].trim().length() - 1);
                System.out.println("xxx " + parsingMessage[x]);
                x++;
            }
            i++;
        }
        output = inDeterminerMsg(parsingMessage);
        return output;
    }

}

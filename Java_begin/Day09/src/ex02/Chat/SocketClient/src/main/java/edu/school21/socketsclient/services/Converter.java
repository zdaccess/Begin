package edu.school21.socketsclient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    private String[]            firstParsing;
    private String[][]          secondParsing;
    private String[]            parsingMessage;
    private DateTimeFormatter   formatter;
    private String              userName;
    private String              chatName;
    private String              roomId;
    private String              command;
    private int                 step;
    private String              password;
    private String              userId;
    private String              status;
    private String              message;
    private String              roomName;
    private int                 blockNmr;


    public Converter() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        this.userName = "";
        this.chatName = "";
        this.roomId = null;
        this.userId = null;
        this.command = "";
        this.password = "";
        this.step = 0;
        this.status = "";
        this.message = "";
        this.roomName = "";
    }

    public String inMessage(String message)  {
        firstParsing = message.trim().substring(1, message.trim().length() - 1)
                .split("(?=(?:[^\"]|\"[^\"]*\")*$),");
        secondParsing = new String[firstParsing.length][2];
        parsingMessage = new String[firstParsing.length * 2];
        String output = null;
        int i = 0;
        int x = 0;
        for (String msg : firstParsing) {
            secondParsing[i] = msg.split("(?=(?:[^\"]|\"[^\"]*\")*$):");
            for (int y = 0; y < secondParsing[i].length; y++) {
                parsingMessage[x] = secondParsing[i][y].trim()
                        .substring(1, secondParsing[i][y].trim().length() - 1);
                x++;
            }
            i++;
        }
        output = inDeterminerMsg(parsingMessage);
        return output;
    }

    public void setNullUserData() {
        step = 0;
        userName = "";
        command = "";
        password = "";
        status = "";
    }

    public String inDeterminerMsg(String[] parsingMessage) {
        String  str = null;
        if (parsingMessage[1].equals("signIn")
                || parsingMessage[1].equals("Choose room")
                || parsingMessage[1].equals("signUp")
                || parsingMessage[1].equals("Create room")
                || parsingMessage[1].equals("Exit")) {
            str = inCommandJSON(parsingMessage);
        } else {
            str = parsingMessage[1];
            userId = parsingMessage[3];
            roomId = parsingMessage[5];
            setNullUserData();
        }
        return str;
    }

    public String inCommandJSON(String[] parsingMessage) {
        String  str = null;
        command = parsingMessage[1];
        if (command.equals("signIn") || command.equals("signUp")) {
            blockNmr = 1;
            str = parsingMessage[7];
        } else if (command.equals("Create room")
                || command.equals("Choose room")) {
            blockNmr = 2;
            status = parsingMessage[7];
            if (parsingMessage[5].length() > 0)
                blockNmr = 3;
            str = status;
        } else {
            command = parsingMessage[1];
            str = command;
        }
        return str;
    }

    public String commandJSON(String msg) {
        String str = null;
        if (command.equals("signUp") || command.equals("signIn")) {
            if (step == 0) {
                userName = msg;
            } else if (step == 1) {
                password = msg;
            }
                str = "{\"command\" : \"" + command + "\", " +
                        "\"userName\" : \"" + userName + "\", " +
                        "\"password\" : \"" + password + "\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + userId + "\", " +
                        "\"time\" : \""
                        + LocalDateTime.now().format(formatter) + "\"}";
            if (!userName.isEmpty() && step == 0
            || !password.isEmpty() && step == 1)
                 step++;
            } else if (command.equals("Create room")
            || command.equals("Choose room")) {
            if (step == 0 && command.equals("Create room")) {
                roomName = msg;
            } else if (step == 0 && command.equals("Choose room")) {
                roomId = msg;
            }
                str = "{\"command\" : \"" + command + "\", " +
                        "\"roomName\" : \"" + roomName + "\", " +
                        "\"roomId\" : \"" + roomId + "\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"time\" : \"" + LocalDateTime.now().format(formatter)
                        + "\"}";
                step++;
            }
        return str;
    }

    public void clearCommandData(String str) {
        if (str.equals("Registration complete!")
        || str.equals("Error! A user with this name exists!")
        || str.equals("Error! A user with this name does not exist!")
        || str.equals("Error! A user with the same name is already in the chat")) {
            command = "";
            userName = "";
            status = "";
            step = 0;
        }
    }

    public String messageJSON(String msg) {
        return ("{\"message\" : \"" + msg + "\", " +
                "\"userId\" : \"" + userId + "\", " +
                "\"roomId\" : \"" + roomId + "\", " +
                "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}");
    }

    public String outDeterminerMsg(String message, int blockNmr) {
        String  str = null;
        if (command.equals("Choose room")
                || command.equals("signIn") || command.equals("signUp")
                || command.equals("Create room")) {
            str = commandJSON(message);
        } else
            str = messageJSON(message);
        return str;
    }

       public String inMessageJSON(String[] parsingMessage) {
        String  str = parsingMessage[1];
        return str;
    }

    public String outMessage(String message, int blockNmr) {
        return outDeterminerMsg(message, blockNmr);
    }

    public String getCom() {
        return command;
    }

    public int getBlockNmr() {
        return blockNmr;
    }

    public String getCommand() {
        return command;
    }
}

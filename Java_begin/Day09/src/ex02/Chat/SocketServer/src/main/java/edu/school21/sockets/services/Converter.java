package edu.school21.sockets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    private String[]            firstParsing;
    private String[][]          secondParsing;
    private String[]            parsingMessage;
    private DateTimeFormatter   formatter;
    private String              userName;
    private String              roomId;
    private String              command;
    private int                 step;
    private String              password;
    private Long                userId;
    private String              status;
    private int                 inStep;
    private String              roomName;

    public Converter() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        this.userName = "";
        this.roomId = null;
        this.userId = null;
        this.command = "";
        this.password = "";
        this.step = 0;
        this.status = "";
        this.inStep = 0;
    }

    public void setNullUserData() {
        command = "";
        userName = "";
        password = "";
        status = "";
        inStep = 0;
        step = 0;
    }

    public String inMessage(String message, int blockNmr)  {
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
                parsingMessage[x] = secondParsing[i][y].trim().substring(
                        1, secondParsing[i][y].trim().length() - 1
                );
                x++;
            }
            i++;
        }
        output = commandDecoding(parsingMessage, blockNmr);
        return output;
    }

    public String commandDecoding(String[] parsingMessage, int blockNmr) {
        String output = null;
            if (parsingMessage[1].equals("1") && blockNmr == 1
            || command.equals("signIn")) {
                parsingMessage[1] = "signIn";
                output = inCommandJSON(parsingMessage);
            } else if (parsingMessage[1].equals("2") && blockNmr == 1
            || command.equals("signUp")) {
                parsingMessage[1] = "signUp";
                output = inCommandJSON(parsingMessage);
            } else if (parsingMessage[1].equals("1") && blockNmr == 2
            || command.equals("Create room")) {
                parsingMessage[1] = "Create room";
                output = inCommandJSON(parsingMessage);
            } else if (parsingMessage[1].equals("2") && blockNmr == 2
            || command.equals("Choose room")) {
                parsingMessage[1] = "Choose room";
                output = inCommandJSON(parsingMessage);
            } else if (parsingMessage[1].equals("3") && blockNmr == 1
            || parsingMessage[1].equals("3") && blockNmr == 2
            || parsingMessage[1].equals("Exit") && blockNmr == 3) {
                command = "Exit";
                output = command;
            } else {
                output = inMessageJSON(parsingMessage);
            }
        return output;
    }

    public String inDeterminerMsg(String[] parsingMessage, int blockNmr) {
        String  str = null;
        if (parsingMessage[1].equals("signIn") && blockNmr == 1
                || parsingMessage[1].equals("Choose room") && blockNmr == 2
                || parsingMessage[1].equals("signUp") && blockNmr == 1
                || parsingMessage[1].equals("Create room") && blockNmr == 2) {
            str = inCommandJSON(parsingMessage);
        } else if (parsingMessage[1].equals("Exit")) {
            command = parsingMessage[1];
            str = command;
        } else
            str = inMessageJSON(parsingMessage);
        return str;
    }

    public String inCommandJSON(String[] parsingMessage) {
        String  str = null;
        command = parsingMessage[1];
        if (command.equals("signUp") || command.equals("signIn")) {
            if (inStep == 0) {
                str = command;
                inStep++;
            } else if (inStep == 1 && parsingMessage[3].length() > 0) {
                userName = parsingMessage[3];
                str = userName;
                inStep++;
            } else if (inStep == 2 && parsingMessage[5].length() > 0) {
                password = parsingMessage[5];
                str = password;
            } else
                str = "";
        }
        else if (command.equals("Create room") || command.equals("Choose room")) {
            if (inStep == 0) {
                str = command;
                inStep++;
            } else if (inStep == 1) {
                if (command.equals("Create room")) {
                    roomName = parsingMessage[3];
                    str = roomName;
                } else {
                    roomId = parsingMessage[5];
                    str = roomId;
                }
                inStep = 0;
            }
        }
        return str;
    }

    public String inMessageJSON(String[] parsingMessage) {
        String  str = parsingMessage[1];
        return str;
    }

    public String outDeterminerMsg(String msg) {
        String  str;
        if (command.equals("Choose room")
                || command.equals("signIn") || command.equals("signUp")
                || command.equals("Create room")) {
            str = outCommandJSON(msg);
        } else if (command.equals("Exit")) {
            str = outCommandExit();
        } else {
            str = messageJSON(msg);
        }
        return str;
    }

    public String outCommandExit() {
        String str = "{\"command\" : \"" + command + "\", " +
                "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
        return str;
    }

    public String outCommandJSON(String msg) {
        String str = null;
        if (command.equals("signUp") || command.equals("signIn")) {
            status = msg;
            str = "{\"command\" : \"" + command + "\", " +
                    "\"userName\" : \"" + userName + "\", " +
                    "\"password\" : \"" + password + "\", " +
                    "\"status\" : \"" + status + "\", " +
                    "\"userId\" : \"" + userId + "\", " +
                    "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
        } else if (command.equals("Create room")
                || command.equals("Choose room")) {
                status = msg;
            str = "{\"command\" : \"" + command + "\", " +
                    "\"roomName\" : \"" + roomName + "\", " +
                    "\"roomId\" : \"" + roomId + "\", " +
                    "\"status\" : \"" + status + "\", " +
                    "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
                step++;
            }
        return str;
    }

    public String messageJSON(String msg) {
        return ("{\"message\" : \"" + msg + "\", " +
                "\"userId\" : \"" + userId + "\", " +
                "\"roomId\" : \"" + roomId + "\", " +
                "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}");
    }

    public String getCommand() {
        return command;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String outMessage(String message) {
        return outDeterminerMsg(message);
    }
    public String getCom() {
        return command;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public Long getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}

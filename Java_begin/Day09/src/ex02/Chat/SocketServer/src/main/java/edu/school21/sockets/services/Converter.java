package edu.school21.sockets;

import edu.school21.sockets.models.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
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
    private Long                userId;
    private String              status;

    public Converter() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        this.userName = "";
        this.chat = "";
        this.roomId = null;
        this.userId = null;
        this.command = "";
        this.password = "";
        this.step = 0;
        this.status = "";
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
                status = message;
                str = "{\"command\" : \"" + command + "\", " +
                        "\"userName\" : \"\", " +
                        "\"password\" : \"\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + userId + "\", " +
                        "\"time\" : " + LocalDateTime.now().format(formatter) + "}";
                step++;
            } else if (step == 2) {
                userName = message;
                str = "{\"command\" : \"" + command + "\", " +
                        "\"userName\" : \"" + userName + "\", " +
                        "\"password\" : \"\", " +
                        "\"status\" : \"" + status + "\", " +
                        "\"userId\" : \"" + userId + "\", " +
                        "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}";
                step++;
            } else if (step == 3) {
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
                "\"fromId\" : \"" + userName + "\", " +
                "\"roomId\" : \"" + roomId + "\", " +
                "\"time\" : \"" + LocalDateTime.now().format(formatter) + "\"}");
    }



    public String outDeterminerMsg(String message, String choose) {
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

    public String inCommandJSON(String[] parsingMessage) {
        String  str = null;
        command = parsingMessage[1];
        if (command.equals("signUp") || command.equals("signIn")) {
            if (step == 0) {
                str = command;
                step++;
            }
            else if (step == 1) {
                str = parsingMessage[3];
                step++;
            } else if (step == 2) {
                str = parsingMessage[5];
                step = 0;
            }
        }
        else if (command.equals("Create room")
        || command.equals("Choose room")) {
            if (step == 0) {
                str = command;
                step++;
            } else if (step == 1) {
                str = parsingMessage[3];
                step = 0;
            }
        }
        return str;
    }

    public String inMessageJSON(String[] parsingMessage) {
        String  str = parsingMessage[1];
        return str;
    }

    public String inDeterminerMsg(String[] parsingMessage, int blockNmr) {
        String  str = null;
        if (parsingMessage[1].equals("signIn") && blockNmr == 1
        || parsingMessage[1].equals("Choose room") && blockNmr == 2
        || parsingMessage[1].equals("signUp") && blockNmr == 1
        || parsingMessage[1].equals("Create room") && blockNmr == 2) {
            str = inCommandJSON(parsingMessage);
        } else
            str = inMessageJSON(parsingMessage);
        return str;
    }

    public String commandDecoding(String[] parsingMessage, int blockNmr) {
        String output = null;
        if (parsingMessage[1].equals("1") && blockNmr == 1) {
            parsingMessage[1] = "signIn";
        } else if (parsingMessage[1].equals("2") && blockNmr == 1) {
            parsingMessage[1] = "signUp";
        } else if (parsingMessage[1].equals("3") && blockNmr == 1) {
            parsingMessage[1] = "Exit";
        } else if (parsingMessage[1].equals("1") && blockNmr == 2) {
            parsingMessage[1] = "Create room";
        } else if (parsingMessage[1].equals("2") && blockNmr == 2) {
            parsingMessage[1] = "Choose room";
        } else if (parsingMessage[1].equals("3") && blockNmr == 2) {
            parsingMessage[1] = "Exit";
        } else {
            blockNmr = 4;
        }
        output = inDeterminerMsg(parsingMessage, blockNmr);
        return output;
    }


    public String inMessage(String message, int blockNmr)  {
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
                System.out.println(parsingMessage[x]);
                x++;
            }
            i++;
        }
        output = commandDecoding(parsingMessage, blockNmr);
        return output;
    }

    public String outMessage(String message, String choose) {
        return outDeterminerMsg(message, choose);
    }
    public String getCom() {
        return command;
    }
}

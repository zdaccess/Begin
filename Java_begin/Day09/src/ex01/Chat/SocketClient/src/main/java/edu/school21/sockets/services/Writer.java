package edu.school21.sockets.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Writer extends Thread  {
    private BufferedReader  reader;
    private PrintWriter     out;
    private String          check;
    private String          userName;
    private String          chat;
    private String          roomId;
    private String          command;
    private int             step;

    public Writer(BufferedReader reader, PrintWriter out) {
        this.reader = reader;
        this.out = out;
        this.check = "run";
        this.userName = null;
        this.chat = null;
        this.roomId = null;
        this.step = 0;
        start();
    }

    public void close(String str) {
        check = str;
    }


    @Override
    public void run() {
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
                    out.println(clientMessage);
                }
            }
        } catch (IOException ignored) {}
    }
}

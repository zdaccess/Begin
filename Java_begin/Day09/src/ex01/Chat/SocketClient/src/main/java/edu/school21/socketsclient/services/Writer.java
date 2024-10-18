package edu.school21.socketsclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.Scanner;

public class Writer extends Thread  {
    private Scanner         reader;
    private PrintWriter     out;

    public Writer(Scanner reader, PrintWriter out) {
        this.reader = reader;
        this.out = out;
        start();
    }

    public void closeWriter() {
        System.exit(0);
    }

    @Override
    public void run() {
        String clientMessage;
        try {
            while(true) {
                    clientMessage = reader.nextLine();
                    if (clientMessage.equals("Exit")) {
                        out.println(clientMessage);
                        System.out.println("You have left the chat.");
                        Client.stopClient(0);
                        System.exit(0);
                    } else {
                        out.println(clientMessage);
                    }
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Error! Server unavailable: " + e.getMessage());
        }
    }
}

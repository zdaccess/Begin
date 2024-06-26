package edu.school21.sockets.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Reader extends Thread {
    private BufferedReader in;
    private PrintWriter out;

    public Reader(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        String serverMessage;
        try {
            while (true) {
                if (in != null) {
                    serverMessage = in.readLine();
                    if (serverMessage == null) {
                        break;
                    }
                    System.out.println(serverMessage);
                }
            }
        } catch (IOException e) {
            System.out.println("Reader " + e.getMessage());
        }
    }
}

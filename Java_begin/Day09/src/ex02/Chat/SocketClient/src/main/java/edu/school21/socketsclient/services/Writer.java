package edu.school21.socketsclient;

import java.io.*;
import java.util.Scanner;

public class Writer extends Thread  {
    private Scanner             reader;
    private PrintWriter         out;
    private Converter           converter;
    private String              clientMessage;
    private int                 blockNmr;

    public Writer(Scanner reader, PrintWriter out, Converter converter) {
        this.reader = reader;
        this.out = out;
        this.converter = converter;
        this.blockNmr = 1;
        start();
    }

    public void closeWriter() {
        System.exit(0);
    }

    @Override
    public void run() {
        try {
            while(!isInterrupted()) {
                blockNmr = converter.getBlockNmr();
                clientMessage = converter.outMessage(
                        reader.nextLine(), blockNmr
                );
                out.println(clientMessage);
            }
        } catch (RuntimeException e) {
            System.out.println("Error! Server unavailable: " + e.getMessage());
        }
    }
}

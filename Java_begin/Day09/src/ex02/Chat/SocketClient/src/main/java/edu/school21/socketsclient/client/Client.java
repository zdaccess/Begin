package edu.school21.socketsclient;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private BufferedReader  in;
    private Scanner         reader;
    private PrintWriter     out;
    private Writer          write;
    private int             port;
    private Converter       converter;

    public Client (int port) {
        this.port = port;
        this.converter = new Converter();
    }

    public void stopClient(Integer nmb) {
        try {
            write.closeWriter();
            reader.close();
            in.close();
            out.close();
            System.exit(nmb);
        } catch (IOException ignored) {}
    }

    public void startClient () throws IOException {
        try {
            Socket socket = new Socket("localhost", port);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            out = new PrintWriter(socket.getOutputStream(), true);
            reader = new Scanner(new InputStreamReader(System.in));
            write = new Writer(reader, out, converter);
            String serverMessage;
            while (true) {
                try {
                    serverMessage = converter.inMessage(in.readLine());
                    converter.clearCommandData(serverMessage);
                    if (converter.getCommand().equals("Exit")) {
                        System.out.println("You have left the chat.");
                        stopClient(0);
                    }
                    if (serverMessage == null) {
                        System.out.println("Error! Server is not available!");
                        stopClient(-1);
                    }
                    System.out.println(serverMessage);
                } catch (IOException e) {
                    System.out.println("Error! The server is disabled: "
                            + e.getMessage());
                    stopClient(-1);
                    break;
                }
            }
        } catch (RuntimeException | ConnectException e) {
            stopClient(1);
            System.out.println("Error! Server unavailable: " + e.getMessage());
        } catch (IOException e) {
            stopClient(1);
            System.out.println("Error! Server unavailable: " + e.getMessage());
        } finally {
            stopClient(0);
        }
    }
}
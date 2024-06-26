package edu.school21.sockets.client;

import edu.school21.sockets.services.ConverterClient;
import edu.school21.sockets.services.Writer;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ConcurrentModificationException;

public class Client {
    private BufferedReader  in;
    private BufferedReader  reader;
    private static String   check;
    private PrintWriter     out;
    private Writer          write;
    private int             port;
    private ConverterClient converter;

    public Client (int port) {
        this.port = port;
        check = "";
        this.converter = new ConverterClient();
    }

    public void stopClient(Integer nmb) {
        try {
            reader.close();
            in.close();
            out.close();
            System.exit(nmb);
        } catch (IOException ignored) {}
    }

    public Boolean checkStatus (String parseMessage) {
        Boolean check = false;
        if (converter.getCommand().equals("signIn")
                || converter.getCommand().equals("Create room")) {
            write.setStatus(parseMessage);
        } else if (converter.getCommand().equals("signUp")) {
            write.setStatus(parseMessage);
            write.setUserId(converter.getUserId());
        } else if (converter.getCommand().equals("Choose room")) {
            write.setStatus(parseMessage);
            write.setUserId(converter.getRoomId());
        }
        if (check.equals("stop")) {
            stopClient(0);
            check = true;
        }
        if (parseMessage == null) {
            write.close("stop");
            stopClient(-1);
            check = true;
        }
        return check;
    }

    public void startClient () throws IOException, NullPointerException {
        try (Socket socket = new Socket("localhost", port)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(System.in));
            write = new Writer(reader, out);
            String parseMessage = null;
            String serverMessage = null;
            while (true) {
                serverMessage = in.readLine();
                if (serverMessage != null) {
                    System.out.println("client " + serverMessage);
                    parseMessage = converter.inMessage(serverMessage);
                    if (checkStatus(parseMessage))
                        break;
                    System.out.println(parseMessage);
                }
            }
        }
    }
}
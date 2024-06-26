package edu.school21.sockets.client;

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

    public Client (int port) {
        this.port = port;
        check = "";
    }

    public void stopClient(Integer nmb) {
        try {
            reader.close();
            in.close();
            out.close();
            System.exit(nmb);
        } catch (IOException ignored) {}
    }

    public void startClient () throws IOException {
        try (Socket socket = new Socket("localhost", port)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(System.in));
            write = new Writer(reader, out);
            String serverMessage;
            while (true) {
                serverMessage = in.readLine();
                if (check.equals("stop")) {
                    stopClient(0);
                    break;
                }
                if (serverMessage == null) {
                    write.close("stop");
                    stopClient(-1);
                    break;
                }
                System.out.println(serverMessage);
            }
        }
    }
}
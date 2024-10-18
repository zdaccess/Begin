package edu.school21.net;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionManager {
    private Socket socket;

    @Getter
    private BufferedReader in;

    @Getter
    private PrintWriter out;

    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            System.out.println("Server is dead :(");
            return false;
        }
    }

    @SneakyThrows
    public void close() {
        if (socket != null) {
            in.close();
            out.close();
            socket.close();
        }
    }

    @SneakyThrows
    public boolean isConnected() {
        return in.ready();
    }
}

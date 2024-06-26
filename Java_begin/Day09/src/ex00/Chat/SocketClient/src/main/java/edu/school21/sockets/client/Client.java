package edu.school21.sockets.client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int port;
    private BufferedReader reader;

    public Client (int port) {
        this.port = port;
    }

    public void startClient () throws IOException {
        try {
            socket = new Socket("localhost", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage;
            String serverMessage;
            while (true) {
                serverMessage = in.readLine();
                System.out.println(serverMessage);
                clientMessage = reader.readLine();
                out.println(clientMessage);
                out.flush();
                if (serverMessage.equals("Successful!")) {
                    out.flush();
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        }  catch (RuntimeException | ConnectException e) {
            System.out.println("Ошибка! Сервер недоступен: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода вывода!");
        }
    }
}
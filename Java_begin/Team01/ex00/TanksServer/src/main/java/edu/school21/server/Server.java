package edu.school21.server;

import edu.school21.entity.GameStat;
import edu.school21.service.GameStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
@Component
public class Server {
    private final List<Client> clients = Collections.synchronizedList(new ArrayList<>());
    private final GameStatService service;
    private int clientCount = 0;

    public void start(int port) {
        try (var serverSocket = new ServerSocket(port)) {
            log.info("Server is run: {}:{}", "localhost", port);

            while (clientCount < 2) acceptClient(serverSocket);

            clients.forEach(Thread::start);
        } catch (IOException e) {
            log.error("Error while running the server", e);
        }
    }

    private void acceptClient(ServerSocket serverSocket) throws IOException {
        var socket = serverSocket.accept();
        var client = new Client(socket, ++clientCount);
        clients.add(client);
        log.info("New connection. Number of client: {}", clientCount);
        service.createClient(client.stat);
    }

    private class Client extends Thread {
        private final GameStat stat;
        private final PrintWriter out;
        private final Scanner in;
        private final Socket socket;
        private final int number;

        Client(Socket socket, int number) throws IOException {
            this.socket = socket;
            this.number = number;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new Scanner(socket.getInputStream());
            this.stat = new GameStat(number, 0, 0);
        }

        @Override
        public void run() {
            out.println(number);
            log.info("Client {} start", number);
            out.println("Start");


            while (true) {
                try {
                    if (in.hasNextLine()) {
                        var command = in.nextLine();
                        if (command.contains("shot")) {
                            service.addShot(stat);
                        } else if (command.contains("hit")) {
                            service.addHit(stat);
                        } else if (command.contains("game_over")) {
                            var statistics = service.getStatistics(number);
                            notifyOtherClients(statistics);
                            sendToClient(statistics);
                            break;
                        }

                        notifyOtherClients(command);
                    } else {
                        notifyOtherClients("enemy_left");
                        exitClient();
                        break;
                    }
                } catch (RuntimeException e) {
                    log.info("Player is left");
                }
            }
        }


        private void notifyOtherClients(String message) {
            clients.stream()
                    .filter(c -> this.number != c.number)
                    .forEach(c -> c.sendToClient(message));
        }

        private void exitClient() {
            try {
                removeClient(this);
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                log.error("Exit error", e);
            }
        }

        private void removeClient(Client client) {
            clients.remove(client);
            --clientCount;
            log.info("User {} has left the game", number);
        }


        void sendToClient(String message) {
            out.println(message);
        }
    }
}
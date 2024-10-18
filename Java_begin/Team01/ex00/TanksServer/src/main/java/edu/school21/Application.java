package edu.school21;

import edu.school21.config.AppConfig;
import edu.school21.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Application {
    public static void main(String[] args) {
        int port = parsePort(args);

        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        Server server = context.getBean(Server.class);

        server.start(port);
    }

    private static int parsePort(String[] args) {
        int port = 8081;

        if (args.length == 1 && args[0].startsWith("--port=")) {
            try {
                port = Integer.parseInt(args[0].substring("--port=".length()));
            } catch (NumberFormatException e) {
                log.error("Invalid port number. Using default port {}.", port);
            }
        } else if (args.length > 0) {
            log.error("Invalid argument. Use '--port=<number>'. Using default port {}.", port);
        }

        return port;
    }
}
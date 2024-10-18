package edu.school21;

import edu.school21.net.ConnectionManager;
import edu.school21.ui.ConnectionWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final ConnectionManager connectionManager = new ConnectionManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new ConnectionWindow(connectionManager).show(stage);
    }
}
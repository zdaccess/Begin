package edu.school21.ui;

import edu.school21.net.ConnectionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ConnectionWindow {
    private static final String HOST = "localhost";
    private static final int PORT = 8081;

    private final ConnectionManager connectionManager;
    private final MainWindow mainWindow;

    public ConnectionWindow(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.mainWindow = new MainWindow();
        MainWindow.setConnectionManager(connectionManager);
    }

    public void show(Stage stage) {
        GridPane grid = createConnectionGrid();
        Scene inputScene = new Scene(grid, 350, 250);

        TextField serverAddressTextField = new TextField(HOST);
        TextField portTextField = new TextField(String.valueOf(PORT));
        Text actionTarget = new Text();

        Button connectButton = createConnectButton(serverAddressTextField, portTextField, actionTarget, stage);
        addElementsToGrid(grid, serverAddressTextField, portTextField, actionTarget, connectButton);

        stage.setScene(inputScene);
        stage.setTitle("Connection Window");
        stage.show();
    }

    private GridPane createConnectionGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #f0f0f0;"); // Set a light background color
        return grid;
    }

    private void addElementsToGrid(GridPane grid, TextField serverAddressTextField, TextField portTextField, Text actionTarget, Button connectButton) {
        Text sceneTitle = new Text("Connect to Server");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label serverTextLabel = new Label("Server:");
        grid.add(serverTextLabel, 0, 1);
        grid.add(serverAddressTextField, 1, 1);

        Label portLabel = new Label("Port:");
        grid.add(portLabel, 0, 2);
        grid.add(portTextField, 1, 2);

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.getChildren().add(connectButton);
        grid.add(buttonContainer, 0, 3, 1, 1);

        actionTarget.setTextAlignment(TextAlignment.CENTER);
        grid.add(actionTarget, 0, 4, 2, 1);
    }

    private Button createConnectButton(TextField serverAddressTextField, TextField portTextField, Text actionTarget, Stage stage) {
        Button btn = new Button("Connect");
        btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Button styling
        btn.setOnAction(e -> handleConnectButtonAction(serverAddressTextField, portTextField, actionTarget, stage));
        return btn;
    }

    private void handleConnectButtonAction(TextField serverAddressTextField, TextField portTextField, Text actionTarget, Stage stage) {
        String portText = portTextField.getText();
        if (!validatePort(portText)) {
            showErrorMessage(actionTarget, "Invalid port number.");
        } else if (!connectionManager.connect(serverAddressTextField.getText(), Integer.parseInt(portText))) {
            showErrorMessage(actionTarget, "Can't connect to this server.");
        } else {
            stage.close();
            mainWindow.show(stage);
        }
    }

    private void showErrorMessage(Text actionTarget, String message) {
        actionTarget.setFill(Color.FIREBRICK);
        actionTarget.setText(message);
        actionTarget.setTextAlignment(TextAlignment.CENTER);
    }

    private boolean validatePort(String port) {
        try {
            int p = Integer.parseInt(port);
            return p >= 0 && p <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

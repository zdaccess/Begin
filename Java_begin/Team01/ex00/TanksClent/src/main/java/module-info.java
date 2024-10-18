module edu.school {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;

    opens edu.school21 to javafx.fxml;
    exports edu.school21;
}
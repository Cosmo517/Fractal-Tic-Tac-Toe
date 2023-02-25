module com.example.advtictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.advtictactoe to javafx.fxml;
    exports com.example.advtictactoe;
}
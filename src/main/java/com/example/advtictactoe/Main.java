package com.example.advtictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage)
    {
        StackPane rootPane = new StackPane();
        Game game = new Game();

        game.setAlignment(Pos.CENTER);

        rootPane.getChildren().add(game);
        Scene scene = new Scene(rootPane, 500,500);
        stage.setTitle("Another Test");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args)
    {
        launch();
    }


}
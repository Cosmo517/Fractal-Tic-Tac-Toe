package com.example.advtictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Stack;


public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        BorderPane rootPane = new BorderPane();
        Button resetGame = new Button("Reset Game");
        Game game = new Game();

        game.setAlignment(Pos.CENTER);
        rootPane.setCenter(game);
        rootPane.setTop(resetGame);
        resetGame.setOnAction(actionEvent -> game.resetGame());

        Scene scene = new Scene(rootPane, 500,500);
        stage.setTitle("Fractal Tic Tac Toe!");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args)
    {
        launch();
    }


}
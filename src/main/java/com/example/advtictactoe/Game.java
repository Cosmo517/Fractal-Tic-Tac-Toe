package com.example.advtictactoe;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Game extends GridPane
{
    private Button[][] board;
    private GridPane mainPane;
    private char[][] bigBoard;
    private char currentPiece;

    public Game()
    {
        mainPane = new GridPane();
        bigBoard = new char[3][3];
        board = new Button[9][9];

        currentPiece = 'X';

        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(10);
        mainPane.setVgap(10);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                bigBoard[i][j] = ' ';

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = new Button(" ");
                mainPane.add(board[i][j], j, i); // gridPane is column row


                final int indexX = j;
                final int indexY = i;


                board[i][j].setOnAction(actionEvent ->
                {
                    //System.out.println("IndexX: " + indexX);
                    //System.out.println("IndexY: " + indexY);
                    board[indexY][indexX].setText(String.valueOf(placePiece()));
                    board[indexY][indexX].setDisable(true);
                    checkForSmallWin(indexX, indexY);
                    checkForDraw(indexX, indexY);
                    checkForWin();
                    determineNextMove(indexX, indexY);
                    swapPiece();
                });
            }
        }

        //mainPane.setStyle("-fx-border-color: red;");
        this.getChildren().add(mainPane);
    }


    public void determineNextMove(int boxClickedX, int boxClickedY)
    {
        int bigBoardBoxToPlayX = boxClickedX % 3;
        int bigBoardBoxToPlayY = boxClickedY % 3;
        int xStart = bigBoardBoxToPlayX * 3;
        int yStart = bigBoardBoxToPlayY * 3;
        int xEnd = (bigBoardBoxToPlayX * 3) + 3;
        int yEnd = (bigBoardBoxToPlayY * 3) + 3;


        /* DEBUG INFORMATION
        System.out.println("----------------------------");
        System.out.println("BigBoardX: " + bigBoardBoxToPlayX);
        System.out.println("BigBoardY: " + bigBoardBoxToPlayY);
        System.out.println("xStart: " + xStart);
        System.out.println("xEnd: " + xEnd);
        System.out.println("yStart: " + yStart);
        System.out.println("yEnd: " + yEnd);
         */

        if (bigBoard[bigBoardBoxToPlayY][bigBoardBoxToPlayX] == ' ')
        {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (i < yEnd && i >= yStart && j < xEnd && j >= xStart)
                    {
                        //System.out.println("Enabling: " + i + ", " + j);  // DEBUG LINE
                        if (board[i][j].getText().charAt(0) == ' ')
                            board[i][j].setDisable(false);
                    }
                    else
                    {
                        board[i][j].setDisable(true);
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    if (board[i][j].getText().charAt(0) == ' ')
                    {
                        board[i][j].setDisable(false);
                    }
                }
            }
            lockBigBoardBasedOnCompleted();
        }
    }

    public void lockBigBoardBasedOnCompleted()
    {
        int xStart = 0;
        int yStart = 0;
        int xEnd = 0;
        int yEnd = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (bigBoard[i][j] != ' ')
                {
                    xStart = j * 3;
                    yStart = i * 3;
                    xEnd = (j * 3) + 3;
                    yEnd = (i * 3) + 3;

                    for (int m = yStart; m < yEnd; m++)
                        for (int n = xStart; n < xEnd; n++)
                            board[m][n].setDisable(true);
                }
            }
        }
    }

    //

    public void checkForDraw(int x, int y)
    {
        int xBox = x/3+1;
        int yBox = y/3+1;

        int xStart = (xBox - 1) * 3;
        int yStart = (yBox - 1) * 3;
        int xEnd = (xBox * 3);
        int yEnd = (yBox * 3);

        int totalSpotsFilled = 0;

        if (bigBoard[yBox-1][xBox-1] == ' ')
        {
            for (int i = yStart; i < yEnd; i++)
            {
                for (int j = xStart; j < xEnd; j++)
                {
                    if (board[i][j].getText().charAt(0) != ' ')
                    {
                        totalSpotsFilled++;
                    }
                }
            }
            if (totalSpotsFilled == 9)
                bigBoard[yBox-1][xBox-1] = 'D';
        }
    }



    public char placePiece()
    {
        return currentPiece;
    }

    public void swapPiece()
    {
        if (currentPiece == 'X')
            currentPiece = 'O';
        else
            currentPiece = 'X';
    }

    public void printBigBoard()
    {
        for (int m = 0; m < 3; m++)
        {
            for (int n = 0; n < 3; n++)
            {
                System.out.print(bigBoard[n][m]);
            }
            System.out.println();
        }
    }

    public void checkForWin()
    {
        int marksVertical = 0;
        int marksHorizontal = 0;
        int marksDiagLR = 0;
        int marksDiagRL = 0;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (bigBoard[i][j] == currentPiece)
                    marksVertical++;
                if (bigBoard[j][i] == currentPiece)
                    marksHorizontal++;
            }
            if (marksVertical == 3 || marksHorizontal == 3)
            {
                for (int m = 0; m < 9; m++)
                    for (int n = 0; n < 9; n++)
                        board[m][n].setDisable(true);
                printBigBoard();
            }
            marksHorizontal = 0;
            marksVertical = 0;
        }

        int x = 0;
        for (int i = 0; i < 3; i++)
        {
            if (bigBoard[i][i] == currentPiece)
                marksDiagRL++;
        }
        for (int i = 2; i >= 0; i--)
        {

            if (bigBoard[x][i] == currentPiece)
                marksDiagLR++;
            x++;
        }

        if (marksDiagRL == 3 || marksDiagLR == 3)
        {
            for (int m = 0; m < 9; m++)
                for (int n = 0; n < 9; n++)
                    board[m][n].setDisable(true);
            printBigBoard();
        }
    }

    public void checkForSmallWin(int x, int y)
    {
        int xBox = x/3+1;
        int yBox = y/3+1;

        int xStart = (xBox - 1) * 3;
        int yStart = (yBox - 1) * 3;
        int xEnd = (xBox * 3);
        int yEnd = (yBox * 3);

        int marksHorizontal = 0;
        int marksVertical = 0;
        int marksDiagLR = 0;
        int marksDiagRL = 0;

        for (int i = yStart; i < yEnd; i++)
        {
            for (int j = xStart; j < xEnd; j++)
            {
                if (board[i][j].getText().charAt(0) == currentPiece)
                {
                    marksVertical++;
                }
            }
            if (marksVertical == 3)
            {
                bigBoard[yBox-1][xBox-1] = currentPiece;
                for (int m = yStart; m < yEnd; m++)
                    for (int n = xStart; n < xEnd; n++)
                        board[m][n].setDisable(true);
            }
            marksVertical = 0;
        }
        for (int i = xStart; i < xEnd; i++)
        {
            for (int j = yStart; j < yEnd; j++)
            {
                if (board[j][i].getText().charAt(0) == currentPiece)
                {
                    marksHorizontal++;
                }
            }
            if (marksHorizontal == 3)
            {
                bigBoard[yBox-1][xBox-1] = currentPiece;
                for (int m = yStart; m < yEnd; m++)
                    for (int n = xStart; n < xEnd; n++)
                        board[m][n].setDisable(true);
            }
            marksHorizontal = 0;
        }


        int xDiag = xStart;
        int xDiagBackwards = xEnd-1;
        for (int i = yStart; i < yEnd; i++)
        {
            if (board[i][xDiag].getText().charAt(0) == currentPiece)
                marksDiagLR++;
            xDiag++;
            if (board[i][xDiagBackwards].getText().charAt(0) == currentPiece)
                marksDiagRL++;
            xDiagBackwards--;
        }



        if (marksDiagRL == 3 || marksDiagLR == 3)
        {
            bigBoard[yBox-1][xBox-1] = currentPiece;
            for (int m = yStart; m < yEnd; m++)
                for (int n = xStart; n < xEnd; n++)
                    board[m][n].setDisable(true);
        }
    }
}

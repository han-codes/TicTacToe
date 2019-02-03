package com.csci4020.tic_tac_toe;
/*
CSCI 4020
Assignment 1
Han Kim
Brian Lake
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RandomActivity extends Activity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    // Todo: For now we start w/ player 1 but once we generate random code, the first player will be random
    private boolean player1Turn = true;

    // counts the # of rounds. If round gets more than 9, which is the max, we know it's a draw
    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        // loop through our rows and columns of buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;

                // set click listeners on the buttons
                int resId = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    /**
     * Give
     */
    @Override
    public void onClick(View v) {
        // checks if button has already been used
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        // if it's player one's turn and they click a button, give the button a text of X or O
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        // check who wins the game
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            // if no one wins and no more rounds left, it's a draw
            draw();
        } else {
            // if no one won and there's no draw, change who's turn it is
            player1Turn = !player1Turn;

        }
    }

    /**
     * Check the board to see if either player 1 or player 2 has won
     */
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // loop through all the buttons and save the buttons text as a string, either X or O
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // checks each row to make sure each field has the same text, either X or O
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        // checks each column to make sure each field has the same text, either X or O
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        // checks diagonal from left to right
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        // checks diagonal from right to left
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    /**
     * When player 1 wins, increment their points, display message, update the points text, and reset the board
     */
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText(); // will update points
        resetBoard(); // will reset the board
    }

    /**
     * When player 2 wins, increment their points, display message, update the points text, and reset the board
     */
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText(); // will update points
        resetBoard(); // will reset the board
    }

    /**
     * Reset the board when there is a draw
     */
    private void draw() {
        Toast.makeText(this, "It's a Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    /**
     * Update the TextView to display points of each player
     */
    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    /**
     * Resets the board so players can play a new game
     */
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;

        // TODO: when board resets, there should be no player until the Random button is pressed
        player1Turn = true;
    }

    /**
     * Resets points of hte players, updates the TextView of the points, and resets the board
     */
    private void resetGame() {
        // reset game and points
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();

    }

    /**
     * Saves the state of our variables so it's not affected when orientation changes
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    /**
     * Reaches the values from onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}

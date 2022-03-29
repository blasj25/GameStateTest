package com.gmail.gamestatetest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button runTestButt = findViewById(R.id.runTestButt);
        TextView testTV = findViewById(R.id.testTV);
        Listener listener = new Listener(testTV, runTestButt);
        runTestButt.setOnClickListener(listener);

    }

    public class Listener implements View.OnClickListener {
        TextView textView;
        Button testButt;

        public Listener(TextView initTV, Button initTestButt) {
            textView = initTV;
            testButt = initTestButt;
        }

        public void runTest(TextView textView) {
            textView.setText("");
            //start the game
            GameState firstInstance = new GameState();
            GameState firstCopy = new GameState(firstInstance);
            textView.append("\n" + "Current phase is " + firstInstance.getCurrentPhase() + "\n");
            firstInstance.initDeck();
            firstInstance.dealCards();
            textView.setText("Cards have been dealt!" + "\n");
            //display current players' info
            textView.append("\n" + "Card List" + firstInstance.cardList.toString() + "\n");
            textView.append("\n" + "Player 1 info: " + firstInstance.player0.toString() + "\n");
            textView.append("\n" + "Player 2 info: " + firstInstance.player1.toString() + "\n");
            //move to next phase of the game
            firstInstance.setNextPhase();
            textView.append("\n" + "Phase changed to " + firstInstance.getCurrentPhase() + "\n");
            firstInstance.betAction(firstInstance.player0, 25000);
            textView.append("\n" + firstInstance.player0.getName() + " bet: " + firstInstance.getCurrentBet() + "\n");
            //show updated info to show that method worked
            textView.append("\n" + "Player 1 info: " + firstInstance.player0.toString() + "\n");
            firstInstance.betAction(firstInstance.player1, 50000);
            textView.append("\n" + firstInstance.player1.getName() + " bet: " + firstInstance.getCurrentBet() + "\n");
            textView.append("\n" + "Player 2 info: " + firstInstance.player1.toString() + "\n");
            //move to next phase
            firstInstance.setNextPhase();
            textView.append("\n" + "Phase changed to: " + firstInstance.getCurrentPhase() + "\n");
            firstInstance.playerTurnID=0;
            firstInstance.drawCard(firstInstance.player0, 2);
            textView.append("\n" + firstInstance.player0.getName() + " has drawn" + "1 card" + "\n");

            firstInstance.setNextPhase();
            textView.append("\n" + "Phase changed to: " + firstInstance.getCurrentPhase() + "\n");
            firstInstance.betAction(firstInstance.player0, firstInstance.currentBet);
            firstInstance.foldAction(firstInstance.player1);
            firstInstance.foldAction(firstInstance.player2);
            textView.append("\n" + firstInstance.player1.getName() + " has folded" + "\n");
            textView.append("\n" + "Player 1 info: " + firstInstance.player0.toString() + "\n");
            textView.append("\n" + "Player 2 info: " + firstInstance.player1.toString() + "\n");
            textView.append("\n" + firstInstance.allPlayersFolded());

        }

        @Override
        public void onClick(View view) {
            runTest(textView);
        }
    }


}
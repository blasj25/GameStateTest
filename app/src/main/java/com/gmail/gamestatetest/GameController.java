package com.gmail.gamestatetest;

import android.view.View;
import android.widget.Button;

public class GameController implements View.OnClickListener{
    com.gmail.gamestatetest.GameState gameState;
    com.gmail.gamestatetest.Player localPlayer;
    Button foldButt;
    Button drawButt;
    Button betButt;
    Button checkButt;
    Button holdButt;
    public GameController(com.gmail.gamestatetest.GameState gs, com.gmail.gamestatetest.Player initLocalPlayer, Button initFoldButt, Button initDrawButt,
                          Button initBetButt, Button initCheckButt, Button initHoldButt){
        /* TODO add the rest of the views to the controller constructor  */
        gameState = new com.gmail.gamestatetest.GameState(gs);
        localPlayer = initLocalPlayer;
        Button foldButt = initFoldButt;
        Button drawButt = initDrawButt;
        Button betButt = initBetButt;
        Button checkButt = initCheckButt;
        Button holdButt = initHoldButt;


    }

    @Override
    public void onClick(View view) {
        /* TODO implement the actions with the corresponding button */
        if(view == foldButt){
            gameState.foldAction(localPlayer);
        }else if(view == drawButt){
            //gameState.drawCard(localPlayer, x);
        }
    }
}

package com.gmail.gamestatetest;

public class ComputerPlayer extends com.gmail.gamestatetest.Player {

    public ComputerPlayer(int initScore, int initTurnID){
        super("Computer "+(initTurnID-1) , initScore, initTurnID);
    }
    public ComputerPlayer(ComputerPlayer orig){
        super(orig);
    }
}

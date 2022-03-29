package com.gmail.gamestatetest;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class holds the primary methods for human and computer players
 */
public class Player {
    String name;
    int score;
    int turnID;
    List<Card> cards;
    boolean isFolded;

    public Player(String initName, int initScore, int initTurnID){
        this.name = initName;
        this.score = initScore;
        this.turnID = initTurnID;
        isFolded = false;
        cards = new ArrayList<>();
        initHand();
    }

    public Player(Player orig){
        this.name = orig.name;
        this.score = orig.score;
        this.turnID = orig.turnID;
        this.isFolded = orig.isFolded;
        this.cards = new ArrayList<>();
            for(Card c : orig.cards){
                this.cards.add(new Card(c));
            }
    }

    public int getTurnID(){
        return turnID;
    }
    public void addCard(int index, Card card){
        cards.set(index, card);
    }

    public int getScore() {
        return score;
    }
    public boolean getIsFolded(){return isFolded; }

    public String getName() {
        return name;
    }

    public void initHand(){
        for(int c = 0; c < 4; c++){
            Card card = new Card(0, 0);
            cards.add(c,card);
        }
    }

    public String getCards() {
        return cards.toString();
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public void setFolded(boolean newStat){
        isFolded = newStat;
    }

    @NonNull
    @Override
    public String toString(){
        return ("\n" + "Name: " + this.getName() + "\n" + "Turn ID: " + this.getTurnID() + "\n" + "Score: " + this.getScore() + "\n" + "Has folded: " +
                this.getIsFolded() + "\n" + "Hand: " + this.getCards());
    }
}

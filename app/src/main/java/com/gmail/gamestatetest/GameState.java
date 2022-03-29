package com.gmail.gamestatetest;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameState {
    int roundCount;
    int minBet;
    int currentBet;
    int currentPot;
    int playerTurnID;
    int player0Score;
    int player1Score;
    int player2Score;
    com.gmail.gamestatetest.Player[] players = new com.gmail.gamestatetest.Player[3];
    com.gmail.gamestatetest.HumanPlayer player0;
    ComputerPlayer player1;
    ComputerPlayer player2;
    List <com.gmail.gamestatetest.Card> cardList = new ArrayList<>();

    String[] phases = {"Opening", "Betting", "Drawing", "Showing"};
    String currentPhase;

    /**
     * ctor makes a new a game
     */
    public GameState(){
        roundCount = 1;
        minBet = 2500;
        currentBet = 0;
        currentPot = 0;
        playerTurnID = 0;
        player0Score = 200000;
        player1Score = 200000;
        player2Score = 200000;
        setPhase(0);

        player0 = new com.gmail.gamestatetest.HumanPlayer("Muskilliblantani", player0Score, 0);
        player1 = new ComputerPlayer(player1Score, 1);
        player2 = new ComputerPlayer(player2Score, 2);

        players[0] = player0;
        players[1] = player1;
        players[2] = player2;


        initDeck();

        if(getCurrentPhase().equals("Opening")){
            dealCards();
        }


    }

    /**
     * Copy ctor
     * @param orig is the original GameState that is being copied
     */
    public GameState(GameState orig){
        this.minBet = orig.minBet;
        this.currentBet = orig.currentBet;
        this.currentPot = orig.currentPot;
        this.players = new com.gmail.gamestatetest.Player[3];
            for(int i = 0; i < 3; i++){
                this.players[i] = new com.gmail.gamestatetest.Player(orig.players[i]);
            }
        this.currentPhase = orig.getCurrentPhase();
        this.playerTurnID = orig.playerTurnID;
        this.phases = orig.phases;
        this.roundCount = orig.roundCount;

        this.player0Score = orig.player0Score;
        this.player1Score = orig.player1Score;
        this.player2Score = orig.player2Score;

        this.player0 = new com.gmail.gamestatetest.HumanPlayer(orig.player0);
        this.player1 = new ComputerPlayer(orig.player1);
        this.player2 = new ComputerPlayer(orig.player2);


        this.cardList = new ArrayList<>();
            for(com.gmail.gamestatetest.Card c : orig.cardList){
                    this.cardList.add(new com.gmail.gamestatetest.Card(c));
            }

    }






    /**
     * foldAction method is used to check if the player is making and valid
     * move and allow the player to fold if it is their turn.
     *
     * @param p player who is trying to make the move
     * @return true if user can make the move
     */
    public boolean foldAction(com.gmail.gamestatetest.Player p){
        if(p.getTurnID() == playerTurnID){
            if(!p.isFolded) {
                if (getCurrentPhase().equals("Betting")) {
                    p.cards.clear();
                    p.setFolded(true);
                    setNextPlayer();
                    setNextPhase();
                    if(allPlayersFolded()){
                        setPhase(3);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * allows the player to place a bet if it's their turn
     * @param p is the player that is attempting to make the move
     * @param betAmount is the amount that the player is trying to bet
     * @return true if the move was valid || false otherwise
     */
    public boolean betAction(com.gmail.gamestatetest.Player p, int betAmount){
        if(p.getTurnID() == playerTurnID){
            if(getCurrentPhase().equals("Betting")){
                if(betAmount > minBet && betAmount >= currentBet){
                    if(betAmount > currentBet){
                        currentBet = betAmount;
                        p.setScore(p.getScore() - betAmount);
                        currentPot += currentBet;
                        setNextPlayer();
                    }
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * checks if all players but one has folded
     * @return true is all but one player has folded
     */
    public boolean allPlayersFolded(){
        int numFolded = 0;
        for(com.gmail.gamestatetest.Player player: players){
            if(player.isFolded){
                numFolded += 1;
            }
        }
        return numFolded < (players.length - 2);
    }

    /**
     * sets the playerTurnID to the next player in the sequence
     */
    public void setNextPlayer(){
        playerTurnID += 1;
        if(playerTurnID > players.length-1){
            playerTurnID = 0;
        }
        if(players[playerTurnID].isFolded){
            setNextPlayer();
        }
    }

    /**
     * sets phase to the next phase in the sequence
     */
    public void setNextPhase(){
        if(currentPhase.equalsIgnoreCase("Opening")){
            setPhase(1);
        }else if(currentPhase.equalsIgnoreCase("Betting")){
            if(roundCount <= 2){
                setPhase(2);
            }else{
                setPhase(3);
            }
        }else if(currentPhase.equalsIgnoreCase("Drawing")){
                            setPhase(1);
                roundCount += 1;

        }else if(currentPhase.equalsIgnoreCase("Showing")){
            setPhase(0);
            roundCount = 1;
        }

    }

    /**
     *  called when user attempts to draw a card(s).  checks if users move is valid,
     *  adds a new card to the slot the choose to swap
     *
     * @param p is the player that is trying to draw cards
     * @param index is the index in which that are attempting to swap
     */
    public void drawCard(com.gmail.gamestatetest.Player p, int index){
    Card card = newCard();
        while(card.getIsDealt()){
            card = newCard();
        }
        if(drawAction(p)){
            if (!card.getIsDealt()) {
                p.addCard(index, card);
                card.setIsDealt(true);
            }
        }
    }

    public boolean drawAction(com.gmail.gamestatetest.Player p){
        if(p.getTurnID() == playerTurnID){
            if(this.getCurrentPhase().equals("Drawing")){
                return true;
            }
        }
        return false;
    }



    /**
     * this method gets a new card
     */
    public Card newCard(){
        Random gen = new Random();
        int newCard = gen.nextInt(cardList.size()-1);
        Card card = cardList.get(newCard);
        return card;
    }
    /**
     * this method deals new cards to each player in game
     */
    public void dealCards(){


        for (com.gmail.gamestatetest.Player player : players) {
            for (int j = 0; j < 4; j++) {// 4 is player hand size
                Card card = newCard();
                if(!card.getIsDealt()) {
                    player.addCard(j, card);
                    card.setIsDealt(true);
                }

            }
        }


    }

    /**
     * initDeck method initializes the deck as all new and unused, also
     * serves as shuffle.
     */
    public void initDeck(){
        int index = 0;
        //sets names for all the cards
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                com.gmail.gamestatetest.Card card = new com.gmail.gamestatetest.Card(j, i);
                cardList.add(index, card);
                index++;
            }
        }
        for(int card = 0; card < cardList.size(); card++){
            cardList.get(card).setIsDealt(false);
        }

    }

    public boolean isOver(){
        if(allPlayersFolded()){
            return true;
        }
        return false;
    }

    public void setPhase(int index){
        currentPhase = phases[index];
    }
    public String getCurrentPhase(){
        return currentPhase;
    }
    public int getCurrentBet() {
        return currentBet;
    }
    public int getMinBet(){
        return currentBet;
    }
    public int getPlayer0Score(){
        return player0Score;
    }
    public int getPlayer1Score(){
        return player1Score;
    }
    public int getPlayer2Score(){
        return player2Score;
    }
    public int getPlayerTurnID(){
        return playerTurnID;
    }
    public com.gmail.gamestatetest.Player[] getPlayers() {
        return players;
    }
    public List<com.gmail.gamestatetest.Card> getCardList() {
        return cardList;
    }



    @NonNull
    @Override
    public String toString(){
        com.gmail.gamestatetest.Player[] curPlayers = getPlayers();
        int curPlayerTurn = getPlayerTurnID();
        int curPlayer0Score = getPlayer0Score();
        int curPlayer1Score = getPlayer1Score();
        int curPlayer2Score = getPlayer2Score();
        List<com.gmail.gamestatetest.Card> curCards = getCardList();
        int curMinBet = getMinBet();
        int curBet = getCurrentBet();
        String curPhase = getCurrentPhase();
        String curPlayer0Cards = player0.getCards();
        String curPlayer1Cards = player1.getCards();
        String curPlayer2Cards = player2.getCards();
        return "Current Players: " + Arrays.toString(curPlayers) + "\n" + "Turn: " + curPlayerTurn +
                "\n" + "Player Scores: " + "\n" + "Player 1: " + curPlayer0Score +
                "\n" + "Player 2: " + curPlayer1Score + "\n" + "Player 3: " + curPlayer2Score
                + "\n" + "Current Cards: " + curCards.toString() + "\n" + "Player 1 Cards: " + curPlayer0Cards
                + "Player 2 Cards: " + curPlayer1Cards + "\n" + "Player 3 Cards: " + curPlayer2Cards
                +"Min bet: " + curMinBet + "\n" + "Current Bet: " + curBet
                + "\n" + "Current Phase: " + curPhase;
    }
}

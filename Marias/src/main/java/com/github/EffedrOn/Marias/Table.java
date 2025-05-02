package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

public class Table implements TableInterface{
    private IOHandler ioHandler = new IOHandler();
    private Player player1 = new HumanPlayer("Human", ioHandler);
    private Player player2 = new BotPlayer("Bot1");
    private Player player3 = new BotPlayer("Bot2");
    private Player[] players = new Player[]{player1, player2, player3};
    private Deck deck = new Deck();
    int playerOnMove = 0;

    Licitator licitator = new Licitator();

    public Table() {
        System.out.println("Table created");
    }
    @Override
    public void run() {
        deck.shuffle();
        dealCards();
        chooseTriumph();
    }
    private void dealCards() {
        // deal cards to players
        // will call the deal from the deck class
    }
    public void chooseTriumph() {
        ;
    }
    public void rotatePlayers() {
        ;
    }
}

package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.Deck;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

public class Table implements TableInterface{
    // This class should handle the logic that is taking place on the "table" of the game
    // So the dealer and players sits behind the table and the cards are dealed to players
    // I think the instances of players and Table should be created in GameControler from which the logic will handle
    // the game and the loop in which the players play against.
    private IOHandler ioHandler;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player[] players;
    private Deck deck;
    private int playerOnMove;
    Licitator licitator;

    public Table(IOHandler ioHandler) {
        System.out.println("Table created");
        this.ioHandler = ioHandler;
        player1 = new HumanPlayer("Human", this.ioHandler);
        player2 = new HumanPlayer("Bot1", this.ioHandler);
        player3 = new HumanPlayer("Bot2", this.ioHandler);
        players = new Player[]{player1, player2, player3};
        deck = new Deck();
        playerOnMove = 0;
        licitator = new Licitator();
    }
    private void dealCards() {
        // deal cards to players
        // will call the deal from the deck class
        // It should be somehow marked which player deals the cards.
        for (Player player : players) {
            player.setCards(deck.deal());
        }
    }
    private void playTrick() {
        Trick trick = new Trick();
        for (int i = 0; i < players.length; i++) {
            Player p = players[(playerOnMove + i) % 3];
            Card c = p.playCard();
            trick.cards[i] = c;
            ioHandler.printMessage(p.name + " played: " + c);
        }
        ioHandler.printMessage("Trick value: " + trick.getValue());
        trick.reset();
        rotatePlayers();
    }
    public void rotatePlayers() {
        playerOnMove = (playerOnMove + 1) % players.length;
        ;
    }
}

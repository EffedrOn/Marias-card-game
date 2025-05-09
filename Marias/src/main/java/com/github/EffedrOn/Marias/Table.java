package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.Deck;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

import java.util.List;

public class Table implements TableInterface{
    // This class should handle the logic that is taking place on the "table" of the game
    // So the dealer and players sits behind the table and the cards are dealed to players
    // I think the instances of players and Table should be created in GameControler from which the logic will handle
    // the game and the loop in which the players play against.
    private final IOHandler ioHandler;
    private final Player player1;
    private final Player player2;
    private final Player player3;
    private final Player[] players;
    private final Deck deck;
    private int playerOnMove;
    Licitator licitator;

    public Table(IOHandler ioHandler) {
        System.out.println("Table created");
        this.ioHandler = ioHandler;
        player1 = new HumanPlayer("Human", this.ioHandler);
        player2 = new BotPlayer("Bot1", this.ioHandler);
        player3 = new BotPlayer("Bot2", this.ioHandler);
        players = new Player[]{player1, player2, player3};
        deck = new Deck();
        playerOnMove = 0;
        licitator = new Licitator();
    }
    public void dealCards() {
        // deal cards to players
        // will call the deal from the deck class
        // It should be somehow marked which player deals the cards.
        for (Player player : players) {
            player.setCards(deck.deal());
        }
        // Printout of dealed cards to players
        for (Player player : players) {
            Hand phand = player.getHand();
            List<Card> pards = phand.getCards();
            System.out.println("Player" + player.name + "cards");
            for (Card card : pards) {
                System.out.println(card.toString());
            }
        }
    }
    public void playTrick() {
        Trick trick = new Trick();
        for (int i = 0; i < players.length; i++) {
            Player p = players[(playerOnMove + i) % 3];
            Card c = p.playCard();
            trick.cards[i] = c;
            ioHandler.printMessage(p.name + " played: " + c);
        }
        ioHandler.printMessage("Trick value: " + trick.getValue());
        trick.reset();
    }

    public void rotatePlayers() {
        playerOnMove = (playerOnMove + 1) % players.length;
    }

    public void chooseTrump() {
        Player p = players[playerOnMove];
        Card c = p.chooseTrump();
    }

    public boolean end() {
        for (Player player : players) {
            if (!player.getHand().getCards().isEmpty()) {
                return false; // If any player still has cards, game continues
            }
        }
        return true; // All players have no cards left
    }

    public void shuffleCards() {
        deck.shuffle();
    }
}

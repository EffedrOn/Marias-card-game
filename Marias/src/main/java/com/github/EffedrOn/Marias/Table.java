package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;
import com.github.EffedrOn.Marias.DeckOfCards.Deck;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

public class Table implements TableInterface {
    // This class should handle the logic that is taking place on the "table" of the game
    // So the dealer and players sits behind the table and the cards are dealed to players
    // ?I think the instances of players and Table should be created in GameControler from which the logic will handle the game and the loop in which the players play against??
    private final IOHandler ioHandler;
    private final Player player1;
    private final Player player2;
    private final Player player3;
    private final Player[] players;
    private final Deck deck;
    private int playerOnMove;
    private Licitator licitator;
    private Card trump;

    public Table(IOHandler ioHandler) {
        System.out.println("Table created");
        this.ioHandler = ioHandler;
        player1 = new HumanPlayer("Human", this.ioHandler);
        player2 = new BotPlayer("Bot1", this.ioHandler);
        player3 = new BotPlayer("Bot2", this.ioHandler);
        players = new Player[]{player1, player2, player3};
        deck = new Deck();
        playerOnMove = 0;            // Zatial bude zacinat vzdy human player
        licitator = new Licitator(); // tu si budu hraci vyberat typ hry a sadzbu
    }

    public void dealCards() {
        // deal cards to players
        // will call the deal from the deck class
        // It should be somehow marked which player deals the cards.
        for (Player player : players) {
            player.setCards(deck.deal());
        }
        /*
        // Printout of dealed cards to players
        for (Player player : players) {
            Hand phand = player.getHand();
            List<Card> pards = phand.getCards();
            System.out.println("Player" + player.name + "cards");
            for (Card card : pards) {
                System.out.println(card.toString());
            }
        }
         */
    }

    public void playTrick() {
        // The player who wins the trick should start the new one.
        Trick trick = new Trick();
        //Player firstPlayer = players[playerOnMove];

        Card[] playedCards = new Card[3];
        int[] playerIndexes = new int[3];

        // play one round
        for (int i = 0; i < players.length; i++) {
            //int playerIndex = (playerOnMove + i) % 3;
            Player currentPlayer = players[playerOnMove];

            if (currentPlayer instanceof HumanPlayer) {
                String trumpSymbol = Card.SUIT_SYMBOLS[trump.getSuit()];
                ioHandler.printMessage( "Trump suit is: " + trumpSymbol );
            }

            Card card = currentPlayer.playCard();
            playedCards[i] = card;
            playerIndexes[i] = playerOnMove;

            ioHandler.printMessage(currentPlayer.name + " played: " + card);
            trick.cards[i] = card;

            rotatePlayers();
        }

        // Determine winner
        CardComparator comparator = new CardComparator(trump.getSuit());
        int winningCardIndex = 0;

        for (int i = 1; i < playedCards.length; i++) {
            if (comparator.compare(playedCards[i], playedCards[winningCardIndex]) < 0) {
                winningCardIndex = i;
            }
        }

       Player winner = players[playerIndexes[winningCardIndex]];

        // could store trick points to the winner here
        for (int i = 0; i < players.length; i++) {
            if (players[i] == winner) {
                playerOnMove = i;
                break;
            }
        }

        ioHandler.printMessage(players[playerOnMove].name + " wins the trick!");
        ioHandler.printMessage("Trick value: " + trick.getValue());
        ioHandler.printMessage("----------------------------------");
        trick.reset();
    }

    public void rotatePlayers() {
        playerOnMove = (playerOnMove + 1) % players.length;
    }

    public void chooseTrump() {
        Player p = players[playerOnMove];
        Card c = p.chooseTrump();
        this.trump = c;
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

    public Card getTrump() {
        return this.trump;
    }
}

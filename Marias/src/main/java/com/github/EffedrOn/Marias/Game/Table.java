package com.github.EffedrOn.Marias.Game;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;
import com.github.EffedrOn.Marias.DeckOfCards.Deck;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;
import com.github.EffedrOn.Marias.Trick;

import java.util.List;

/**
 * Table of the marias card game
 * <p>
 * This class simulates the table environment where the Marias game takes place.
 * It handles dealing cards, managing turns, validating card plays, and determining
 * the winner of each trick. It connects the players with the deck and the IOHandler
 * for user interactions.
 * Players play cards against each other to win the trick and rotates after every trick.
 * </p>
 *
 * <p>
 * GameController is responsible for creating an instance of this Table and
 * controlling the overall game loop.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Table implements TableInterface {
    /** Handler for input/output operations */
    private final IOHandler ioHandler;
    /** Player instances */
    private final Player player1, player2, player3;
    /** Player array for rotation */
    private final Player[] players;
    /** Deck of cards used in the game */
    private final Deck deck;
    /** Index of the player currently on move */
    private int playerOnMove;
    /** Current trump card */
    private Card trump;

    /**
     * Constructs the Table with players and initializes the game deck.
     *
     * @param ioHandler handler for input/output messages
     * @param player1 first player
     * @param player2 second player
     * @param player3 third player
     */
    public Table(IOHandler ioHandler, Player player1, Player player2, Player player3) {
        this.ioHandler = ioHandler;
        ioHandler.printInfo("Table created");

        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;

        this.players = new Player[]{player1, player2, player3};
        this.deck = new Deck();
        ioHandler.printInfo("Deck created");

        playerOnMove = 0;
        ioHandler.printInfo("End of Table initialization");
    }

    /**
     * Deals the initial hand of cards to all players.
     * First player gets 7 cards, others get 10.
     */
    @Override
    public void dealCards() {
        ioHandler.printInfo("Dealing Cards");

        players[playerOnMove].setCards(deck.deal(7));
        players[(playerOnMove + 1) % 3].setCards(deck.deal(10));
        players[(playerOnMove + 2) % 3].setCards(deck.deal(10));
    }

    /**
     * Deals the remaining 5 hidden cards to the first player after trump is selected.
     */
    @Override
    public void dealFirstPlayer() {
        players[playerOnMove].setCards(deck.deal(5));
    }

    /**
     * Sets the index of the starting player.
     *
     * @param index index of the player to start
     */
    @Override
    public void setStartingPlayer(int index) {
        this.playerOnMove = index;
    }

    /**
     * First player chooses 2 cards that he has to throw away after he chose the trump.
     */
    @Override
    public void throwAwayCards() {
        Player current = players[playerOnMove];
        if (current instanceof HumanPlayer) {
            List<Card> cards = current.getHand().getCards();
            Card firstCard = null;
            Card secondCard = null;
            ioHandler.printPrompt("Your hand (choose 2 cards to throw away)");
            ioHandler.printHand(current.getHand());

            int firstIndex = -1;
            int secondIndex = -1;

            while (true) {
                // Until the player chooses 2 correct cards to throw away he has to choose again.
                try {
                    firstIndex = ioHandler.readCardIndex("Enter index of the FIRST card to throw away", cards.size());
                    secondIndex = ioHandler.readCardIndex("Enter index of the SECOND card to throw away", cards.size());
                    if (firstIndex == secondIndex) {
                        ioHandler.printError("You cannot throw away the same card twice.");
                        continue;
                    }

                    firstCard = cards.get(firstIndex);
                    secondCard = cards.get(secondIndex);

                    // Check if any card is Ace or 10
                    if (isHighValueCard(firstCard) || isHighValueCard(secondCard)) {
                        ioHandler.printError("You cannot throw away an Ace or a 10. Pick different cards.");
                        continue;
                    }
                    break; // input is valid
                } catch (NumberFormatException e) {
                    ioHandler.printError("Please enter valid numbers");
                }
            }

            // The case when firstCard or secondCard remains null should not be possible (because of while loop)
            // Remove higher index first to avoid shifting
            if (firstIndex > secondIndex) {
                current.getHand().removeCard(firstCard);
                current.getHand().removeCard(secondCard);
            } else {
                current.getHand().removeCard(secondCard);
                current.getHand().removeCard(firstCard);
            }
            ioHandler.printInfo("Two cards have been thrown away");
        } else {
            current.throwAwayTwoCardsAutomatically(trump.getSuit());
            ioHandler.printInfo(current.name + " threw away two cards.");
        }
        ioHandler.printSeparator();
    }

    /**
     * Checks if a card is considered a high-value card (Ace or Ten).
     *
     * @param card the card to check
     * @return true if high-value, false otherwise
     */
    private boolean isHighValueCard(Card card) {
        int rank = card.getRank();
        return rank == Card.ACE || rank == Card.TEN;
    }

    /**
     * Conducts a full round of play (trick), allowing each player to play one card.
     * Validates card play rules and determines the winner.
     * Winner of this trick is determined and this player starts first in next round.
     */
    @Override
    public void playTrick() {
        Trick trick = new Trick();

        // play one round
        for (int i = 0; i < players.length; i++) {
            Player currentPlayer = players[playerOnMove];

            if (currentPlayer instanceof HumanPlayer) {
                String trumpSymbol = Card.SUIT_SYMBOLS[trump.getSuit()];
                ioHandler.printInfo( "Trump suit is: " + trumpSymbol );
            }

            Card card = currentPlayer.playCard();

            // Check if the card which currentPlayer played is really correct by rules
            boolean correctlyPlayedCard = false;
            while (!correctlyPlayedCard) {
                correctlyPlayedCard = checkPlayedCard(currentPlayer.getHand(), card, trick);
                if (!correctlyPlayedCard) {
                    if( currentPlayer instanceof HumanPlayer ) {
                        ioHandler.printError( "Stop cheating!");
                    }
                    card = currentPlayer.playCard();
                }
            }

            // Remove the played card from hand of player
            currentPlayer.confirmPlayedCard(card);

            ioHandler.printPlayedCard(currentPlayer, card);

            // Check for marriage after playing a card
            if (card.getRank() == Card.KING || card.getRank() == Card.HORNIK) {
                List<Card> remainingCards = currentPlayer.getHand().getCards();
                int counterpartRank = (card.getRank() == Card.KING) ? Card.HORNIK : Card.KING;

                final int suit = card.getSuit();
                boolean hasPair = remainingCards.stream().anyMatch(c -> c.getSuit() == suit && c.getRank() == counterpartRank);
                if (hasPair) {
                    int marriagePoints = (card.getSuit() == trump.getSuit()) ? 40 : 20;
                    currentPlayer.addMarriagePoints(marriagePoints);
                    ioHandler.printMarriage(currentPlayer, card, marriagePoints, card.getSuit() == trump.getSuit());
                }
            }

            trick.addCard(card, playerOnMove);
            // Set the playerOnMove for the next one in order
            rotatePlayers();
        }

        // Determine winner of the trick
        boolean isLastTrick = end(); // "Ultimo"
        determineWinner(trick, isLastTrick);
    }

    /**
     * Determines the winner of the trick and awards it.
     * Handles bonus points for the last trick.
     *
     * @param trick the trick that was played
     * @param isLastTrick true if this is the final trick
     */
    private void determineWinner(Trick trick, boolean isLastTrick) {
        // Determine winner of trick
        int winnerPlayerIndex = trick.getWinnerPlayerIndex(trump);
        Player winner = players[winnerPlayerIndex];

        // Informative printout who won each trick
        ioHandler.printInfo(winner.name + " wins the trick!");
        ioHandler.printInfo("Trick value: " + trick.getValue());
        if (isLastTrick) {
            trick.setBonusPoints(10);
            ioHandler.printInfo("Bonus: Last trick! 10 points awarded to " + winner.name);
        }
        ioHandler.printSeparator();

        // The player who won this trick will take it
        winner.addTrick(trick);
        // The player who wins the trick should start the new one.
        setPlayerOnMove(winner);
    }
    /**
     * Set player who won the trick to start in next round
     * @param winner
     */
    private void setPlayerOnMove(Player winner) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == winner) {
                playerOnMove = i;
                break;
            }
        }
    }

    /**
     * Validates whether a card was legally played according to game rules.
     *
     * @param hand the player's current hand
     * @param card the card being played
     * @param trick the current trick state
     * @return true if the play is legal, false otherwise
     */
    private boolean checkPlayedCard(Hand hand, Card card, Trick trick) {
        if (trick.getFirstCard() == null) {
            return true; // First to play, no rules apply
        }
        int firstSuit = trick.getFirstCardSuit();
        int trumpSuit = trump.getSuit();
        List<Card> playerCards = hand.getCards();

        boolean hasSameSuit = false;
        boolean hasHigherSameSuit = false;
        boolean hasTrump = false;

        Card highestSameSuit = trick.getHighestCardOfSuit(firstSuit, trump);
        Card highestOverall = trick.getHighestCard(trump);
        CardComparator comparator = new CardComparator(trumpSuit);

        for (Card c : playerCards) {
            if (c.getSuit() == firstSuit) {
                hasSameSuit = true;
                if (comparator.compare(c, highestSameSuit) < 0) {
                    hasHigherSameSuit = true;
                }
            }

            if (c.getSuit() == trumpSuit) {
                hasTrump = true;
            }
        }

        // Rule 1: Must follow suit
        if (hasSameSuit && card.getSuit() != firstSuit) {
            return false;
        }

        // Rule 2: If has higher card in same suit than current highest of that suit → must use it
        if (card.getSuit() == firstSuit && hasHigherSameSuit) {
            if (comparator.compare(card, highestSameSuit) >= 0) {
                return false; // Played lower card while having higher
            }
        }

        // Rule 3: If can't follow suit and has trump, must do it
        boolean playedTrump = card.getSuit() == trumpSuit;
        boolean canWinWithTrump = false;
        for (Card c : playerCards) {
            if (c.getSuit() == trumpSuit && comparator.compare(c, highestOverall) < 0) {
                canWinWithTrump = true;
            }
        }

        if (!hasSameSuit && canWinWithTrump && !playedTrump) {
            return false; // Had winning trump but didn't use it
        }

        if (!hasSameSuit && hasTrump && !playedTrump) {
            return false; // Had trump but didnt confess it
        }

        return true;
    }

    /**
     * Rotates the active player index to the next one in the sequence.
     */
    @Override
    public void rotatePlayers() {
        playerOnMove = (playerOnMove + 1) % players.length;
    }

    /**
     * Prompts the current player to choose the trump suit.
     */
    @Override
    public void chooseTrump() {
        Player p = players[playerOnMove];
        this.trump = p.chooseTrump();
    }

    /**
     * Checks if the game has ended (i.e., all players have no cards left).
     *
     * @return true if game is over, false otherwise
     */
    @Override
    public boolean end() {
        for (Player player : players) {
            if (!player.getHand().getCards().isEmpty()) {
                return false; // If any player still has cards, game continues
            }
        }
        return true; // All players have no cards left
    }

    /**
     * Shuffles the deck and resets its state.
     */
    @Override
    public void shuffleCards() {
        deck.reset();
        deck.shuffle();
    }
}

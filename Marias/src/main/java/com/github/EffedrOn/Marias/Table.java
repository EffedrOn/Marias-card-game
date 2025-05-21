package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;
import com.github.EffedrOn.Marias.DeckOfCards.Deck;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

import java.util.List;

/**
 * Table of the marias card game
 * The Table behind which players sit and play the game
 * On table deck of cards lays, cards are dealed
 * Players play cards against each other to win the trick and rotates
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
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

    /**
     * Constructor of table class to hold game objects and logic that takes place here.
     * @param ioHandler for reading and printing inputs/outputs
     * @param player1
     * @param player2
     * @param player3
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

        playerOnMove = 0;            // Zatial bude zacinat vzdy human player
        licitator = new Licitator(); // tu si budu hraci vyberat typ hry a sadzbu
        ioHandler.printInfo("End of Table initialization");
        ioHandler.printSeparator();
    }

    /**
     * Deal the first round of cards to all players.
     * First player gets 7 cards from which he chooses trump.
     * Second and third player gets 10 cards
     */
    public void dealCards() {
        // Prvy hrac(Human) dostane 7 kariet a dalsich 5 nevidi, zo 7 vyberie trumf potom zo vsetkych 12 vyhodi 2 do talonu
        // Dalsi hraci dostanu po 10 kariet.
        // deal cards to players
        // will call the deal from the deck class
        // It should be somehow marked which player deals the cards.
        ioHandler.printInfo("Dealing Cards");

        player1.setCards(deck.deal(7));
        player2.setCards(deck.deal(10));
        player3.setCards(deck.deal(10));

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

    public void setStartingPlayer(int index) {
        this.playerOnMove = index;
    }

    /**
     * Deal the remaining 5 cards to first player, in second round of dealing.
     */
    public void dealFirstPlayer() {
        player1.setCards(deck.deal(5));
    }

    /**
     * First player chooses 2 cards that he has to throw away after he cose the trump.
     */
    public void throwAwayCards() {
        List<Card> cards = player1.getHand().getCards();
        ioHandler.printPrompt("Your hand (choose 2 cards to throw away)");
        ioHandler.printHand(player1.getHand());

        int firstIndex = -1;
        int secondIndex = -1;

        while (true) {
            // Until the player chooses correct 2 cards to throw away he has to choose again.
            try {
                firstIndex = ioHandler.readCardIndex("Enter index of the FIRST card to throw away", cards.size());
                secondIndex = ioHandler.readCardIndex("Enter index of the SECOND card to throw away", cards.size());
                if (firstIndex == secondIndex) {
                    ioHandler.printError("You cannot throw away the same card twice.");
                    continue;
                }

                Card firstCard = cards.get(firstIndex);
                Card secondCard = cards.get(secondIndex);

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

        // Remove higher index first to avoid shifting
        if (firstIndex > secondIndex) {
            cards.remove(firstIndex);
            cards.remove(secondIndex);
        } else {
            cards.remove(secondIndex);
            cards.remove(firstIndex);
        }

        ioHandler.printInfo("Two cards have been thrown away");
    }

    /**
     * Check if card is high value (10 or Ace)
     * @param card
     * @return
     */
    private boolean isHighValueCard(Card card) {
        int rank = card.getRank();
        // Assuming Ace = 14, Ten = 10 (adjust if your game uses different values)
        return rank == Card.ACE || rank == Card.TEN;
    }

    /**
     * Play one round of trick.
     * Players rotate and every one play card from his hand.
     * Round of trick ends after every player played card.
     * Winner of this trick is determined and this player starts first in next round.
     */
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
                        ioHandler.printMessage( "Stop cheating!"); // for now only human is printed to pick new index, bots should be designed in a way not to randomly pick index until they get the correct one
                    }
                    card = currentPlayer.playCard();
                }
            }

            // remove the played card from hand of player
            currentPlayer.confirmPlayedCard(card);

            ioHandler.printPlayedCard(currentPlayer, card);
            trick.addCard(card, playerOnMove);

            // Set the playerOnMove for the next one in order
            rotatePlayers();
        }

        // Determine winner of the trick
        boolean isLastTrick = end();
        determineWinner(trick, isLastTrick);
    }

    /**
     * Function that finds the winner of trick
     * sets the winner as player who will start next trick
     * prints informations about the winner and trick value.
     * @param trick
     */
    private void determineWinner(Trick trick, boolean isLastTrick) {
        // Determine winner of trick
        int winnerPlayerIndex = trick.getWinnerPlayerIndex(trump);
        Player winner = players[winnerPlayerIndex];

        // Informative printout who won each trick
        ioHandler.printMessage(winner.name + " wins the trick!");
        ioHandler.printMessage("Trick value: " + trick.getValue());
        if (isLastTrick) {
            trick.setBonusPoints(10);
            ioHandler.printMessage("Bonus: Last trick! 10 points awarded.");
        }
        ioHandler.printSeparator();

        winner.addTrick(trick); // The player who won this trick will take it
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
     * Check if the card played by a player corresponds to rules of the game(color)
     * @param hand of player who played the card
     * @param card which player played
     * @param trick rest of the trick which was played
     * @return boolean  if card was played correctly
     */
    private boolean checkPlayedCard(Hand hand, Card card, Trick trick) {
        // Ak uz hrac predomnou prebil stych trumfom nemusim zahrat vyssiu kartu ako je prva karta v stychu, pretoze ajtak by som nevyhral
        // Farbu vsak musim priznat vzdy ajkeby som stych nemal vyhrat
        // Ak nemam ako prebit stych ale mam vyssiu kartu ako druha najvyssia v stychu nemusim zahrat prave ju
        // ak mam vyssiu kartu musim prebit cely trick
        // ajkeby som nechcel prebit tromfom tak musim

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
        // musi zahrat trumf ajkeby nim nevyhra stych pretoze uz dal niekto silnejsi trumf
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

    public void rotatePlayers() {
        playerOnMove = (playerOnMove + 1) % players.length;
    }

    public void chooseTrump() {
        Player p = players[playerOnMove];
        this.trump = p.chooseTrump();
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
        deck.reset();
        deck.shuffle();
    }

}

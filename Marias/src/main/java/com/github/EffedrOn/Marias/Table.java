package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;
import com.github.EffedrOn.Marias.DeckOfCards.Deck;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

import java.util.List;

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
        this.ioHandler = ioHandler;
        ioHandler.printInfo("Table created");

        this.player1 = new HumanPlayer("Human", this.ioHandler);
        this.player2 = new BotPlayer("Bot1", this.ioHandler);
        this.player3 = new BotPlayer("Bot2", this.ioHandler);

        ioHandler.printInfo("Human player created");
        ioHandler.printInfo("Bot1 player created");
        ioHandler.printInfo("Bot2 player created");

        this.players = new Player[]{player1, player2, player3};
        this.deck = new Deck();
        ioHandler.printInfo("Deck created");

        playerOnMove = 0;            // Zatial bude zacinat vzdy human player
        licitator = new Licitator(); // tu si budu hraci vyberat typ hry a sadzbu
        ioHandler.printInfo("End of Table initialization");
        ioHandler.printSeparator();
    }

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
        // After this player1 should get the remaining 5 cards
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

    public void dealFirstPlayer() {
        player1.setCards(deck.deal(5));
    }

    public void throwAwayCards() {
        List<Card> cards = player1.getHand().getCards();
        //ioHandler.printMessage("Your hand (choose 2 cards to throw away):");
        ioHandler.printPrompt("Your hand (choose 2 cards to throw away)");
        ioHandler.printHand(player1.getHand());

        int firstIndex = -1;
        int secondIndex = -1;

        while (true) {
            try {
                firstIndex = ioHandler.readInt("Enter index of the FIRST card to throw away");
                secondIndex = ioHandler.readInt("Enter index of the SECOND card to throw away");

                if (firstIndex == secondIndex) {
                    ioHandler.printError("You cannot throw away the same card twice.");
                    continue;
                }

                if (firstIndex < 0 || firstIndex >= cards.size() || secondIndex < 0 || secondIndex >= cards.size()) {
                    ioHandler.printError("Invalid indexes. Please enter valid card indexes.");
                    continue;
                }

                Card firstCard = cards.get(firstIndex);
                Card secondCard = cards.get(secondIndex);

                // Check if any card is Ace or 10
                if (isHighValueCard(firstCard) || isHighValueCard(secondCard)) {
                    //ioHandler.printMessage("You cannot throw away an Ace or a 10. Pick different cards.");
                    ioHandler.printError("You cannot throw away an Ace or a 10. Pick different cards.");
                    continue;
                }
                break; // input is valid
            } catch (NumberFormatException e) {
                //ioHandler.printMessage("Please enter valid numbers.");
                ioHandler.printError("Please enter valid numbers.");
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

        ioHandler.printInfo("Two cards have been thrown away.");
    }

    private boolean isHighValueCard(Card card) {
        int rank = card.getRank();
        // Assuming Ace = 14, Ten = 10 (adjust if your game uses different values)
        return rank == Card.ACE || rank == Card.TEN;
    }

    public void playTrick() {
        Trick trick = new Trick();

        // play one round
        for (int i = 0; i < players.length; i++) {
            Player currentPlayer = players[playerOnMove];

            if (currentPlayer instanceof HumanPlayer) {
                String trumpSymbol = Card.SUIT_SYMBOLS[trump.getSuit()];
                //ioHandler.printMessage( "Trump suit is: " + trumpSymbol );
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

            ioHandler.printMessage(currentPlayer.name + " played: " + card); // toto by slo mozno priamo ako funkcia ioHandleru
            trick.addCard(card, playerOnMove);

            // Set the playerOnMove for the next one in order
            rotatePlayers();
        }

        // Determine winner
        int winnerPlayerIndex = trick.getWinnerPlayerIndex(trump);
        Player winner = players[winnerPlayerIndex];

        // The player who wins the trick should start the new one.
        setPlayerOnMove(winner);

        ioHandler.printMessage(players[playerOnMove].name + " wins the trick!");
        ioHandler.printMessage("Trick value: " + trick.getValue());
        ioHandler.printSeparator();
        //trick.reset(); // Not needed because every new time playTrick is called new instance of trick is created
    }

    private void setPlayerOnMove(Player winner) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == winner) {
                playerOnMove = i;
                break;
            }
        }
    }

    // Check if the card played by a player corresponds to rules of the game(color) for now on
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
        deck.shuffle();
    }

    public Card getTrump() {
        return this.trump;
    }
}

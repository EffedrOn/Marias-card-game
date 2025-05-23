package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Trick;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Abstract class for all players, bot and human. Player should decide the actions he will play.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public abstract class Player implements PlayerInterface {
    protected Hand hand;
    public String name;
    protected int score = 0;
    protected int marriagePoints = 0;
    protected int bank;
    protected List<Trick> wonTricks = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.bank = 20; // initially player beggins with 20 cents in his account
    }

    /**
     * Accumulating the trick that this player won
     * @param trick
     */
    public void addTrick(Trick trick) {
        this.wonTricks.add(trick);
    }

    public int getScore() {
        return this.score;
    }

    public void addMarriagePoints(int points) {
        this.marriagePoints += points;
    }

    public int getMarriagePoints() {
        return this.marriagePoints;
    }

    public void resetMarriagePoints() {
        this.marriagePoints = 0;
    }
    /**
      * Add cards into players hand
      */
    public void setCards(Card[] cards) {
        hand.addCards(cards);
    }

    public Hand getHand() {
        return hand;
    }

    /**
     * Removes the card from players hand
     * Used when the card was correctly played in game
     * @param card
     */
    public void confirmPlayedCard(Card card) {
        hand.removeCard(card);  // Only remove after successful validation
    }

    /**
     * Cummulative score of all tricks that player won
     * @return
     */
    public int countTricks() {
        for (Trick trick : wonTricks) {
            this.score += trick.getValue();
        }
        return this.score;
    }

    public void clearTricks() {
        this.wonTricks.clear();
        this.score = 0;
    }

    public void adjustBank(int payoff) {
        this.bank += payoff;
    }

    public int getBank() {
        return this.bank;
    }

    public void throwAwayTwoCardsAutomatically(int trumpSuit) {
        List<Card> cards = hand.getCards();

        // Filter out high-value cards (Ace and 10)
        List<Card> discardable = cards.stream()
                .filter(c -> c.getSuit() != trumpSuit)
                .sorted(Comparator.comparingInt(Card::getRank))
                .toList();

        // If fewer than 2 discardable cards, fall back to lowest overall
        if (discardable.size() < 2) {
            discardable = cards.stream()
                    .sorted(Comparator.comparingInt(Card::getRank))
                    .toList();
        }

        /*
        System.out.println("All cards:");
        for (Card c : cards) {
            System.out.print(c.toString() + " ");
        }
        System.out.println();
        for (Card card : discardable) {
            System.out.print(card.toString() + " ");
        }
        System.out.println();
         */
        Card first = discardable.get(0);
        Card second = discardable.get(1);
        /*
        System.out.println("first and second cards to remove");
        System.out.println(first.toString() + " " + second.toString());
        */
        hand.removeCard(first);
        hand.removeCard(second);
        /*
        List<Card> crds = hand.getCards();
        System.out.println("Hand after removing 2 cards");
        for (Card card : crds) {
            System.out.print(card.toString() + " ");
        }
        System.out.println();
         */
    }
}

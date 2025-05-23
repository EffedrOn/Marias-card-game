package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Trick;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Abstract class for all players (both human and bot) in the Marias card game.
 * <p>
 * Each player has a hand of cards, a score based on won tricks, and a marriage point total.
 * They can play cards, discard, manage their hand, and track game progress.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public abstract class Player implements PlayerInterface {
    /** The player's hand of cards. */
    protected Hand hand;
    /** The name of the player. */
    public String name;
    /** Accumulated score from tricks. */
    protected int score = 0;
    /** Points gained from marriages. */
    protected int marriagePoints = 0;
    /** Player's current bank balance (in cents). */
    protected int bank;
    /** List of tricks won by the player. */
    protected List<Trick> wonTricks = new ArrayList<>();

    /**
     * Constructs a new Player with the given name and an empty hand.
     * Initializes the bank to 20 cents.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.bank = 20; // initially player beggins with 20 cents in his account
    }

    /**
     * Adds a trick to the list of won tricks.
     *
     * @param trick the trick won by the player
     */
    public void addTrick(Trick trick) {
        this.wonTricks.add(trick);
    }

    /**
     * Adds points from a marriage to the player's total.
     *
     * @param points the marriage points to add
     */
    @Override
    public void addMarriagePoints(int points) {
        this.marriagePoints += points;
    }

    /**
     * Returns the current marriage points of the player.
     *
     * @return the marriage points
     */
    @Override
    public int getMarriagePoints() {
        return this.marriagePoints;
    }

    /**
     * Resets the marriage points to zero.
     */
    @Override
    public void resetMarriagePoints() {
        this.marriagePoints = 0;
    }

    /**
     * Adds cards to the player's hand.
     *
     * @param cards the cards to add
     */
    @Override
    public void setCards(Card[] cards) {
        hand.addCards(cards);
    }

    /**
     * Returns the player's hand.
     *
     * @return the player's hand
     */
    @Override
    public Hand getHand() {
        return hand;
    }

    /**
     * Confirms and removes a played card from the player's hand.
     *
     * @param card the card to remove
     */
    @Override
    public void confirmPlayedCard(Card card) {
        hand.removeCard(card);  // Only remove after successful validation
    }

    /**
     * Calculates the total value of all won tricks.
     *
     * @return the cumulative trick score
     */
    @Override
    public int countTricks() {
        for (Trick trick : wonTricks) {
            this.score += trick.getValue();
        }
        return this.score;
    }

    /**
     * Clears all tricks and resets the trick score to zero.
     */
    @Override
    public void clearTricks() {
        this.wonTricks.clear();
        this.score = 0;
    }

    /**
     * Adjusts the player's bank account by the specified payoff.
     *
     * @param payoff the amount to adjust by (positive or negative)
     */
    @Override
    public void adjustBank(int payoff) {
        this.bank += payoff;
    }

    /**
     * Returns the player's current bank balance.
     *
     * @return the bank balance
     */
    @Override
    public int getBank() {
        return this.bank;
    }

    /**
     * Automatically discards two low-value cards from the player's hand.
     * Prioritizes non-trump cards, avoids discarding high-value cards.
     *
     * @param trumpSuit the current trump suit
     */
    @Override
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

        Card first = discardable.get(0);
        Card second = discardable.get(1);

        hand.removeCard(first);
        hand.removeCard(second);

    }
}

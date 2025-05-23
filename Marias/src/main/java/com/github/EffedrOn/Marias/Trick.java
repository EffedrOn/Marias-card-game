package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;

/**
 * Represents a trick in the Marias card game.
 * <p>
 * A trick consists of three cards played by the players in one round.
 * The class tracks which player played which card, determines the value of the trick,
 * and identifies the winning player based on game rules and the trump suit.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Trick implements TrickInterface{
    /** Cards played in this trick, one by each player. */
    Card[] cards = new Card[3];
    /** Indexes of the players who played each card. */
    public int[] playerIndexes  = new int[3];
    /** Counter for how many cards have been added to the trick. */
    private int count = 0;
    /** Optional bonus points awarded for the trick (e.g. last trick bonus). */
    private int bonusPoints = 0;

    /**
     * Adds a card to the trick along with the index of the player who played it.
     *
     * @param card the card played
     * @param playerIndex the index of the player who played the card
     */
    @Override
    public void addCard(Card card, int playerIndex) {
        cards[count] = card;
        playerIndexes[count] = playerIndex;
        count++;
    }

    /**
     * Sets bonus points for the trick (e.g. for the last trick).
     *
     * @param bonus the bonus points to assign
     */
    @Override
    public void setBonusPoints(int bonus) {
        this.bonusPoints = bonus;
    }

    /**
     * Calculates the total value of the trick.
     * Only Aces and Tens contribute points, and additionally also bonuspoints.
     *
     * @return the total value of the trick including bonus points
     */
    @Override
    public int getValue() {
        int sum = 0;
        for (Card c : cards) {
            if (c != null) {
                sum += getCardValue(c);
            }
        }
        sum += bonusPoints; // only if trick is the last one, bonus points are added
        return sum;
    }

    /**
     * Returns the value of a specific card based on its rank.
     *
     * @param card the card to evaluate
     * @return the point value of the card (Ace/Ten = 10, others = 0)
     */
    private int getCardValue(Card card) {
        return switch (card.getRank()) {
            case Card.ACE -> 10;
            case Card.TEN -> 10;
            default -> 0;
        };
    }

    /**
     * Determines the index of the player who won the trick based on trump and rules.
     *
     * @param trump the current trump card
     * @return the player index who won the trick
     */
    @Override
    public int getWinnerPlayerIndex(Card trump) {
        int trumpSuit = trump.getSuit();
        int firstSuit = getFirstCardSuit();

        int winningCardIndex = 0;
        Card winningCard = cards[0];

        CardComparator comparator = new CardComparator(trumpSuit);

        for (int i = 1; i < cards.length; i++) {
            Card current = cards[i];

            boolean isTrump = current.getSuit() == trumpSuit;
            boolean isSameSuitAsFirst = current.getSuit() == firstSuit;

            if (!isTrump && !isSameSuitAsFirst) continue; // Skip invalid (off-suit non-trump) cards

            if (comparator.compare(current, winningCard) < 0) {
                winningCard = current;
                winningCardIndex = i;
            }
        }

        return playerIndexes[winningCardIndex];
    }

    /**
     * Gets the first card played in the trick.
     *
     * @return the first card played
     */
    @Override
    public Card getFirstCard() {
        return cards[0];
    }

    /**
     * Gets the suit of the first card played in the trick.
     *
     * @return the suit of the first card
     */
    @Override
    public int getFirstCardSuit() {
        return cards[0].getSuit();
    }

    /**
     * Returns the highest ranked card of a specific suit in the trick.
     *
     * @param suit the suit to check
     * @param trump the trump card used for comparisons
     * @return the highest card of the given suit
     */
    @Override
    public Card getHighestCardOfSuit(int suit, Card trump) {
        Card highest = null;
        CardComparator comparator = new CardComparator(trump.getSuit());

        for (Card c : cards) {
            if (c != null && c.getSuit() == suit) {
                if (highest == null || comparator.compare(c, highest) < 0) {
                    highest = c;
                }
            }
        }
        return highest;
    }

    /**
     * Returns the highest card played in the trick overall, considering trump rules.
     *
     * @param trump the current trump card
     * @return the highest card in the trick
     */
    @Override
    public Card getHighestCard(Card trump) {
        Card highest = null;
        CardComparator comparator = new CardComparator(trump.getSuit());

        for (Card c : cards) {
            if (c != null) {
                if (highest == null || comparator.compare(c, highest) < 0) {
                    highest = c;
                }
            }
        }
        return highest;
    }
}

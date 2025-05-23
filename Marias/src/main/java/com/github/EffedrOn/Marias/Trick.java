package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;

/**
 * Trick represents cards played by all 3 players in one round.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Trick {
    Card[] cards = new Card[3];
    public int[] playerIndexes  = new int[3];
    private int count = 0;
    private int bonusPoints = 0;

    public void addCard(Card card, int playerIndex) {
        cards[count] = card;
        playerIndexes[count] = playerIndex;
        count++;
    }

    public void setBonusPoints(int bonus) {
        this.bonusPoints = bonus;
    }

    public int getValue() {
        int sum = 0;
        for (Card c : cards) {
            if (c != null) {
                sum += getCardValue(c); // jednoduchý výpočet
            }
        }
        sum += bonusPoints; // only if trick is the last one, bonus points are added
        return sum;
    }

    private int getCardValue(Card card) {
        return switch (card.getRank()) {
            case Card.ACE -> 10;
            case Card.TEN -> 10;
            default -> 0;
        };
    }

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

    public Card getFirstCard() {
        return cards[0];
    }

    public int getFirstCardSuit() {
        return cards[0].getSuit();
    }

    // Get highest card so far in the trick of the given suit
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

    // Get highest card in the trick overall
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

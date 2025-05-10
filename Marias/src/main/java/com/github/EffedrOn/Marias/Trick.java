package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;
import com.github.EffedrOn.Marias.Players.Player;

public class Trick {
    Card[] cards = new Card[3];
    public int[] playerIndexes  = new int[3];
    private int count = 0;

    public void addCard(Card card, int playerIndex) {
        cards[count] = card;
        playerIndexes[count] = playerIndex;
        count++;
    }

    public int getValue() {
        int sum = 0;
        for (Card c : cards) {
            if (c != null) {
                sum += getCardValue(c); // jednoduchý výpočet
            }
        }
        return sum;
    }

    private int getCardValue(Card card) {
        return switch (card.getRank()) {
            case Card.ACE -> 10;
            case Card.TEN -> 10;
            default -> 0;
        };
    }

    public void reset() {
        cards = new Card[3];
    }

    public int getWinnerPlayerIndex(Card trump) {
        // Determine winner
        CardComparator comparator = new CardComparator(trump.getSuit());
        int winningCardIndex = 0;

        for (int i = 1; i < cards.length; i++) {
            if (comparator.compare(cards[i], cards[winningCardIndex]) < 0) {
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

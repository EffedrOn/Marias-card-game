package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.DeckOfCards.CardComparator;

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
}

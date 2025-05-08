package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;

public class Trick {
    Card[] cards = new Card[3];
    public int getValue() {
        int sum = 0;
        for (Card c : cards) {
            if (c != null) {
                sum += c.getRank(); // jednoduchý výpočet
            }
        }
        return sum;
    }
    public void reset() {
        cards = new Card[3];
    }
}

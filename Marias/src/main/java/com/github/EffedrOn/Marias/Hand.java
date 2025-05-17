package com.github.EffedrOn.Marias;

import java.util.Random;
import com.github.EffedrOn.Marias.DeckOfCards.Card;
import java.util.ArrayList;
import java.util.List;

public class Hand implements HandInterface{
    private ArrayList<Card> cards;
    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCards(Card[] cards){
        for(Card card : cards){
            this.cards.add(card);
        }
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }
    public List<Card> getCards() {
        return cards;
    }

    public Card getRandomCard() {
        if (cards.isEmpty()) {
            return null; // or throw an exception, depending on needs
        }

        Random random = new Random();
        int index = random.nextInt(cards.size());
        return cards.get(index);
    }
}

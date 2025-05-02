package com.github.EffedrOn.Marias;

import java.util.List;

public class Hand implements HandInterface{
    private List<Card> cards;
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
}

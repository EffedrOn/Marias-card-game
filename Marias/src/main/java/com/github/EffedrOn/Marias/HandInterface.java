package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;

import java.util.List;

public interface HandInterface {
    void addCards(Card[] cards);
    List<Card> getCards();
    void removeCard(Card card);
}

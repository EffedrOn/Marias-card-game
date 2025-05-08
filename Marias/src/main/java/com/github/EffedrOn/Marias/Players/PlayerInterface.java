package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;

public interface PlayerInterface {
    void setCards(Card[] cards);
    void getBet();
    Card playCard();
    Card chooseTrump();
}

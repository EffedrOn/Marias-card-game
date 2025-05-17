package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Trick;

public interface PlayerInterface {
    void setCards(Card[] cards);
    void getBet();
    Card playCard();
    Card chooseTrump();
    void addTrick(Trick trick);
    int getScore();
}

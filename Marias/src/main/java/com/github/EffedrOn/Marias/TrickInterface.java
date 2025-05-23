package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;

public interface TrickInterface {
    int getValue();
    void addCard(Card card, int playerIndex);
    void setBonusPoints(int bonusPoints);
    int getWinnerPlayerIndex(Card trump);
    Card getFirstCard();
    int getFirstCardSuit();
    Card getHighestCardOfSuit(int suit, Card trump);
    Card getHighestCard(Card trump);
}

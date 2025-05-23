package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Trick;

public interface PlayerInterface {
    void setCards(Card[] cards);
    Card playCard();
    Card chooseTrump();
    void addTrick(Trick trick);
    void addMarriagePoints(int points);
    int getMarriagePoints();
    void resetMarriagePoints();
    Hand getHand();
    void confirmPlayedCard(Card card);
    int countTricks();
    void clearTricks();
    void adjustBank(int payoff);
    int getBank();
    void throwAwayTwoCardsAutomatically(int trumpSuit);
}

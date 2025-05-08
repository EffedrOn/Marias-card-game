package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;

public abstract class Player implements PlayerInterface {
    // Abstract class for all players, bot and human. Player should decide the actions he will play
    protected Hand hand;
    public String name;

    public Player(String name) {
        this.name = name;
    }

    public void setCards(Card[] cards) {
        hand.addCards(cards);
    }

    public Card playCard() {
        Card card = hand.getCards().get(0);
        hand.removeCard(card);
        return card;
    }
}

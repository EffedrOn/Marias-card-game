package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.PlayerInterface;

public abstract class Player implements PlayerInterface {
    protected Hand hand;
    protected String name;

    public Player(String name) {
        this.name = name;
    }

    public void setCards(Card[] cards) {
        hand.addCards(cards);
    }
}

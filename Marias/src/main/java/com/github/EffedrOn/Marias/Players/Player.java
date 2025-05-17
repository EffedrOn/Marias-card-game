package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Trick;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements PlayerInterface {
    // Abstract class for all players, bot and human. Player should decide the actions he will play
    protected Hand hand;
    public String name;
    protected int score = 0;
    protected List<Trick> wonTricks = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public void addTrick(Trick trick) {
        this.wonTricks.add(trick);
    }

    public int getScore() {
        return this.score;
    }

    public void setCards(Card[] cards) {
        hand.addCards(cards);
    }

    public Hand getHand() {
        return hand;
    }

    public void confirmPlayedCard(Card card) {
        hand.removeCard(card);  // Only remove after successful validation
    }

    public int countTricks() {
        for (Trick trick : wonTricks) {
            this.score += trick.getValue();
        }
        return this.score;
    }
}

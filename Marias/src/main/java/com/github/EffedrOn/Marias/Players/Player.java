package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Trick;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all players, bot and human. Player should decide the actions he will play.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public abstract class Player implements PlayerInterface {
    protected Hand hand;
    public String name;
    protected int score = 0;
    protected List<Trick> wonTricks = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    /**
     * Accumulating the trick that this player won
     * @param trick
     */
    public void addTrick(Trick trick) {
        this.wonTricks.add(trick);
    }

    public int getScore() {
        return this.score;
    }

    /**
      * Add cards into players hand
      */
    public void setCards(Card[] cards) {
        hand.addCards(cards);
    }

    public Hand getHand() {
        return hand;
    }

    /**
     * Removes the card from players hand
     * Used when the card was correctly played in game
     * @param card
     */
    public void confirmPlayedCard(Card card) {
        hand.removeCard(card);  // Only remove after successful validation
    }

    /**
     * Cummulative score of all tricks that player won
     * @return
     */
    public int countTricks() {
        for (Trick trick : wonTricks) {
            this.score += trick.getValue();
        }
        return this.score;
    }
}

package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;

import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    // The BotPlayer should play randomly based on the rules
    // That means he will choose its actions randomly but only from the set of actions he can do in correspondence with rules of game

    public BotPlayer(String name) {
        super(name);
    }

    public void getBet() {
        ;
    }
    @Override
    public Card playCard() {
        Random rand = new Random();
        List<Card> cards = hand.getCards();
        int idx = rand.nextInt(cards.size());
        Card chosen = cards.get(idx);
        hand.removeCard(chosen);
        return chosen;
    }

    public Card chooseTrump() {
        return hand.getRandomCard();
    }

}
package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    // The BotPlayer should play randomly based on the rules
    // That means he will choose its actions randomly but only from the set of actions he can do in correspondence with rules of game
    IOHandler ioHandler;
    public BotPlayer(String name, IOHandler ioHandler) {
        super(name);
        this.ioHandler = ioHandler;
    }

    public void getBet() {
        // here should be only the raising of the whole game not betting;
    }
    @Override
    public Card playCard() {
        Random rand = new Random();
        List<Card> cards = hand.getCards();
        if (cards.isEmpty()) {
            throw new IllegalStateException(name + " has no cards to play.");
        }
        int idx = rand.nextInt(cards.size());
        return cards.get(idx);
    }

    public Card chooseTrump() {
        ioHandler.printInfo(name + " is choosing the trump...");
        /*
        List<Card> crds = hand.getCards();
        System.out.println("Cards from which bot choose trump");
        for (Card crd : crds) {
            System.out.print(crd + " ");
        }
        System.out.println();
         */
        Card trump = hand.getRandomCard();
        //System.out.println("chosen trump : " + trump.toString());
        ioHandler.printInfo(name + " chose " + Card.SUIT_SYMBOLS[trump.getSuit()] + " as trump.");
        return trump;
    }
}
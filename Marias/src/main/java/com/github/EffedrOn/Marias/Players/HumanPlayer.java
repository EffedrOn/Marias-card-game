package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

public class HumanPlayer extends Player {
    // HumanPlayer should communicate through the CLI in which he will enter the actions he wants to play when he is on move
    //
    private final IOHandler ioHandler;

    public HumanPlayer(String name, IOHandler ioHandler) {
        super(name);
        this.ioHandler = ioHandler;
    }

    public void getBet() {
        ioHandler.printMessage("Enter your bet:");
        // read input
    }

    @Override
    public Card playCard() {
        ioHandler.printMessage("Your hand:");
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            ioHandler.printMessage(i + ": " + c.toString());
        }

        ioHandler.printMessage("Choose card index to play:");
        int idx = Integer.parseInt(ioHandler.readInput());
        Card chosen = hand.getCards().get(idx);
        hand.removeCard(chosen);
        return chosen;
    }

    @Override
    public Card chooseTrump() {
        ioHandler.printMessage("Choose trump:");
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            ioHandler.printMessage(i + ": " + c.toString());
        }
        ioHandler.printMessage("Choose card index to pick the trump:");
        int idx = Integer.parseInt(ioHandler.readInput());
        Card chosen = hand.getCards().get(idx);
        return chosen;
    }
}
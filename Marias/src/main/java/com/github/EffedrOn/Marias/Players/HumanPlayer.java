package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

public class HumanPlayer extends Player {
    // HumanPlayer should communicate through the CLI in which he will enter the actions he wants to play when he is on move

    private final IOHandler ioHandler;

    public HumanPlayer(String name, IOHandler ioHandler) {
        super(name);
        this.ioHandler = ioHandler;
    }

    public void getBet() {
        ioHandler.printPrompt("Enter your bet");
        // read input
    }

    @Override
    public Card playCard() {
        ioHandler.printMessage("Your hand:");
        ioHandler.printHand(hand);

        int idx = ioHandler.readInt("Choose card index to play");
        // Treba este ocheckovat ci idx je naozaj platny index (v rozmedzi 0,hand.length)
        Card chosen = hand.getCards().get(idx);
        //hand.removeCard(chosen);
        return chosen;
    }

    @Override
    public Card chooseTrump() {
        ioHandler.printPrompt("Choose trump");
        ioHandler.printHand(hand);
        int idx = ioHandler.readInt("Choose card index to pick the trump");
        // Treba tu este ocheckovat ci je idx platny ako aj v playCard
        Card chosen = hand.getCards().get(idx);
        return chosen;
    }
}
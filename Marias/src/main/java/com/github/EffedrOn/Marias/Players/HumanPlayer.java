package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

/**
 * Represents a human player in the Marias card game.
 * <p>
 * This class allows interaction via a command-line interface (CLI) through the {@link IOHandler}.
 * A human player is responsible for choosing the trump, discarding cards, and playing cards during their turn.
 * </p>
 *
 * <p>
 * This player is the first player to start the game.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class HumanPlayer extends Player {
    /** CLI handler for input and output operations. */
    private final IOHandler ioHandler;

    /**
     * Constructs a new human player with a name and I/O handler for CLI interaction.
     *
     * @param name      the name of the human player
     * @param ioHandler the handler used for console input/output
     */
    public HumanPlayer(String name, IOHandler ioHandler) {
        super(name);
        this.ioHandler = ioHandler;
    }

    /**
     * Prompts the human player to choose a card to play.
     * Displays the hand and reads the chosen card index.
     *
     * @return the card the player chose to play
     */
    @Override
    public Card playCard() {
        ioHandler.printMessage("Your hand:");
        ioHandler.printHand(hand);

        int idx = ioHandler.readCardIndex("Choose card index to play", hand.getCards().size());
        Card chosen = hand.getCards().get(idx);
        return chosen;
    }

    /**
     * Prompts the human player to choose a trump card.
     * Displays the hand and reads the selected card index.
     *
     * @return the card chosen as the trump
     */
    @Override
    public Card chooseTrump() {
        ioHandler.printPrompt("Choose trump");
        ioHandler.printHand(hand);

        int idx = ioHandler.readCardIndex("Choose card index to pick the trump", hand.getCards().size());
        return hand.getCards().get(idx);
    }
}
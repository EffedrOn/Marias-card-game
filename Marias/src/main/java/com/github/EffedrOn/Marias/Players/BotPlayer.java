package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

import java.util.List;
import java.util.Random;

/**
 * Represents a bot (automatic) player in the Marias card game.
 * <p>
 * The bot makes decisions randomly, but within the constraints of valid game rules.
 * It selects cards to play and chooses trump using random logic. This class simulates
 * automated gameplay for non-human participants.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class BotPlayer extends Player {
    /** IO handler used for logging bot actions. */
    IOHandler ioHandler;

    /**
     * Constructs a new bot player with the given name and IO handler.
     *
     * @param name the name of the bot player
     * @param ioHandler the IO handler used to print bot actions
     */
    public BotPlayer(String name, IOHandler ioHandler) {
        super(name);
        this.ioHandler = ioHandler;
    }

    /**
     * Randomly selects a card from the bot's hand to play.
     *
     * @return the card selected to be played
     * @throws IllegalStateException if the bot has no cards left to play
     */
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

    /**
     * Randomly selects a trump card from the bot's hand.
     * Prints the selected trump suit using the IO handler.
     *
     * @return the selected trump card
     */
    public Card chooseTrump() {
        ioHandler.printInfo(name + " is choosing the trump...");

        Card trump = hand.getRandomCard();
        ioHandler.printInfo(name + " chose " + Card.SUIT_SYMBOLS[trump.getSuit()] + " as trump.");

        return trump;
    }
}
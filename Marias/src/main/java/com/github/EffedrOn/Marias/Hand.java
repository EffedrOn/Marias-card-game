package com.github.EffedrOn.Marias;

import java.util.Random;
import com.github.EffedrOn.Marias.DeckOfCards.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's hand in the Marias card game.
 * <p>
 * The hand manages a collection of cards held by the player,
 * and provides operations to add, remove, retrieve, and randomly select cards.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Hand implements HandInterface{
    /** List of cards currently in the player's hand. */
    private ArrayList<Card> cards;

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Adds an array of cards to the hand.
     *
     * @param cards the cards to add
     */
    @Override
    public void addCards(Card[] cards){
        for(Card card : cards){
            this.cards.add(card);
        }
    }

    /**
     * Removes a specific card from the hand.
     *
     * @param card the card to remove
     */
    @Override
    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    /**
     * Returns the list of cards currently in the hand.
     *
     * @return list of cards in the hand
     */
    @Override
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Returns a random card from the hand.
     *
     * @return a randomly selected card, or {@code null} if the hand is empty
     */
    @Override
    public Card getRandomCard() {
        if (cards.isEmpty()) {
            return null; // or maybe throwing exception would be appropriate
        }

        Random random = new Random();
        int index = random.nextInt(cards.size());
        return cards.get(index);
    }
}

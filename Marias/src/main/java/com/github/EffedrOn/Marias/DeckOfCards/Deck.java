package com.github.EffedrOn.Marias.DeckOfCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of playing cards for the Marias card game.
 * <p>
 * This deck supports operations such as initializing a new full deck of cards,
 * shuffling, dealing cards to players, and resetting the deck.
 * The deck uses {@link Card} instances and maintains a standard set of cards
 * as defined by {@code Card.SUIT_SYMBOLS} and {@code Card.RANK_SYMBOLS}.
 * </p>
 *
 * <p>Note: In Marias, cards are traditionally not shuffled, but for randomness
 * in gameplay, shuffling will be applied in my case.</p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Deck implements DeckInterface {
    /** List of cards representing the deck. */
    private final List<Card> cards;

    /**
     * Constructs a new deck and initializes it with all possible cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        init();
    }

    /**
     * Initializes the deck with a fresh full set of cards.
     * Clears any existing cards and fills the deck with all
     * combinations of rank and suit defined by the Card class.
     */
    private void init() {
        cards.clear();
        for (int suit = 0; suit < Card.SUIT_SYMBOLS.length; suit++) {
            for (int rank = 0; rank < Card.RANK_SYMBOLS.length; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Resets the deck to its initial full state by reinitializing the cards.
     */
    @Override
    public void reset() {
        init();
    }

    /**
     * Deals a specified number of cards from the top of the deck.
     * <p>
     * Cards are removed from the beginning of the internal list,
     * simulating dealing from the top of a shuffled or ordered deck.
     * Deals a 3 packs of cards (one for every player)
     * </p>
     *
     * @param NumOfCards the number of cards to deal
     * @return an array containing the dealt cards
     * @throws IndexOutOfBoundsException if there are not enough cards in the deck
     */
    @Override
    public Card[] deal(int NumOfCards) {
        Card[] hand = new Card[NumOfCards];
        for (int i = 0; i < NumOfCards; i++) {
            hand[i] = cards.removeFirst();
        }
        return hand;
    }

    /**
     * Shuffles the deck using a random order.
     * <p>
     * Although traditional Marias may not require shuffling,
     * this ensures fairness and randomness.
     * </p>
     */
    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }
}

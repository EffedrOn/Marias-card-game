package com.github.EffedrOn.Marias.DeckOfCards;
import java.util.Comparator;

/**
 * Comparator for comparing {@link Card} objects based on Marias game rules.
 * <p>
 * The comparison prioritizes the trump suit over others,
 * and among cards of the same suit (or neither being trump),
 * the card with the higher rank is considered stronger.
 * </p>
 *
 * <p>
 * This comparator is essential for evaluating which card wins a trick.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class CardComparator implements Comparator<Card> {
    /** The suit considered trump in the current game context. */
    private final int trumpSuit;

    /**
     * Constructs a CardComparator with the specified trump suit.
     *
     * @param trumpSuit the suit to treat as trump when comparing cards
     */
    public CardComparator(int trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    /**
     * Compares two cards based on the Marias rules.
     * Trump cards are always higher than non-trump cards.
     * Among cards of the same suit, higher rank wins.
     *
     * @param o1 the first card to compare
     * @param o2 the second card to compare
     * @return -1 if o1 is stronger, 1 if o2 is stronger, 0 if equal
     */
    @Override
    public int compare(Card o1, Card o2) {
        boolean isTriumph1 = o1.getSuit() == trumpSuit;
        boolean isTriumph2 = o2.getSuit() == trumpSuit;

        if (isTriumph1 && !isTriumph2) return -1; // triumph wins
        if (!isTriumph1 && isTriumph2) return 1;

        // Same suit (or neither trump), compare rank
        return Integer.compare(o2.getRank(), o1.getRank()); // higher rank wins
    }
}

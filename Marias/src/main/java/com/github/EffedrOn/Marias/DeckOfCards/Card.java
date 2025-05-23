package com.github.EffedrOn.Marias.DeckOfCards;

/**
 * Represents a card in the Marias card game using Bohemian suits and ranks.
 * <p>
 * Cards have a rank and a suit, both represented by integer values.
 * Suits are mapped to symbols (e.g., leaves, acorns, bells, hearts), and ranks
 * range from 7 up to Ace, including unique figures like Dolnik and Hornik.
 * </p>
 *
 * <p>
 * Ranks and suits are validated on creation, and string representation is provided
 * via {@code toString()} using symbolic arrays.
 * </p>
 *
 * <p>
 * Examples:
 * - Rank 7 of hearts -> "7❤️"
 * - Ace of bells -> "A⚫"
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Card {
    /** Constants for card ranks */
    public static final int ACE = 7;
    public static final int TEN = 6;
    public static final int KING = 5;
    public static final int HORNIK = 4;
    public static final int DOLNIK = 3;
    public static final int NINE = 2;
    public static final int EIGHT = 1;
    public static final int SEVEN = 0;

    /** Constants for card suits */
    public static final int hearts = 3;
    public static final int bells = 2;
    public static final int acorns = 1;
    public static final int leaves = 0;

    /** Symbols used for rendering card ranks */
    public static final String[] RANK_SYMBOLS = { "7", "8", "9", "D", "H", "K", "10", "A" };
    //public static final char[] SUIT_SYMBOLS = {'♠', '♣', '♦', '♥' }; // Optionally these symbols could be used
    /** Symbols used for rendering card suits */
    public static final String[] SUIT_SYMBOLS = {"🍃","🌰", "⚫", "❤️"  };
                                            // list, zalud, gula, cerven
    //    public static final String[] SUIT_SYMBOLS = {"\uD83C\uDF43", "\uD83C\uDF30", "\u26AB", "\u2764\uFE0F"};

    /** The rank of the card */
    private final int rank;
    /** The suit of the card */
    private final int  suit;

    /**
     * Constructs a card with a specified rank and suit.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @throws IllegalArgumentException if the rank or suit is invalid
     */
    public Card(int rank, int suit) {
        if (rank < 0 || rank > RANK_SYMBOLS.length - 1) {
            throw new IllegalArgumentException("Invalid rank: " + rank);
        }
        if (suit < 0 || suit > SUIT_SYMBOLS.length - 1) {
            throw new IllegalArgumentException("Invalid suit: " + suit);
        }
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the card's rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the card's suit
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Returns the string representation of the card using rank and suit symbols.
     *
     * @return a string like "A❤️" or "9🍃"
     */
    @Override
    public String toString() {
        return RANK_SYMBOLS[rank] + SUIT_SYMBOLS[suit];
    }
}

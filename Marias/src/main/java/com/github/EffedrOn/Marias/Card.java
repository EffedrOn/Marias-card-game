package com.github.EffedrOn.Marias;

public class Card {
    // German cards

    // The ranks
    public static final int ACE = 7;
    public static final int HORNIK = 6;
    public static final int DOLNIK = 5;
    public static final int TEN = 4;
    public static final int NINE = 3;
    public static final int EIGHT = 2;
    public static final int SEVEN = 1;
    public static final int SIX = 0;

    public static final String[] RANK_SYMBOLS = {"6", "7", "8", "9", "10", "D", "H", "A" };

    // The suits
    public static final int hearts = 3;
    public static final int bells = 2;
    public static final int acorns = 1;
    public static final int leaves = 0;

    public static final char[] SUIT_SYMBOLS = {'h', 'b', 'a', 'l' };

    private final int rank;

    private final int  suit;


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
}

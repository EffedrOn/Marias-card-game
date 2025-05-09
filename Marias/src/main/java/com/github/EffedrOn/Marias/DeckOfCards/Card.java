package com.github.EffedrOn.Marias.DeckOfCards;

public class Card {
    // German/Bohemian cards
    // Card values depend on type of game that is played
    // This is the datastructure which would be appropriate for holding the cards: cards = [[7,7,7], ["X",15,10]]
    // That means the [[Symbol of card, rank of card, Value of card in game ]]

    // 2 typy hodnot jedna pri farbe druha pri malom velkom
    // Tu treba tiez nejak dodat ze hlaska ma nejaku hodnotu
    // The ranks should indicate how strong the card really is  but it doesnt depend only on this but also on type of game.
    public static final int ACE = 7;
    public static final int TEN = 6;
    public static final int KING = 5;
    public static final int HORNIK = 4;
    public static final int DOLNIK = 3;
    public static final int NINE = 2;
    public static final int EIGHT = 1;
    public static final int SEVEN = 0;

    // The suits
    public static final int hearts = 3;
    public static final int bells = 2;
    public static final int acorns = 1;
    public static final int leaves = 0;

    public static final String[] RANK_SYMBOLS = { "7", "8", "9", "D", "H", "K", "10", "A" };
    //public static final char[] SUIT_SYMBOLS = {'♠', '♣', '♦', '♥' };
    public static final String[] SUIT_SYMBOLS = {"🍃","🌰", "⚫", "❤️"  };
                                            // list, zalud, gula, cerven
    // Tu by som mohol vytvorit miesto emoji a stringu oznacujuceho typ karty cele ascii art kariet

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
    public int getRank() {
        return rank;
    }
    public int getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return RANK_SYMBOLS[rank] + SUIT_SYMBOLS[suit];
    }

}

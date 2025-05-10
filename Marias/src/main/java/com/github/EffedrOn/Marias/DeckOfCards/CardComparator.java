package com.github.EffedrOn.Marias.DeckOfCards;
import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
    private final int trumpSuit;

    public CardComparator(int trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    @Override
    public int compare(Card o1, Card o2) {
        boolean isTriumph1 = o1.getSuit() == trumpSuit;
        boolean isTriumph2 = o2.getSuit() == trumpSuit;

        if (isTriumph1 && !isTriumph2) return -1; // triumph wins
        if (!isTriumph1 && isTriumph2) return 1;

        // pridat ze ak niesu rovnakej farby vzdy vyhrava farba ktora bola vylozena prvym hracom na tahu
        // Same suit (or neither trump), compare rank
        return Integer.compare(o2.getRank(), o1.getRank()); // higher rank wins
    }
}

package com.github.EffedrOn.Marias.DeckOfCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck of cards, which can be shuffled, from which cards are dealed.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Deck implements DeckInterface {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        init();
        /*
        // printout of cards in deck
        for (Card card : cards) {
            System.out.println(card);
        }
        */
    }

    /**
     * Refill the deck with a fresh set of cards.
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
     * Refill and reshuffle the deck.
     */
    public void reset() {
        init();
    }

    @Override
    public Card[] deal(int NumOfCards) {
        // Deal a 3 packs of cards (one for every player), the first player (one that is choosing the trump) gets 12 cards, but only 7 of them are visible and 5
        // of them are hidden from him. Next player gets 10 cards as well as the next player.
        Card[] hand = new Card[NumOfCards];
        for (int i = 0; i < NumOfCards; i++) {
            hand[i] = cards.removeFirst();
        }
        return hand;
    }


    @Override
    public void shuffle() {
        // shuffle the deck of cards randomly.
        // v mariasi sa ale naozaj nemiesaju karty nazaciatku vsak musia byt randomne
        Collections.shuffle(cards);
    }

    @Override
    public void takeOutCard() {
        if (!cards.isEmpty()) {
            cards.removeFirst();
        }
    }

}

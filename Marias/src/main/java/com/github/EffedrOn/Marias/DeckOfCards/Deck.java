package com.github.EffedrOn.Marias.DeckOfCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements DeckInterface {
    //private Card[] cards = new Card[32]; mozno bude lepsie spravit pole uvidime
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (int suit = 0; suit < Card.SUIT_SYMBOLS.length; suit++) {
            for (int rank = 0; rank < Card.RANK_SYMBOLS.length; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
        /*
        // printout of cards in deck
        for (Card card : cards) {
            System.out.println(card);
        }
        */
    }

    @Override
    public Card[] deal() {
        // Deal a 3 packs of cards (one for every player), the first player (one that is choosing the trump) gets 12 cards, but only 7 of them are visible and 5
        // of them are hidden from him. Next player gets 10 cards as well as the next player.
        Card[] hand = new Card[5];
        for (int i = 0; i < 5; i++) {
            hand[i] = cards.removeFirst();
        }
        return hand;
    }

    @Override
    public void shuffle() {
        // shuffle the deck of cards randomly.
        // v mariasi sa ale naozaj nemiesaju karty nazaciatku vsak musia byt randomne
        //Collection.shuffle(cards); toto nefunguje
        Collections.shuffle(cards);
    }

    @Override
    public void takeOutCard() {
        if (!cards.isEmpty()) {
            cards.removeFirst();
        }
    }

}

package com.github.EffedrOn.Marias.DeckOfCards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void testResetCreatesFullDeck() {
        deck.reset();
        Card[] dealt = deck.deal(32);  // assuming 32-card deck for Marias
        assertEquals(32, dealt.length);
    }

    @Test
    void testDealReducesDeckSize() {
        Card[] hand = deck.deal(5);
        assertEquals(5, hand.length);

        // Deal rest and ensure no cards left
        Card[] rest = deck.deal(27);
        assertEquals(27, rest.length);

        assertThrows(NoSuchElementException.class, () -> deck.deal(1));
    }

    @Test
    void testShuffleChangesCardOrder() {
        deck.reset();
        List<Card> beforeShuffle = Arrays.asList(deck.deal(32));
        deck.reset();
        deck.shuffle();
        List<Card> afterShuffle = List.of(deck.deal(32));

        // It's possible the shuffle results in same order by chance, so loosen test
        assertFalse(Arrays.equals(beforeShuffle.toArray(), afterShuffle.toArray()),
                    "Shuffled deck should differ from original order");
    }
}

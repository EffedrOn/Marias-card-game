package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrickTest {

    private Trick trick;
    private Card trump;

    @BeforeEach
    void setUp() {
        trick = new Trick();
        trump = new Card(Card.DOLNIK, Card.hearts); // example trump
    }

    @Test
    void testAddCard() {
        Card card = new Card(Card.ACE, Card.bells);
        trick.addCard(card, 0);
        assertEquals(card, trick.getFirstCard());
        assertEquals(Card.bells, trick.getFirstCardSuit());
    }

    @Test
    void testGetValue() {
        trick.addCard(new Card(Card.ACE, Card.hearts), 0);  // 10 points
        trick.addCard(new Card(Card.TEN, Card.hearts), 1);  // 10 points
        trick.addCard(new Card(Card.KING, Card.hearts), 2); // 0 points
        trick.setBonusPoints(5);
        assertEquals(25, trick.getValue());
    }

    @Test
    void testGetWinnerPlayerIndex() {
        trick.addCard(new Card(Card.NINE, Card.acorns), 0);  // Lead suit
        trick.addCard(new Card(Card.KING, Card.hearts), 1); // Trump
        trick.addCard(new Card(Card.HORNIK, Card.acorns), 2);
        int winner = trick.getWinnerPlayerIndex(trump);
        assertEquals(1, winner); // trump should win
    }

    @Test
    void testGetHighestCard() {
        trick.addCard(new Card(Card.TEN, Card.bells), 0);
        trick.addCard(new Card(Card.ACE, Card.bells), 1);
        trick.addCard(new Card(Card.KING, Card.hearts), 2);
        Card highest = trick.getHighestCard(trump);
        assertEquals(highest.getRank(), Card.KING);
    }

    @Test
    void testGetHighestCardOfSuit() {
        trick.addCard(new Card(Card.TEN, Card.leaves), 0);
        trick.addCard(new Card(Card.HORNIK, Card.leaves), 1);
        trick.addCard(new Card(Card.NINE, Card.bells), 2);
        Card highest = trick.getHighestCardOfSuit(Card.leaves, trump);
        assertEquals(highest.getRank(), Card.TEN);
    }
}
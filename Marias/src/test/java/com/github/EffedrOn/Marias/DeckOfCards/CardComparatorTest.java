package com.github.EffedrOn.Marias.DeckOfCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardComparatorTest {

    @Test
    void testTrumpBeatsNonTrump() {
        Card trumpCard = new Card(Card.KING, Card.hearts);
        Card nonTrumpCard = new Card(Card.ACE, Card.leaves);
        CardComparator comparator = new CardComparator(Card.hearts);

        assertTrue(comparator.compare(trumpCard, nonTrumpCard) < 0);
        assertTrue(comparator.compare(nonTrumpCard, trumpCard) > 0);
    }

    @Test
    void testSameSuitHigherRankWins() {
        Card ace = new Card(Card.ACE, Card.hearts);
        Card king = new Card(Card.KING, Card.hearts);
        Card ten = new Card(Card.TEN, Card.hearts);
        CardComparator comparator = new CardComparator(Card.bells);

        assertTrue(comparator.compare(king, ace) > 0);
        assertTrue(comparator.compare(ten, ace) > 0);
    }

    @Test
    void testEqualCards() {
        Card c1 = new Card(Card.DOLNIK, Card.acorns);
        Card c2 = new Card(Card.DOLNIK, Card.acorns);
        CardComparator comparator = new CardComparator(Card.bells);

        assertEquals(0, comparator.compare(c1, c2));
    }
}
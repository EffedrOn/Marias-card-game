package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    private Hand hand;
    private Card card1;
    private Card card2;

    @BeforeEach
    void setUp() {
        hand = new Hand();
        card1 = new Card(Card.ACE, Card.hearts);
        card2 = new Card(Card.KING, Card.bells);
    }

    @Test
    void testAddCards() {
        Card[] cards = {card1, card2};
        hand.addCards(cards);
        List<Card> handCards = hand.getCards();

        assertEquals(2, handCards.size());
        assertTrue(handCards.contains(card1));
        assertTrue(handCards.contains(card2));
    }

    @Test
    void testRemoveCard() {
        hand.addCards(new Card[]{card1, card2});
        hand.removeCard(card1);
        List<Card> handCards = hand.getCards();

        assertEquals(1, handCards.size());
        assertFalse(handCards.contains(card1));
        assertTrue(handCards.contains(card2));
    }

    @Test
    void testGetCardsReturnsCorrectList() {
        hand.addCards(new Card[]{card1});
        List<Card> handCards = hand.getCards();

        assertEquals(1, handCards.size());
        assertEquals(card1, handCards.get(0));
    }

    @Test
    void testGetRandomCardReturnsCardInHand() {
        hand.addCards(new Card[]{card1, card2});
        Card randomCard = hand.getRandomCard();

        assertNotNull(randomCard);
        assertTrue(randomCard.equals(card1) || randomCard.equals(card2));
    }

    @Test
    void testGetRandomCardReturnsNullWhenEmpty() {
        assertNull(hand.getRandomCard());
    }
}
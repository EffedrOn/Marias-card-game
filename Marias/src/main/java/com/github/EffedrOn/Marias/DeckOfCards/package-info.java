/**
 * Provides classes and interfaces related to card and deck management for the Marias card game.
 *
 * <p>This package defines the structure and functionality of a traditional Bohemian/German deck,
 * including individual card representation, deck operations, and card comparison logic.</p>
 *
 * <ul>
 *   <li>{@link com.github.EffedrOn.Marias.DeckOfCards.Card} – Represents a playing card, including suit, rank, and value in the game.</li>
 *   <li>{@link com.github.EffedrOn.Marias.DeckOfCards.CardComparator} – Contains logic to compare two cards for determining trick outcomes.</li>
 *   <li>{@link com.github.EffedrOn.Marias.DeckOfCards.DeckInterface} – Interface defining basic operations such as shuffling and dealing.</li>
 *   <li>{@link com.github.EffedrOn.Marias.DeckOfCards.Deck} – Concrete deck implementation that generates, manages, and deals cards.</li>
 * </ul>
 *
 * <p>This package is used across the entire game logic to ensure consistent card behavior and enforce
 * the specific rules of the Marias card game.</p>
 *
 * @author Simon
 * @version 1.0
 * @since 2025-03-29
 */
package com.github.EffedrOn.Marias.DeckOfCards;
/**
 * Contains the core game orchestration logic for the Marias card game.
 *
 * <p>This package defines the central gameplay structure including table mechanics,
 * turn handling, round progression, and the overall game controller. It separates
 * high-level orchestration from lower-level components such as players and card handling.</p>
 *
 * <ul>
 *   <li>{@link com.github.EffedrOn.Marias.Game.Game} – Represents the game runner that initializes and launches the game.</li>
 *   <li>{@link com.github.EffedrOn.Marias.Game.GameController} – Manages the sequence of turns, rounds, and game phases.</li>
 *   <li>{@link com.github.EffedrOn.Marias.Game.TableInterface} – Declares the necessary operations for any table logic, allowing modular design.</li>
 *   <li>{@link com.github.EffedrOn.Marias.Game.Table} – Concrete implementation of the game table where cards are played and tricks are resolved.</li>
 * </ul>
 *
 * <p>This package plays a pivotal role in coordinating the interaction between players,
 * cards, and game rules to simulate the Marias gameplay experience.</p>
 *
 * @author Simon
 * @version 1.0
 * @since 2025-05-23
 */
package com.github.EffedrOn.Marias.Game;
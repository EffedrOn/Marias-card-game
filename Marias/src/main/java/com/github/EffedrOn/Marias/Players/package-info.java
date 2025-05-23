/**
 * Provides the core classes and interfaces for player behavior in the Marias card game.
 *
 * <p>This package contains definitions for different types of players that can participate
 * in the game. It abstracts common functionality and separates concerns between human
 * interaction and automated logic.</p>
 *
 * <ul>
 *   <li>{@link com.github.EffedrOn.Marias.Players.PlayerInterface} – The common interface for all player types,
 *       defining essential methods for gameplay.</li>
 *   <li>{@link com.github.EffedrOn.Marias.Players.Player} – The base class containing shared logic for all players,
 *       such as card handling and trick participation.</li>
 *   <li>{@link com.github.EffedrOn.Marias.Players.HumanPlayer} – A concrete implementation for user-controlled players
 *       interacting via the CLI.</li>
 *   <li>{@link com.github.EffedrOn.Marias.Players.BotPlayer} – A concrete implementation of a computer-controlled player
 *       with logic to simulate strategic gameplay.</li>
 * </ul>
 *
 * @author Simon
 * @version 1.0
 * @since 2025-05-23
 */
package com.github.EffedrOn.Marias.Players;
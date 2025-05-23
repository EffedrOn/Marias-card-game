/**
 * Provides classes and interfaces for handling input and output operations
 * within the command-line interface of the Marias card game.
 *
 * <p>This package encapsulates CLI-based user interaction, such as reading input
 * and displaying messages. The separation of input/output logic helps maintain
 * a clean architecture and improves testability of game components.</p>
 *
 * <ul>
 *   <li>{@link com.github.EffedrOn.Marias.InputOutputHandler.IOHandlerInterface} – Defines the contract for all input/output handlers,
 *       allowing multiple implementations (e.g., for CLI, GUI, or testing).</li>
 *   <li>{@link com.github.EffedrOn.Marias.InputOutputHandler.IOHandler} – The main implementation of {@code IOHandlerInterface} for
 *       reading user input from the console and printing game-related messages.</li>
 * </ul>
 *
 * @author Simon
 * @version 1.0
 * @since 2025-05-23
 */
package com.github.EffedrOn.Marias.InputOutputHandler;
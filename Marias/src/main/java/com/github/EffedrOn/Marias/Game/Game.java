package com.github.EffedrOn.Marias.Game;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.Player;

/**
 * Represents the entry point and wrapper for the Marias card game.
 * <p>
 * This class initializes the players, the table, and the game controller.
 * It acts as a central point to start the game and could support
 * multiple game modes or sessions in the future.
 * </p>
 *
 * <p>
 * Players are passed into this class externally, and the table is created here
 * to ensure that the core gameplay structure is in place.
 * </p>
 *
 * @author Simon
 * @version 1.0
 * @since 2025-03-29
 */
public class Game {
    /** Controller that manages the flow and logic of the game. */
    private final GameController gameController;
    /** Handler for input and output operations. */
    private final IOHandler ioHandler;
    /** The first player (typically human). */
    private final Player player1;
    /** The next players (typically bots). */
    private final Player player2, player3;
    /** The table where gameplay takes place. */
    private final Table table;

    /**
     * Constructs a new Game instance, initializing the IO handler, players, table, and controller.
     * I think it is a good idea to have instance of gameController in Game class, because I could create other games for example
     *
     * @param ioHandler the handler used for console input/output
     * @param player1   the first player
     * @param player2   the second player
     * @param player3   the third player
     */
    public Game(IOHandler ioHandler, Player player1, Player player2, Player player3) {
        this.ioHandler = ioHandler;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;

        ioHandler.printInfo("Human player entered the game");
        ioHandler.printInfo("Bot1 player entered the game");
        ioHandler.printInfo("Bot2 player entered the game");

        this.table =  new Table(ioHandler, player1, player2, player3);
        this.gameController = new GameController(player1, player2, player3, ioHandler, this.table);
    }

    /**
     * Starts the game using the game controller.
     */
    public void start() {
        this.gameController.startGame();
    }
}

package com.github.EffedrOn.Marias.Game;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.Player;

/**
 * This class represents whole game as an object.
 * Players lives outside of this class and they can join it. Table is initialized here, because game without table would not make sense.
 *
 */
public class Game {
    private final GameController gameController;
    private final IOHandler ioHandler;
    private final Player player1;
    private final Player player2;
    private final Player player3;
    private final Table table;

    public Game(IOHandler ioHandler, Player player1, Player player2, Player player3) {
        this.ioHandler = ioHandler;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;

        ioHandler.printInfo("Human player entered the game");
        ioHandler.printInfo("Bot1 player entered the game");
        ioHandler.printInfo("Bot2 player entered the game");

        this.table =  new Table(ioHandler, player1, player2, player3);
        // I think it is a good idea to have instance of gameController in Game class, because i could create other games for example
        this.gameController = new GameController(player1, player2, player3, ioHandler, this.table);
    }

    public void start() {
        this.gameController.startGame();
    }

}

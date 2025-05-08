package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

public class GameControler {
    // Class responsible for whole game logic, that decides who is on turn...
    // This class should handle all game logic like who is on turn, who wins and so on
    // The players should be dealed cards at the beggining.
    // Next players choose the type of game they will play.
    private final Table table;
    private final IOHandler ioHandler;
    public GameControler() {
        this.ioHandler = new IOHandler();
        this.table = new Table(ioHandler);
    }

    public void startGame() {


    }

}

package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

public class GameController {
    // Class responsible for whole game logic, that decides who is on turn...
    // This class should handle all game logic like who is on turn, who wins and so on
    // The players should be dealed cards at the beggining.
    // Next players choose the type of game they will play.
    private final Table table;
    private final IOHandler ioHandler;
    public GameController() {
        this.ioHandler = new IOHandler();
        this.table = new Table(ioHandler);
    }

    public void startGame() {
        table.shuffleCards();
        table.dealCards();
        table.chooseTrump();
        while (true) {
            table.playTrick(); // toto presunut asi do gamecontrolleru
            table.rotatePlayers();
            if (table.end()) {
                ioHandler.printMessage("End of game");
                break;// end the while loop
            }
        }
    }

}

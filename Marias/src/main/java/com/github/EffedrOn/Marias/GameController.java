package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

public class GameController {
    // Class responsible for whole game logic, that decides who is on turn...
    // This class should handle all game logic like who is on turn, who wins and so on
    // The players should be dealed cards at the beggining.
    // Next players choose the type of game they will play.
    private final Table table;
    private final IOHandler ioHandler;
    //  mozno by mala byt instancia comparatoru tu
    // 2 comparatory jeden pre farbu druhy pre maly a velky
    public GameController() {
        this.ioHandler = new IOHandler();
        this.table = new Table(ioHandler);
    }
    // Tu by mohla byt funkcia ktora by bola zodpovedna za to aky typ hry sa hra (Farba, Maly, Velky)
    public void startGame() {
        table.shuffleCards();
        table.dealCards();
        table.chooseTrump();

        while (true) {
            table.playTrick(); // logiku tohto presunut asi do gamecontrolleru

            if (table.end()) {
                ioHandler.printMessage("End of game");
                break;// end the while loop
            }
        }
    }

}

package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
// Mozno si drzat trump kartu radsej tu ako v Table
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
        table.dealCards();  // Z reality je to tak ze asi sa rozdava na 2x najprv dostanu hraci po 5 kariet a human 7, nasledne dostanu vsetci dalsich 5

        table.chooseTrump(); // Mozno este pridat ze hrac moze zavolit z ludu, t.j ze vyberie sa random karta z talonu 5 kariet rozdanych v dealFirstPlayer()
        table.dealFirstPlayer();

        // Ask First player to choose 2 cards to throw away
        table.throwAwayCards();

        while (true) {
            table.playTrick(); // logiku tohto presunut asi do gamecontrolleru

            if (table.end()) {
                ioHandler.printMessage("End of game");
                break;// end the while loop
            }
        }
    }

}

package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

// Mozno si drzat trump kartu radsej tu ako v Table
public class GameController {
    // Class responsible for whole game logic, that decides who is on turn...
    // This class should handle all game logic like who is on turn, who wins and so on
    // The players should be dealed cards at the beggining.
    // Next players choose the type of game they will play.
    private final Table table;
    private final IOHandler ioHandler;

    private final Player player1;
    private final Player player2;
    private final Player player3;

    //  mozno by mala byt instancia comparatoru tu
    // 2 comparatory jeden pre farbu druhy pre maly a velky

    public GameController() {
        this.ioHandler = new IOHandler();

        this.player1 = new HumanPlayer("Human", this.ioHandler);
        this.player2 = new BotPlayer("Bot1", this.ioHandler);
        this.player3 = new BotPlayer("Bot2", this.ioHandler);

        ioHandler.printInfo("Human player created");
        ioHandler.printInfo("Bot1 player created");
        ioHandler.printInfo("Bot2 player created");

        this.table = new Table(ioHandler, player1, player2, player3);

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
                // Tu treba rozhodnut kto vyhral hru

                int player1Score = this.player1.countTricks();
                int  player2Score = this.player2.countTricks();
                int player3Score= this.player3.countTricks();
                ioHandler.printMessage(player1.name + " score: " + player1Score);
                ioHandler.printMessage(player2.name + " score: " + player2Score);
                ioHandler.printMessage(player3.name + " score: " + player3Score);

                // Determine winner
                Player winner = player1;
                int maxScore = player1Score;

                if (player2Score > maxScore) {
                    winner = player2;
                    maxScore = player2Score;
                }
                if (player3Score > maxScore) {
                    winner = player3;
                    maxScore = player3Score;
                }

                ioHandler.printSeparator();
                ioHandler.printMessage("🏆 " + winner.name + " wins the game!");
                ioHandler.printMessage("Final score: " + maxScore);
                ioHandler.printSeparator();

                break;// end the while loop
            }
        }
    }
}

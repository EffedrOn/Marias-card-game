package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

/**
 * Class responsible for whole game logic, that decides who is on turn
 * This class handle main game logic.
 * Here players and table lives.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class GameController {
    private final Table table;
    private final IOHandler ioHandler;

    private final Player player1;
    private final Player player2;
    private final Player player3;

    // Grouping the players based on teams
    private Player soloPlayer;
    private Player[] teamPlayers;

    // mozno by mala byt instancia comparatoru tu

    /**
     * Constructor
     */
    public GameController() {
        this.ioHandler = new IOHandler();

        this.player1 = new HumanPlayer("Human", this.ioHandler);
        this.player2 = new BotPlayer("Bot1", this.ioHandler);
        this.player3 = new BotPlayer("Bot2", this.ioHandler);

        setupTeams(player1, player2, player3);

        ioHandler.printInfo("Human player created");
        ioHandler.printInfo("Bot1 player created");
        ioHandler.printInfo("Bot2 player created");

        this.table = new Table(ioHandler, player1, player2, player3);

    }

    private void setupTeams(Player player1, Player player2, Player player3) {
        this.soloPlayer = player1;
        this.teamPlayers = new Player[]{player2, player3};
    }

    // Tu by mohla byt funkcia ktora by bola zodpovedna za to aky typ hry sa hra (Farba, Maly, Velky)

    private void determineGameWinner() {
        int  soloPlayerScore = this.soloPlayer.countTricks();
        int  teamPlayer0Score = this.teamPlayers[0].countTricks();
        int  teamPlayer1Score= this.teamPlayers[1].countTricks();

        ioHandler.printMessage(soloPlayer.name + " score: " + soloPlayerScore);
        ioHandler.printMessage(teamPlayers[0].name + " score: " + teamPlayer0Score);
        ioHandler.printMessage(teamPlayers[1].name + " score: " + teamPlayer1Score);


        int teamScore = teamPlayer0Score + teamPlayer1Score;

        ioHandler.printMessage("Team players " + teamPlayers[0].name + " and " + teamPlayers[1].name + " scored: " + teamScore);

        if (soloPlayerScore > teamScore) {
            ioHandler.printMessage("You won!");
            ioHandler.printMessage("Solo player " + soloPlayer.name + " WINS!");
        } else {
            ioHandler.printMessage("Team won!");
            ioHandler.printMessage("You lost!");
        }
        ioHandler.printSeparator();
    }
    /**
     * Starting the game in which deck of cards is shuffled
     * Then cards are dealed
     * First player chooses trump of game, then he throw away 2 cards
     * Then the rounds of tricks are played until every player has empty hand (no card)
     * Then total winner of game is decided.
     */
    public void startGame() {
        // Players should choose what value will the game be
        table.shuffleCards();
        // Tu by este mal mat prvy hrac moznost "prelozit" balik
        table.dealCards();  // Z reality je to tak ze asi sa rozdava na 2x najprv dostanu hraci po 5 kariet a human 7, nasledne dostanu vsetci dalsich 5

        table.chooseTrump(); // Mozno este pridat ze hrac moze zavolit z ludu, t.j ze vyberie sa random karta z talonu 5 kariet rozdanych v dealFirstPlayer()
        table.dealFirstPlayer(); // Deal first player rest of the cards

        // Ask First player to choose 2 cards to throw away
        table.throwAwayCards();

        // skusit aby nebol vzdy human ten kto vybera trumfa ale ten kto je na rade (teda napr bot1 a human hra spolu s bot2)
        while (true) {
            table.playTrick(); // logiku tohto presunut asi do gamecontrolleru

            if (table.end()) {
                ioHandler.printMessage("End of game");

                // Determine winner
                determineGameWinner();

                break;  // end the while loop
            }
        }
    }
}

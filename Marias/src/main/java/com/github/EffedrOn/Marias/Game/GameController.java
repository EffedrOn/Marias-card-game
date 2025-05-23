package com.github.EffedrOn.Marias.Game;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
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

    private final Player[] allPlayers;
    // Grouping the players based on teams
    private Player soloPlayer;
    private Player[] teamPlayers;

    // Value that game has / what players pay when he loose
    private static final int PAYOFF_AMOUNT = 2;
    // Or i could just licitate inside the GameController class which will change the internal variable PAYOFF_AMOUNT.
    // mozno by mala byt instancia comparatoru tu
    private int startingPlayerIndex = 0;
    /**
     * Constructor
     */
    public GameController(Player player1, Player player2, Player player3, IOHandler ioHandler, Table table) {
        this.ioHandler = ioHandler;

        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;

        this.allPlayers = new Player[]{player1, player2, player3};

        setupTeams(startingPlayerIndex);

        this.table = table;
    }

    private void setupTeams(int startIndex) {
        soloPlayer = allPlayers[startIndex];
        teamPlayers = new Player[]{allPlayers[(startIndex + 1) % 3], allPlayers[(startIndex + 2) % 3]};
    }

    private void determineGameWinner() {
        int  soloPlayerScore = this.soloPlayer.countTricks() + this.soloPlayer.getMarriagePoints();
        int  teamPlayer0Score = this.teamPlayers[0].countTricks() + this.teamPlayers[0].getMarriagePoints();
        int  teamPlayer1Score= this.teamPlayers[1].countTricks() + this.teamPlayers[1].getMarriagePoints();
        int teamScore = teamPlayer0Score + teamPlayer1Score;

        ioHandler.printInfo(soloPlayer.name + " score: " + soloPlayerScore);
        ioHandler.printInfo(teamPlayers[0].name + " score: " + teamPlayer0Score);
        ioHandler.printInfo(teamPlayers[1].name + " score: " + teamPlayer1Score);

        ioHandler.printInfo("Team players " + teamPlayers[0].name + " and " + teamPlayers[1].name + " scored: " + teamScore);

        // Bank/payoff logic and letting user know who wins.
        if (soloPlayerScore > teamScore) {
            //ioHandler.printMessage("You won!"); // tu bude tiez treba checknut ci je human naozaj soloPlayer
            ioHandler.printMessage("-------------Solo player " + soloPlayer.name + " WINS!---------------");
            // Solo wins, team players pay
            for (Player teamPlayer : teamPlayers) {
                teamPlayer.adjustBank(-PAYOFF_AMOUNT);
                soloPlayer.adjustBank(PAYOFF_AMOUNT);
            }
        } else {
            ioHandler.printMessage("---------------------Team won!---------------------");
            //ioHandler.printMessage("You lost!"); // toto bude treba checknut ci je soloplayer naozaj human
            // Team wins, solo player pays each team player
            soloPlayer.adjustBank(-PAYOFF_AMOUNT * teamPlayers.length);
            for (Player teamPlayer : teamPlayers) {
                teamPlayer.adjustBank(PAYOFF_AMOUNT);
            }
        }

        // Show updated banks
        ioHandler.printInfo(soloPlayer.name + " bank: " + soloPlayer.getBank() + " cents");
        ioHandler.printInfo(teamPlayers[0].name + " bank: " + teamPlayers[0].getBank() + " cents");
        ioHandler.printInfo(teamPlayers[1].name + " bank: " + teamPlayers[1].getBank() + " cents");
        ioHandler.printSeparator();

        // Check if any player has gone bankrupt
        for (Player p : allPlayers) {
            if (p.getBank() <= 0) {
                ioHandler.printMessage("!!! " + p.name + " has gone bankrupt with " + p.getBank() + " cents !!!");
                if (p.getBank() < 0) {
                    ioHandler.printMessage(p.name + "is in DEBT with " + p.getBank() + " cents." + ". Should take a loan and pay it back in future!");
                }
                ioHandler.printGameOver();
                System.exit(0);
            }
        }
    }

    /**
     * Starting the game in which deck of cards is shuffled
     * Then cards are dealed
     * First player chooses trump of game, then he throw away 2 cards
     * Then the rounds of tricks are played until every player has empty hand (no card)
     * Then total winner of game is decided.
     */
    public void startGame() {
        while (true) {
            ioHandler.printSeparator();
            ioHandler.printMessage("Starting a new game round...");
            ioHandler.printSeparator();

            startRound();
            // skusit aby nebol vzdy human ten kto vybera trumfa ale ten kto je na rade (teda napr bot1 a human hra spolu s bot2)
            while (true) {
                table.playTrick(); // logiku tohto presunut asi do gamecontrolleru

                if (table.end()) {
                    ioHandler.printMessage("--------------------End of game--------------------");
                    // Determine winner
                    determineGameWinner();
                    break;  // end the while loop
                }
            }
            String response = ioHandler.readLine("Do you want to play another round? (y/n) ");
            if (!response.equalsIgnoreCase("y")) {
                ioHandler.printMessage("Thanks for playing!");
                break;
            }

            // Clear tricks for all players before next round
            player1.clearTricks();
            player2.clearTricks();
            player3.clearTricks();
            // Reset the points gained from marriages
            player1.resetMarriagePoints();
            player2.resetMarriagePoints();
            player3.resetMarriagePoints();

            startingPlayerIndex = (startingPlayerIndex + 1) % 3;
            setupTeams(startingPlayerIndex);
        }
    }

    private void startRound() {
        //table.setStartingPlayer(0); // 0 = Human player starts round
        table.setStartingPlayer(startingPlayerIndex);
        // Players should choose what value will the game be

        table.shuffleCards();
        // Tu by este mal mat prvy hrac moznost "prelozit" balik

        table.dealCards();  // Z reality je to tak ze asi sa rozdava na 2x najprv dostanu hraci po 5 kariet a human 7, nasledne dostanu vsetci dalsich 5

        table.chooseTrump(); // Mozno este pridat ze hrac moze zavolit z ludu, t.j ze vyberie sa random karta z talonu 5 kariet rozdanych v dealFirstPlayer()
        table.dealFirstPlayer(); // Deal first player rest of the cards
        table.throwAwayCards();// Ask First player to choose 2 cards to throw away
    }

}

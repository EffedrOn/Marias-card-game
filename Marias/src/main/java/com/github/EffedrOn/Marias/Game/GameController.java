package com.github.EffedrOn.Marias.Game;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.Player;

/**
 * Controls the overall game flow and logic for the Marias card game.
 * <p>
 * This class handles shuffling, dealing, managing turns, score calculation,
 * determining game winners, managing banks, and checking for bankrupt players.
 * It also rotates the starting player and prompts the user for another round.
 * </p>
 *
 * <p>
 * The main interaction with players and table is coordinated here,
 * including when to start and end rounds and how to handle payoffs.
 * </p>
 *
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
    private int startingPlayerIndex = 0;

    /**
     * Constructs a new GameController to manage gameplay.
     *
     * @param player1    the first player
     * @param player2    the second player
     * @param player3    the third player
     * @param ioHandler  the IO handler for CLI interactions
     * @param table      the game table
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

    /**
     * Sets up the solo player and team players based on who is starting.
     *
     * @param startIndex the index of the solo player
     */
    private void setupTeams(int startIndex) {
        soloPlayer = allPlayers[startIndex];
        teamPlayers = new Player[]{allPlayers[(startIndex + 1) % 3], allPlayers[(startIndex + 2) % 3]};
    }

    /**
     * Determines the winner of the game and distributes the payoff accordingly.
     * Updates player bank balances and checks for bankruptcy.
     */
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
            ioHandler.printMessage("-------------Solo player " + soloPlayer.name + " WINS!---------------");
            // Solo wins, team players pay
            for (Player teamPlayer : teamPlayers) {
                teamPlayer.adjustBank(-PAYOFF_AMOUNT);
                soloPlayer.adjustBank(PAYOFF_AMOUNT);
            }
        } else {
            ioHandler.printMessage("---------------------Team won!---------------------");
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
     * Starts the game loop, shuffling cards and playing rounds until termination.
     * Prompts users for continuing new rounds and resets state between rounds.
     */
    public void startGame() {
        while (true) {
            ioHandler.printSeparator();
            ioHandler.printMessage("Starting a new game round...");
            ioHandler.printSeparator();

            startRound();
            while (true) {
                table.playTrick();

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

    /**
     * Initializes and deals cards for a new round of the game.
     * Handles setting the starting player, choosing trump, and discarding cards.
     */
    private void startRound() {
        table.setStartingPlayer(startingPlayerIndex);
        table.shuffleCards();
        table.dealCards();
        table.chooseTrump();
        table.dealFirstPlayer(); // Deal first player rest of the cards
        table.throwAwayCards();  // Ask First player to choose 2 cards to throw away
    }
}

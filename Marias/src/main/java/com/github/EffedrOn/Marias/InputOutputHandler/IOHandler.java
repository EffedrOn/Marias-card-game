package com.github.EffedrOn.Marias.InputOutputHandler;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Players.Player;

import java.util.Scanner;

/**
 * Handles user input and output in the CLI version of the Marias card game.
 * This class manages printing prompts, game information, player actions, errors, and other game messages.
 * It also provides methods to read user input from the console.
 * <p>
 * The interface is constructed using this class, focusing on clarity and consistent formatting
 * for user experience during gameplay.
 * </p>
 *
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class IOHandler implements IOHandlerInterface {
    private final Scanner scanner;

    /**
     * Constructs a new IOHandler and initializes the input scanner.
     */
    public IOHandler() {
         this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user and reads a line of input.
     *
     * @param prompt the message to display before reading input
     * @return the input line from the user
     */
    public String readLine(String prompt) {
        printPrompt(prompt);
        return scanner.nextLine();
    }

    /**
     * Prints an error message to the console with a standard prefix.
     *
     * @param message the error message to display
     */
    public void printError(String message) {
        println("[ERROR] " + message);
    }

    /**
     * Prints a plain message to the console.
     *
     * @param message the message to print
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints an informational message with a prefix.
     *
     * @param message the info message to print
     */
    public void printInfo(String message) {
        println("[INFO] " + message);
    }

    /**
     * Prints a user prompt with a prefixed format.
     *
     * @param message the prompt message to print
     */
    public void printPrompt(String message) {
        println("> " + message + ": ");
    }

    /**
     * Prints a message indicating the card played by a player.
     *
     * @param player the player who played the card
     * @param card   the card that was played
     */
    public void printPlayedCard(Player player, Card card) {
        printMessage(player.name + " played: " + card);
    }

    /**
     * Prints a visual separator to distinguish sections in the CLI.
     */
    public void printSeparator() {
        println("---------------------------------------------------");
    }

    /**
     * Prints a game over message.
     */
    public void printGameOver() {
        printMessage("--------------------Game Over----------------------");

    }

    /**
     * Prints a single line message to the console.
     *
     * @param message the message to print
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints a message indicating that a player has played a marriage (KING and HORNIK of the same suit).
     *
     * @param player         the player who played the marriage
     * @param card           the card involved in the marriage
     * @param marriagePoints the points awarded for the marriage
     * @param isTrumpSuit    true if the marriage is in the trump suit
     */
    public void printMarriage(Player player, Card card, int marriagePoints, boolean isTrumpSuit) {
        String suitSymnbol = Card.SUIT_SYMBOLS[card.getSuit()];
        if(!isTrumpSuit) {
            printMessage(player.name + " plays a MARRIAGE in " + suitSymnbol + " suit" + " for " + marriagePoints + " points!");
        } else{
            printMessage(player.name + " plays a MARRIAGE " + " in trump suit " + suitSymnbol + " for " + marriagePoints + " points!");
        }
    }

    /**
     * Displays the cards in a player's hand, with indexes for user reference.
     *
     * @param hand the hand of cards to display
     */
    public void printHand(Hand hand) {
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            System.out.print(String.format("| %d : %3s ", i, c.toString()));
        }
        System.out.println("|");
    }

    /**
     * Reads a single line of input from the user.
     *
     * @return the line entered by the user
     */
    public String readInput() {
        return scanner.nextLine();
    }

    /**
     * Prompts the user to select a card index and validates the input.
     *
     * @param prompt   the message shown to prompt the user
     * @param maxIndex the maximum valid index (exclusive)
     * @return the validated card index entered by the user
     */
    public int readCardIndex(String prompt, int maxIndex) {
        while (true) {
            printPrompt(prompt);
            try {
                int index = Integer.parseInt(readInput());
                if (index >= 0 && index < maxIndex) {
                    return index;
                } else {
                    printError("Invalid index. Please select a number between 0 and " + (maxIndex - 1) + ".");
                }
            } catch (NumberFormatException e) {
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }
}

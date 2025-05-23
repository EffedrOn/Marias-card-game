package com.github.EffedrOn.Marias.InputOutputHandler;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Players.Player;

import java.util.List;
import java.util.Scanner;

/**
 * Class handling reading and writing with scanner.
 * User Interface and its visual design is mainly being constructed by this class.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class IOHandler implements IOHandlerInterface {
    // It would be good to always show the player what color is triumph because its easy to forget.
    private final Scanner scanner;

    public IOHandler() {
         this.scanner = new Scanner(System.in);
    }

    // Basic input method
    public String readLine(String prompt) {
        //print(prompt);
        printPrompt(prompt);
        return scanner.nextLine();
    }

    // Common message types
    public void printError(String message) {
        println("[ERROR] " + message);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printInfo(String message) {
        println("[INFO] " + message);
    }

    public void printPrompt(String message) {
        println("> " + message + ": ");
    }

    public void printPlayedCard(Player player, Card card) {
        printMessage(player.name + " played: " + card);
    }

    public void printSeparator() {
        println("---------------------------------------------------");
    }

    public void printGameOver() {
        printMessage("--------------------Game Over----------------------");

    }
    // General print helpers
    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printMarriage(Player player, Card card, int marriagePoints, boolean isTrumpSuit) {
        String suitSymnbol = Card.SUIT_SYMBOLS[card.getSuit()];
        if(!isTrumpSuit) {
            printMessage(player.name + " plays a MARRIAGE in " + suitSymnbol + " suit" + " for " + marriagePoints + " points!");
        } else{
            printMessage(player.name + " plays a MARRIAGE " + " in trump suit " + suitSymnbol + " for " + marriagePoints + " points!");
        }
    }

    public void printHand(Hand hand) {
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            System.out.print(String.format("| %d : %3s ", i, c.toString()));
        }
        System.out.println("|");

        /*
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            System.out.print("|  " + c.toString() + "  ");
        }
        System.out.print("|");
        System.out.println();

         */
    }

    public String readInput() {
        return scanner.nextLine();
    }

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

    // List printing utility
    public <T> void printList(List<T> items) {
        if (items == null || items.isEmpty()) {
            printInfo("No items to display.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                println((i + 1) + ". " + items.get(i).toString());
            }
        }
    }

    // Menu printing
    public void printMenu(String title, List<String> options) {
        println("==== " + title + " ====");
        for (int i = 0; i < options.size(); i++) {
            println((i + 1) + ". " + options.get(i));
        }
        printSeparator();
    }

}

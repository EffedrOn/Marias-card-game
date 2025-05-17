package com.github.EffedrOn.Marias.InputOutputHandler;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import com.github.EffedrOn.Marias.Players.Player;

import java.util.ArrayList;
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
        print(prompt);
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
        printMessage(player.name + "played: " + card);
    }

    public void printSeparator() {
        println("--------------------------------------------------");
    }

    // General print helpers
    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public String readInput() {
        return scanner.nextLine();
    }


    public void printHand(Hand hand) {
        List<Card> cards = hand.getCards();

        StringBuilder indexLine = new StringBuilder();
        StringBuilder cardLine = new StringBuilder();

        List<Integer> cardWidths = new ArrayList<>();

        List<String> formattedCards = new ArrayList<>();
        for (Card card : cards) {
            String cardStr = card.toString();
            String formatted = "| " + cardStr + " ";
            formattedCards.add(formatted);
            cardWidths.add(formatted.length());
        }

        int currentPos = 0;
        for (int i = 0; i < formattedCards.size(); i++) {
            String card = formattedCards.get(i);
            int width = cardWidths.get(i);
            cardLine.append(card);

            int indexPos = currentPos + width / 2 - String.valueOf(i).length() / 2;
            while (indexLine.length() < indexPos) {
                indexLine.append(" ");
            }
            indexLine.append(i);

            currentPos += width;
        }

        cardLine.append("|");

        System.out.println(indexLine.toString());
        System.out.println(cardLine.toString());
        /*
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            System.out.print("| " + c.toString());
        }

        System.out.print("|");
        System.out.println();

         */
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

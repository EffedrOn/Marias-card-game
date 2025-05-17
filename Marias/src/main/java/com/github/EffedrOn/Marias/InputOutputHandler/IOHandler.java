package com.github.EffedrOn.Marias.InputOutputHandler;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;
import java.util.List;
import java.util.Scanner;

// It would be good to always show the player what color is triumph because its easy to forget.
public class IOHandler implements IOHandlerInterface {
    // Should have methods that will be preimplented which mesagge it will printout
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

    public void printSuccess(String message) {
        println("[SUCCESS] " + message);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printInfo(String message) {
        println("[INFO] " + message);
    }

    public void printPrompt(String message) {
        print("> " + message + ": ");
    }

    public void printSeparator() {
        println("--------------------------------------------------");
    }

    public void printEmptyLine() {
        println("");
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
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card c = hand.getCards().get(i);
            System.out.print("| " + c.toString());
        }

        System.out.print(" |");
        System.out.println();
    }

    // Maybe it will be better to use this function instead
    public int readInt(String prompt) {
        while (true) {
            //printMessage(prompt);
            printPrompt(prompt);
            try {
                return Integer.parseInt(readInput());
            } catch (NumberFormatException e) {
                printMessage("Invalid input. Please enter a number.");
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

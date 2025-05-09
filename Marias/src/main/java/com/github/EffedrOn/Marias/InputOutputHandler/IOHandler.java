package com.github.EffedrOn.Marias.InputOutputHandler;

import com.github.EffedrOn.Marias.DeckOfCards.Card;
import com.github.EffedrOn.Marias.Hand;

import java.util.Scanner;
// It would be good to always show the player what color is triumph because its easy to forget.
public class IOHandler implements IOHandlerInterface {
    // Should have methods that will be preimplented which mesagge it will printout
    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
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
}

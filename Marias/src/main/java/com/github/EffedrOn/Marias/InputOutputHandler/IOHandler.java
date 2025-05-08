package com.github.EffedrOn.Marias.InputOutputHandler;

import java.util.Scanner;
// It would be good to always show the player what color is triumph because its easy to forget.
public class IOHandler implements IOHandlerInterface {
    // Should have methods that will be preimplemted which mesagge it will printout
    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String readInput() {
        return scanner.nextLine();
    }
}

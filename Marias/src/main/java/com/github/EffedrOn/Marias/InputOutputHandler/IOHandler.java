package com.github.EffedrOn.Marias.InputOutputHandler;

import java.util.Scanner;

public class IOHandler implements IOHandlerInterface {
    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String readInput() {
        return scanner.nextLine();
    }
}

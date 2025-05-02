package com.github.EffedrOn.Marias.Players;

import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;

public class HumanPlayer extends Player {
    private final IOHandler ioHandler;

    public HumanPlayer(String name, IOHandler ioHandler) {
        super(name);
        this.ioHandler = ioHandler;
    }

    public void getBet() {
        ioHandler.printMessage("Enter your bet:");
        // read input
    }

}
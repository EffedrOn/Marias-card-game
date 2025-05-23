package com.github.EffedrOn.Marias;

import com.github.EffedrOn.Marias.Game.Game;
import com.github.EffedrOn.Marias.InputOutputHandler.IOHandler;
import com.github.EffedrOn.Marias.Players.BotPlayer;
import com.github.EffedrOn.Marias.Players.HumanPlayer;
import com.github.EffedrOn.Marias.Players.Player;

public class Main {
    public static void main(String[] args) {
        IOHandler ioHandler = new IOHandler();
        Player player1 = new HumanPlayer("Human", ioHandler);
        Player player2 = new BotPlayer("Bot1", ioHandler);
        Player player3 = new BotPlayer("Bot2", ioHandler);

        ioHandler.printInfo("Human player created");
        ioHandler.printInfo("Bot1 player created");
        ioHandler.printInfo("Bot2 player created");

        Game game = new Game(ioHandler, player1, player2, player3);
        game.start();
    }
}

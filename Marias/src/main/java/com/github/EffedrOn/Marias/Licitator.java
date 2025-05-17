package com.github.EffedrOn.Marias;

/**
 * Class in which players should decide what value will the game they are going to play has.
 * @author Simon Fabus
 * @version 1.0
 * @since 2025-03-29
 */
public class Licitator {
    private int Bank;
    String typeOfGame;

    public void raise(int bet) {
        Bank += bet;
    }

    public void switchTypeGame(String game) {
        typeOfGame = game;
    }

    public void licitate() {
        ;
    }
}

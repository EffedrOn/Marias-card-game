package com.github.EffedrOn.Marias;

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

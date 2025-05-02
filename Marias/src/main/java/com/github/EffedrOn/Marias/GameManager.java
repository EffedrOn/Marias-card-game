package com.github.EffedrOn.Marias;

public class GameManager {
    // Class responsible for whole game logic, that decides who is on turn...
    private final Table table;

    public GameManager() {
        this.table = new Table();
    }

    public void startGame() {
        System.out.println("Welcome to Marias");
        table.run();
    }
}

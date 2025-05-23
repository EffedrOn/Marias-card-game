package com.github.EffedrOn.Marias.Game;

public interface TableInterface {
    void rotatePlayers();
    void shuffleCards();
    boolean end();
    void chooseTrump();
    void playTrick();
    void throwAwayCards();
    void setStartingPlayer(int index);
    void dealFirstPlayer();
    void dealCards();
}

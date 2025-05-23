package com.github.EffedrOn.Marias.DeckOfCards;

public interface DeckInterface {
    void shuffle();
    Card[] deal(int NumOfCards); // hrac by mal dostat karty na 2, krat jedny ktore bude vidiet a dalsich 5 ktore nevidi
    void reset();
}

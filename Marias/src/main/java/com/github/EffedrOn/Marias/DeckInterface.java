package com.github.EffedrOn.Marias;
public interface DeckInterface {
    void shuffle();
    Card[] deal(); // hrac by mal dostat karty na 2, krat jedny ktore bude vidiet a dalsich 5 ktore nevidi
    void takeOutCard();
}

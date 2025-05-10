package com.github.EffedrOn.Marias;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame();
    }
}

// Z  mainu vsetko vytvorit a tam to uz bezat nezavisle takze mozno hracov v maine a tych predat Game.

// mozno trieda game, na jednom stole moze byt odohranych viac hier/kol 3 hracmi.
// Tu by sa teda mala vyberat aka hra sa ide hrat a aj o kolko penazi.
// V kole/hre by mal byt ulozene co hram a ked sa hra farba aj tromfovu farbu.
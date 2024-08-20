package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {

    /**
     * Denne metoden skal finne ut hvor mange kolonner og rader
     * som skal tegnes på brettet
     * @return dimensjonene til brettet som skal tegnes
     */
    GridDimension getDimension();

    /**
     * Denne metoden skal være ganske lik den metoden som ligger i Grid-klassen
     * Den skal lage en liste med med GridCell objekter som når den itereres over
     * skal kunne hente ut posisjonen til cellene og verdiene til tilhørende celler
     * @return et Iterable<GridCell<Character>> objekt som kan itereres over
     */
    Iterable<GridCell<Character>> getTilesOnBoard();

    /**
     * Denne metoden skal virke på nøyaktig samme måte som metdoden
     * getTilesOnBoard ovenfor. Den skal lage en liste med GridCell objekter og når
     * den itererer over skal den kunne hente ut posisjonen til den fallende brikken.
     * @return skal være en en liste med GridCell objekter som kan itereres over.
     */
    Iterable<GridCell<Character>> getFallingTetro();

    /**
     * Denne metoden skal virke på nøyaktig samme måte som metoden ovenfor. Den skal lage en liste med
     * GridCell objekter når den iterer over skal den kunne hente ut posisjonen til skyggen
     * til den fallende brikken.
     * @return skal være en liste med GridCell objekter som kan itereres over.
     */
    Iterable<GridCell<Character>> getShadowTetro();

    /**
     * Denne metoden skal virke på nøyaktig samme måte som metoden ovenfor. Den skal lage en liste med
     * GridCell objekter når den iterer over skal den kunne hente ut posisjonen til den neste brikken.
     * @return skal være en liste med GridCell objekter som kan itereres over.
     */
    Iterable<GridCell<Character>> getNext1Tetro();

    /**
     * Denne metoden skal virke på nøyaktig samme måte som metoden ovenfor. Den skal lage en liste med
     * GridCell objekter når den iterer over skal den kunne hente ut posisjonen til den neste neste brikken.
     * @return skal være en liste med GridCell objekter som kan itereres over.
     */
    Iterable<GridCell<Character>> getNext2Tetro();

    /**
     * Denne metoden skal virke på nøyaktig samme måte som metoden ovenfor. Den skal lage en liste med
     * GridCell objekter når den iterer over skal den kunne hente ut posisjonen til den neste neste neste brikken.
     * @return skal være en liste med GridCell objekter som kan itereres over.
     */
    Iterable<GridCell<Character>> getNext3Tetro();

    /**
     * Denne metoden skal virke på nøyaktig måte som metoden ovenfor. Den skal lage en liste med
     * GridCell objekter når den iterer over skal den kunne hente ut posisjonen til brikken som er satt
     * på hold
     * @return skal være en liste med GridCell objekter som kan itereres over
     */
    Iterable<GridCell<Character>> getHoldTetro();

    /**
     * Denne metoden skal brukes for å sjekke og oppdatere GameState
     * @return statusen til spillet altså GameState sin status
     */
    GameState getGameState();

    /**
     * Denne metoden skal hente ut det nåværende levelet i spillet
     * @return det nåværende levelet i spillet
     */
    int getGameLevel();

    /**
     * Denne metoden skal hente ut den nåværende scoren
     * @return antall poeng spilleren har
     */
    int getCurrentScore();
}

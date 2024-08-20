package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {

    /**
     * I denne metoden skal det automatisk velges et av de 6
     * forskjellige tegnene som representerer en Tetromino
     * Dette tegnet skal brukes som argument i et kall på
     * newTetromino metoden for å opprette en ny Tetromino
     * @return en Tetromino av typen som blir valgt tilfeldig
     */
    Tetromino getNext();

    /**
     * Denne metoden skal det bare hentes ut en startverdi
     * for der holdTetromino skal være
     * @return Tetromino med holdTetromino sin startverdi
     */
    Tetromino getStartHoldTetromino();
}

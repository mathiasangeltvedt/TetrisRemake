package no.uib.inf101.tetris.model.tetromino;

import java.util.*;

/**
 * Denne klassen brukes for å lage et tilfeldig Tetromino objekt.
 * Den implementerer TetrominoFactory som er grensesnittet med metodene som bestemmer
 * hvordan de forskjellige tetrominoene hentes ut.
 */
public class RandomTetrominoFactory implements TetrominoFactory{

    Character chosenTetromino;

    @Override
    public Tetromino getNext() {
        List<Character> possibleTetrominos = new ArrayList<>();
        possibleTetrominos.add('T');
        possibleTetrominos.add('S');
        possibleTetrominos.add('Z');
        possibleTetrominos.add('L');
        possibleTetrominos.add('I');
        possibleTetrominos.add('O');
        possibleTetrominos.add('J');

        Random random = new Random();
        int randomTetromino = random.nextInt(possibleTetrominos.size());
        this.chosenTetromino = possibleTetrominos.get(randomTetromino);

        return Tetromino.newTetromino(this.chosenTetromino);
    }

    @Override
    public Tetromino getStartHoldTetromino() {
        return Tetromino.newTetromino('H');
    }
}

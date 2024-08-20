package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {

    String string;
    int currentCharInString;

    public PatternedTetrominoFactory(String string) {
        this.string = string;

    }

    @Override
    public Tetromino getNext() {
        char tetrominoToBeMade = string.charAt(currentCharInString);
        if (currentCharInString == string.length() - 1) {
            currentCharInString = 0;
        } else {
            currentCharInString += 1;
        }
        return Tetromino.newTetromino(tetrominoToBeMade);
    }

    @Override
    public Tetromino getStartHoldTetromino() {
        return Tetromino.newTetromino('H');
    }
}

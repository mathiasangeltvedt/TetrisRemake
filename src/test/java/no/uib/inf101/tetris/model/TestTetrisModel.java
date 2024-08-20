package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTetrisModel {
    @Test
    public void initialPositionOfO() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }

    @Test
    public void initialPositionOfI() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
    }

    @Test
    public void successfulMove() {
        TetrisBoard board = new TetrisBoard(20, 20);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        model.mainTetromino.shiftedToTopCenterOf(board);
        assertTrue(model.moveTetromino(0, 3));
        model.mainTetromino.shiftedToTopCenterOf(board);
        assertTrue(model.moveTetromino(17, 0));
        model.mainTetromino.shiftedToTopCenterOf(board);
        assertTrue(model.moveTetromino(0, 0));
    }

    @Test
    public void movingChangesValues() {
        TetrisBoard board = new TetrisBoard(10, 9);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 3), 'T')));

        model.moveTetromino(3, 4);

        List<GridCell<Character>> tetrominoCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetrominoCells.add(gc);
        }

        assertEquals(4, tetrominoCells.size());
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(3, 6), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(3, 7), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(3, 8), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(4, 7), 'T')));
    }

    @Test
    public void falseMoveReturnFalseAndDoesNotMoveTetro() {
        TetrisBoard board = new TetrisBoard(10, 9);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        assertFalse(model.moveTetromino(10, 9));
        assertFalse(model.moveTetromino(-3, 0));
        assertFalse(model.moveTetromino(0, -3));

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 3), 'T')));

        model.moveTetromino(11, 0);

        List<GridCell<Character>> tetrominoCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetrominoCells.add(gc);
        }

        assertEquals(4, tetrominoCells.size());
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 4), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(1, 3), 'T')));
    }

    @Test
    public void returnFalseIfCellIsOccupied() {
        TetrisBoard board = new TetrisBoard(10, 9);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        board.set(new CellPosition(0, 0), 'T');
        board.set(new CellPosition(0, 8), 'S');
        board.set(new CellPosition(9, 0), 'J');
        board.set(new CellPosition(9, 8), 'I');

        assertFalse(model.moveTetromino(0, -2));
        assertFalse(model.moveTetromino(9, -2));

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 3), 'T')));

        model.moveTetromino(0, -2);
        model.moveTetromino(9, -2);

        List<GridCell<Character>> tetrominoCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetrominoCells.add(gc);
        }

        assertEquals(4, tetrominoCells.size());
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 4), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(1, 3), 'T')));
    }

    @Test
    public void succesfulRotation() {
        TetrisBoard board = new TetrisBoard(20, 20);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        model.mainTetromino.shiftedToTopCenterOf(board);
        model.moveTetromino(5, 0);
        assertTrue(model.rotateTetromino());

        model.mainTetromino.shiftedToTopCenterOf(board);
        model.moveTetromino(10, 0);
        assertTrue(model.rotateTetromino());
    }

    @Test
    public void roationChangesValues() {
        TetrisBoard board = new TetrisBoard(10, 9);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);
        model.moveTetromino(3, 0);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(3, 2), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(3, 3), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(3, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(4, 3), 'T')));


        model.rotateTetromino();

        List<GridCell<Character>> tetrominoCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetrominoCells.add(gc);
        }

        assertEquals(4, tetrominoCells.size());
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(2, 3), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(3, 3), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(3, 4), 'T')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(4, 3), 'T')));
    }

    @Test
    public void returnFalseIfRotatedTetroIsOutsideBoard() {
        TetrisBoard board = new TetrisBoard(20, 20);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        model.mainTetromino.shiftedToTopCenterOf(board);
        assertFalse(model.rotateTetromino());

        model.mainTetromino.shiftedToTopCenterOf(board);
        assertFalse(model.rotateTetromino());
    }

    @Test
    public void returnFalseIfRotatedTetroIsOnTopOfAnotherTetro() {
        TetrisBoard board = new TetrisBoard(10, 9);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);

        board.set(new CellPosition(1, 4), 'T');
        assertFalse(model.rotateTetromino());
    }

    @Test
    public void circleDoesNotMoveWhenRotated() {
        TetrisBoard board = new TetrisBoard(10, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));

        model.rotateTetromino();

        List<GridCell<Character>> tetrominoCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetrominoCells.add(gc);
        }

        assertEquals(4, tetrominoCells.size());
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));

        model.rotateTetromino();

        List<GridCell<Character>> tCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tCells.add(gc);
        }

        assertEquals(4, tCells.size());
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }

    @Test
    public void checkIfDropPositionIsCorrect() {
        TetrisBoard board = new TetrisBoard(10, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));

        model.dropTetromino();

        List<GridCell<Character>> tCells = new ArrayList<>();
        for (GridCell<Character> gc : board) {
            if (!gc.value().equals('-')) {
                tCells.add(gc);
            }
        }

        assertTrue(tCells.contains(new GridCell<>(new CellPosition(8, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(8, 5), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(9, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(9, 5), 'O')));
    }

    @Test
    public void checkIfDropPositionIsCorrect2() {
        TetrisBoard board = new TetrisBoard(10, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);
        board.set(new CellPosition(5, 3), 'I');
        board.set(new CellPosition(5, 4), 'I');
        board.set(new CellPosition(5, 5), 'I');
        board.set(new CellPosition(5, 6), 'I');

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));

        model.dropTetromino();

        List<GridCell<Character>> tCells = new ArrayList<>();
        for (GridCell<Character> gc : board) {
            if (!gc.value().equals('-')) {
                tCells.add(gc);
            }
        }

        assertEquals(8, tCells.size());
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(3, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(3, 5), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(4, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(4, 5), 'O')));
    }

    @Test
    public void checkIfIDropPositionIsCorrect() {
        TetrisBoard board = new TetrisBoard(15, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        TetrisModel model = new TetrisModel(board, factory);
        model.dropTetromino();

        List<GridCell<Character>> tCells = new ArrayList<>();
        for (GridCell<Character> gc : board) {
            if (!gc.value().equals('-')) {
                tCells.add(gc);
            }
        }

        assertEquals(4, tCells.size());
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(14, 3), 'I')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(14, 4), 'I')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(14, 5), 'I')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(14, 6), 'I')));
    }

    @Test
    public void checkIfClockTickWorksAsIntended() {
        TetrisBoard board = new TetrisBoard(10, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        TetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));

        model.clockTick();

        List<GridCell<Character>> tetrominoCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tetrominoCells.add(gc);
        }

        assertEquals(4, tetrominoCells.size());
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(2, 4), 'O')));
        assertTrue(tetrominoCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));

        model.clockTick();

        List<GridCell<Character>> tCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getFallingTetro()) {
            tCells.add(gc);
        }

        assertEquals(4, tCells.size());
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(2, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(3, 4), 'O')));
        assertTrue(tCells.contains(new GridCell<>(new CellPosition(3, 5), 'O')));
    }

    @Test
    public void checkIfGameLevelUpdates() {
        TetrisBoard board = new TetrisBoard(10, 4);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);
        board.set(new CellPosition(5, 0), 'L');
        board.set(new CellPosition(5, 1), 'L');
        board.set(new CellPosition(5, 2), 'L');
        board.set(new CellPosition(5, 3), 'L');
        board.set(new CellPosition(6, 0), 'L');
        board.set(new CellPosition(6, 1), 'L');
        board.set(new CellPosition(6, 2), 'L');
        board.set(new CellPosition(6, 3), 'L');
        board.set(new CellPosition(7, 0), 'L');
        board.set(new CellPosition(7, 1), 'L');
        board.set(new CellPosition(7, 2), 'L');
        board.set(new CellPosition(7, 3), 'L');
        board.set(new CellPosition(8, 0), 'L');
        board.set(new CellPosition(8, 1), 'L');
        board.set(new CellPosition(8, 2), 'L');
        board.set(new CellPosition(8, 3), 'L');
        board.set(new CellPosition(9, 0), 'L');
        board.set(new CellPosition(9, 1), 'L');
        board.set(new CellPosition(9, 2), 'L');
        board.set(new CellPosition(9, 3), 'L');

        model.dropTetromino();
        assertEquals(2, model.getGameLevel());
    }

    @Test
    public void checkIfScoreUpdates() {
        TetrisBoard board = new TetrisBoard(10, 4);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);
        board.set(new CellPosition(7, 0), 'L');
        board.set(new CellPosition(7, 1), 'L');
        board.set(new CellPosition(7, 2), 'L');
        board.set(new CellPosition(7, 3), 'L');
        board.set(new CellPosition(8, 0), 'L');
        board.set(new CellPosition(8, 1), 'L');
        board.set(new CellPosition(8, 2), 'L');
        board.set(new CellPosition(8, 3), 'L');
        board.set(new CellPosition(9, 0), 'L');
        board.set(new CellPosition(9, 1), 'L');
        board.set(new CellPosition(9, 2), 'L');
        board.set(new CellPosition(9, 3), 'L');

        model.dropTetromino();
        assertEquals(500, model.getCurrentScore());
    }
}
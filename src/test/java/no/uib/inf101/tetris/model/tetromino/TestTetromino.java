package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.tetris.model.TetrisBoard;
import org.junit.jupiter.api.Test;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.CellPosition;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestTetromino {
    @Test
    public void testHashCodeAndEquals() {
        Tetromino t1 = Tetromino.newTetromino('T');
        Tetromino t2 = Tetromino.newTetromino('T');
        Tetromino t3 = Tetromino.newTetromino('T').shiftedBy(1, 0);
        Tetromino s1 = Tetromino.newTetromino('S');
        Tetromino s2 = Tetromino.newTetromino('S').shiftedBy(0, 0);

        assertEquals(t1, t2);
        assertEquals(s1, s2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1, s1);
    }

    @Test
    public void tetrominoIterationOfT() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 100), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'T')));
    }

    @Test
    public void tetrominoIterationOfS() {
        Tetromino tetromino = Tetromino.newTetromino('S');
        tetromino = tetromino.shiftedBy(10, 20);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objs.add(gc);
        }
        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 21), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 22), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 20), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 21), 'S')));

        // Shift by the same amount again
        tetromino = tetromino.shiftedBy(10, 20);

        // Collect which objects are iterated through
        List<GridCell<Character>> objects = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objects.add(gc);
        }
        // Check that we got the expected GridCell objects
        assertEquals(4, objects.size());
        // Check if changing the position twice with the same amount will make the TetrominoPos double
        assertTrue(objects.contains(new GridCell<>(new CellPosition(21, 41), 'S')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(21, 42), 'S')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(22, 40), 'S')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(22, 41), 'S')));
    }

    @Test
    public void shiftedToTopOfCenterCharacterTWithOddCols() {
        TetrisBoard board = new TetrisBoard(20, 9);
        Tetromino tetromino = Tetromino.newTetromino('T');
        tetromino = tetromino.shiftedBy(10, 20);

        tetromino = tetromino.shiftedToTopCenterOf(board);

        List<GridCell<Character>> objects = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objects.add(gc);
        }

        assertEquals(4, objects.size());
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 4), 'T')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(1, 3), 'T')));
    }

    @Test
    public void shiftedToTopOfCenterCharacterSWithOddCols() {
        TetrisBoard board = new TetrisBoard(20, 9);
        Tetromino tetromino = Tetromino.newTetromino('S');
        tetromino = tetromino.shiftedBy(10, 20);

        tetromino = tetromino.shiftedToTopCenterOf(board);

        List<GridCell<Character>> objects = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objects.add(gc);
        }

        assertEquals(4, objects.size());
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 3), 'S')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 4), 'S')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(1, 3), 'S')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(1, 2), 'S')));
    }

    @Test
    public void shiftedToTopOfCenterCharacterOWithEvenCols() {
        TetrisBoard board = new TetrisBoard(20, 10);
        Tetromino tetromino = Tetromino.newTetromino('O');
        tetromino = tetromino.shiftedBy(10, 20);

        tetromino = tetromino.shiftedToTopCenterOf(board);

        List<GridCell<Character>> objects = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objects.add(gc);
        }

        assertEquals(4, objects.size());
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }

    @Test
    public void shiftedToTopOfCenterCharacterIWithEvenCols() {
        TetrisBoard board = new TetrisBoard(20, 10);
        Tetromino tetromino = Tetromino.newTetromino('I');
        tetromino = tetromino.shiftedBy(10, 20);

        tetromino = tetromino.shiftedToTopCenterOf(board);

        List<GridCell<Character>> objects = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objects.add(gc);
        }

        assertEquals(4, objects.size());
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
    }

    @Test
    public void rotateT() {
        TetrisBoard board = new TetrisBoard(20, 10);
        Tetromino tetromino = Tetromino.newTetromino('T');
        tetromino = tetromino.shiftedToTopCenterOf(board);
        tetromino = tetromino.shiftedBy(10, 0);

        List<GridCell<Character>> objects = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            objects.add(gc);
        }

        assertEquals(4, objects.size());
        assertTrue(objects.contains(new GridCell<>(new CellPosition(10, 4), 'T')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(10, 5), 'T')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(10, 6), 'T')));
        assertTrue(objects.contains(new GridCell<>(new CellPosition(11, 5), 'T')));

        tetromino = tetromino.rotateTetro();

        List<GridCell<Character>> object = new ArrayList<>();
        for (GridCell<Character> gc : tetromino) {
            object.add(gc);
        }

        assertEquals(4, object.size());
        assertTrue(object.contains(new GridCell<>(new CellPosition(9, 5), 'T')));
        assertTrue(object.contains(new GridCell<>(new CellPosition(10, 6), 'T')));
        assertTrue(object.contains(new GridCell<>(new CellPosition(10, 5), 'T')));
        assertTrue(object.contains(new GridCell<>(new CellPosition(11, 5), 'T')));
    }
}

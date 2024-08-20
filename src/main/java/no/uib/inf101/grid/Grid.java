package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Denne klassen brukes for å opprette et brett som spilleren skal
 * spille tetris på. Den er muterbar og kan endres på slik at objekter kan legges
 * på brettet. Klassen grid er generell slik at man kan legge inn den typen man ønsker på
 * brettet.
 * @param <E> er parameter for å gjøre klassen grid generell.
 */
public class Grid<E> implements IGrid<E>{
    // Feltvariabler; kolonner, rader og grid
    private final List<List<GridCell<E>>> grid;
    private final int cols;
    private final int rows;

    /**
     * Konstruktør uten spesifisert verdi som skal være i cellene
     * Her vil verdien i cellene automatisk settes til verdien "null"
     * Dersom oppgitt antall rader og/eller kolonner er mindre eller lik null
     * kaster den en IllegalArgumentException
     * @param rows antall rader grid skal ha
     * @param cols antall kolonner grid skal ha
     */
    public Grid(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and cols cannot be less or equal to 0");
        }
        this.cols = cols;
        this.rows = rows;
        this.grid = new ArrayList<>();

        for (int i = 0 ; i < rows ; i++) {
            List<GridCell<E>> row = new ArrayList<>();
            for (int j = 0 ; j < cols ; j++) {
                GridCell<E> col = new GridCell<>(new CellPosition(i, j), null);
                row.add(col);
            }
            this.grid.add(row);
        }
    }

    /**
     * Konstruktør med spesifisert verdi som skal være i cellene
     * Her vil verdien i cellene settes til det som blir brukt som standarverdi
     * @param rows antall rader grid skal ha
     * @param cols antall kolonner grid skal ha
     * @param defaultValue verdien cellene i grid skal inneholde
     */
    public Grid(int rows, int cols, E defaultValue) {
        this(rows, cols);
        for (int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
                set(new CellPosition(row, col), defaultValue);
            }
        }
    }

    @Override
    public void set(CellPosition pos, E value) {
        if (!positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("Oppgitt posisjon er utenfor grensen til grid!");
        }
        List<GridCell<E>> rowValuesAsList = grid.get(pos.row());
        GridCell<E> valueToBeInCell = new GridCell<>(pos, value);
        rowValuesAsList.set(pos.col(), valueToBeInCell);
    }

    @Override
    public E get(CellPosition pos) {
        if (!positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("Oppgitt posisjon er utenfor grensen til grid!");
        }
        List<GridCell<E>> rowValuesAsList = grid.get(pos.row());
        GridCell<E> valueInCell = rowValuesAsList.get(pos.col());
        return valueInCell.value();
    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        if (0 <= pos.row() && pos.row() < this.rows) {
            return 0 <= pos.col() && pos.col() < this.cols;
        }
        return false;
    }

    @Override
    public Iterator<GridCell<E>> iterator() {
        ArrayList<GridCell<E>> newList = new ArrayList<>();
        for (int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
                E value = get(new CellPosition(row, col));
                GridCell<E> newCell = new GridCell<>(new CellPosition(row, col), value);
                newList.add(newCell);
            }
        }
        return newList.iterator();

    }

    @Override
    public int rows() {
        return this.rows;
    }

    @Override
    public int cols() {
        return this.cols;
    }
}

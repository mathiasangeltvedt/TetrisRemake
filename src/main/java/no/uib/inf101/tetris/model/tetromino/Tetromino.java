package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

import java.util.*;

/**
 * Denne klassen representerer alle delene av en tetromino.
 * I klassen finner man ting som metode for å opprette en ny tetromino,
 * metode for å flytte på en tetromino og en metode for å rotere en tetromino.
 * Klassen brukes generelt for å gi ut nye tetrominoer ut ifra de forskjellige metodene.
 * Denne klassen er ikke muterbar, ettersom den alltid gir ut en ny tetromino, og ikke endrer
 * på en tetromino som allerede eksisterer.
 */
public class Tetromino implements Iterable<GridCell<Character>>{

    // Instansvariabler
    char symbolOfTetromino;
    boolean[][] tetrominoShape;
    CellPosition tetrominoPos;

    private Tetromino(char symbolOfTetromino, boolean[][] tetrominoShape, CellPosition tetrominoPos) {
        this.symbolOfTetromino = symbolOfTetromino;
        this.tetrominoShape = tetrominoShape;
        this.tetrominoPos = tetrominoPos;
    }

    /**
     * Denne metoden brukes for å lage en ny Tetromino
     * Den får inn et gitt symbol/karakter og ut ifra denne så lager den
     * en ny boolean[][] som skal representere en Tetromino på posisjon (0, 0)
     * Dersom symbolet som blir gitt ikke representerer en Tetromino
     * vil det kastes en exception
     * Metoden er også pakke-privat derfor er det ikke oppgitt om den er
     * private, public eller protected som vil si at den er default
     * @param character er karakteren/symbolet som representerer en Tetromino
     * @return en boolean[][] som representerer en Tetromino
     */
     static Tetromino newTetromino(char character) {
         boolean [][] tetrominoToBeMade;
        if (character == 'T') {
            tetrominoToBeMade = new boolean[][]{
                    {false, false, false},
                    {true, true, true},
                    {false, true, false}
            };
        }
        else if (character == 'L') {
            tetrominoToBeMade = new boolean[][] {
                    { false, false, false },
                    { true,  true,  true },
                    { true,  false, false }
            };
        }
        else if (character == 'J') {
            tetrominoToBeMade = new boolean[][] {
                    { false, false, false },
                    { true,  true,  true },
                    { false,  false, true }
            };
        }
        else if (character == 'S') {
            tetrominoToBeMade = new boolean[][] {
                    { false, false, false },
                    { false,  true,  true },
                    { true,  true, false }
            };
        }
        else if (character == 'Z') {
            tetrominoToBeMade = new boolean[][] {
                    { false, false, false },
                    { true,  true,  false },
                    { false, true, true }
            };
        }
        else if (character == 'I') {
            tetrominoToBeMade = new boolean[][] {
                    { false, false, false, false },
                    { true,  true,  true, true },
                    { false,  false, false, false },
                    { false,  false, false, false }
            };
        }
        else if (character == 'O') {
            tetrominoToBeMade = new boolean[][] {
                    { false, false, false, false },
                    { false, true,  true, false },
                    { false, true, true, false },
                    { false, false, false, false }
            };
        }
        else if (character == 'H') {
            tetrominoToBeMade = new boolean[][] {
                    {false, false, false},
                    {false, true, false},
                    {false, false, false}
            };
        }
        else {
            throw new IllegalArgumentException("Illegal symbol for a Tetromino");
        }
        return new Tetromino(character, tetrominoToBeMade, new CellPosition(0, 0));
    }

    /**
     * Denne metoden skal endre på posisjonen til en Tetromino
     * den bruker parameterne til å bestemme hvor stort hopp
     * Tetrominoen skal ta
     *
     * @param deltaRow antall rader den skal bevege seg
     * @param deltaCol antall kolonner den skal bevege seg
     * @return et nytt tetromino objekt på ny posisjon
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        int newColPos = this.tetrominoPos.col() + deltaCol;
        int newRowPos = this.tetrominoPos.row() + deltaRow;
        return new Tetromino(this.symbolOfTetromino, this.tetrominoShape, new CellPosition(newRowPos, newColPos));
    }

    /**
     * Denne metoden skal regne ut hvor på brette Tetrominoen skal ende opp
     * Først og fremst ettersom øverste del av Tetrominoet alltid vil være ureelt
     * vil vi sette raden venstre-topp skal ende opp på til -1, slik at selve Tetrominoet
     * faktisk kommer på øverste rad. Vi må også regne ut kolonnen den skal ende opp på,
     * og dette gjør vi med å sjekke om det er oddetall eller partall antall kolonner i
     * dimensjonen. Ut ifra dette må vi trekke fra et visst antall slik at det ikke er
     * venstre topp-hjørne som ender opp på midten, men heller midten av selve brikken.
     * (Vi må trekke fra 1 ekstra kolonne ettersom CellPosition lager en index fra 0 og opp,
     * mens lengden av tetroDimension.cols() går fra 1 og oppover
     * @param tetroDimension er dimensjonen på brettet vi har
     * @return en kopi av Tetromionet på utregnet posisjon
     */
    public Tetromino shiftedToTopCenterOf(GridDimension tetroDimension) {
        int newColPos;
        if (tetroDimension.cols() % 2 == 0){
            if (this.tetrominoShape[0].length % 2 == 0) {
                newColPos = (tetroDimension.cols() / 2) - 2;
            }
            else {
                newColPos = (tetroDimension.cols() / 2) - 1;
            }
        }
        else {
            if (this.tetrominoShape[0].length % 2 == 0) {
                newColPos = (tetroDimension.cols() / 2) - 1;
            }
            else {
                newColPos = (tetroDimension.cols() / 2) - 2;
            }
        }
        return new Tetromino(this.symbolOfTetromino,
                            this.tetrominoShape,
                            new CellPosition(-1 , newColPos));
    }

    /**
     * Denne metoden tar en Tetromino-brikke og henter ut dimensjonene til brikken
     * så lager den en kopi av brikken med samme dimensjon men med tomme verdier
     * Deretter går den gjennom en nøstet for-løkke. Det selve "formelen" for å rotere brikken
     * gjør er at den gjør øverste raden og kolonnen lengs til venste,
     * blir nedre rad og kolonnen til venstre. Øverste rad og midtre kolonne blir midtre rad og kolonnen
     * lengst til venstre.
     * @return er en ny Tetromino med nye posisjoner for brikken.
     */
    public Tetromino rotateTetro() {
        boolean[][] originalTetro = this.tetrominoShape;
        int originalHeight = originalTetro.length;
        int originalWidth = originalTetro[0].length;
        boolean[][] rotatedTetro = new boolean[originalHeight][originalWidth];

        for (int row = 0 ; row < originalHeight ; row++) {
            for (int col = 0 ; col < originalWidth ; col++) {
                rotatedTetro[originalWidth - 1 - col][row] = this.tetrominoShape[row][col];
            }
        }
        return new Tetromino(this.symbolOfTetromino, rotatedTetro, this.tetrominoPos);
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        List<GridCell<Character>> characters = new ArrayList<>();
        for (int row = 0 ; row < this.tetrominoShape.length ; row++) {
            for (int col = 0 ; col < this.tetrominoShape[0].length ; col++) {
                if (this.tetrominoShape[row][col]) {
                    GridCell<Character> tetrominoCell = new GridCell<>(
                            new CellPosition(this.tetrominoPos.row() + row,
                                            this.tetrominoPos.col() + col),
                            this.symbolOfTetromino);
                    characters.add(tetrominoCell);
                }
            }

        }
        return characters.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tetromino gridCells = (Tetromino) o;
        return symbolOfTetromino == gridCells.symbolOfTetromino &&
                                    Arrays.deepEquals(tetrominoShape, gridCells.tetrominoShape) &&
                                    Objects.deepEquals(tetrominoPos, gridCells.tetrominoPos);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(symbolOfTetromino, tetrominoPos);
        result = 31 * result + Arrays.deepHashCode(tetrominoShape);
        return result;
    }
}

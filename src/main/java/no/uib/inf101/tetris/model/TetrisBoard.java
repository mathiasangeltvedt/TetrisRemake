package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

/**
 * Denne klassen utvider Grid klassen som brukes for å lage et brett til spillet.
 * Denne klassen bruker grid sin konstruktør, og til å lage et brett, og bruker dette brettet
 * til å gjøre ting som å fjerne fuller rader, den kan skrive ut et brett i form av en string osv.
 * TetrisBoard er en muterbar klasse, som vil si at man kan endre på ting i klassen, som for eksempel
 * verdiene som er på brettet.
 */
public class TetrisBoard extends Grid<Character> {

    // Konstruktør
    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
     * Denne metoden brukes til testing og for å sjekke om brettet blir opprettet som det skal
     * Den skal gå gjennom antall rader og kolonner og legge til verdiene i string-form
     * Dersom det ikke er en gitt verdi skal den bare skrive '-'.
     * @return en string-form av tetris-brettet
     */
    public String prettyString() {
        String boardInString = "";
        for (int row = 0 ; row < rows() ; row++) {
            for (int col = 0 ; col < cols() ; col++) {
                boardInString += get(new CellPosition(row, col));
            }
            if (!(row == rows() - 1)) {
                boardInString += "\n";
            }
        }
        return boardInString;
    }

    /**
     * Denne metoden får inn en string versjon av et spillebrett
     * og oppretter et nytt brett av typen TetrisBoard med de oppgitte
     * verdiene som skal være på brettet
     * @param newBoard er brettet de ønsker å opprette
     * @return er brettet vi har opprettet
     */
    public static TetrisBoard getTetrisBoardWithContents(String[] newBoard) {
        int rows = newBoard.length;
        int cols = newBoard[0].length();
        TetrisBoard board = new TetrisBoard(rows, cols);
        for (int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
                board.set(new CellPosition(row, col), newBoard[row].charAt(col));
            }
        }
        return board;
    }

    /**
     * Denne metoden skal gå gjennom alle radene på brettet
     * dersom en rad på brettet ikke inneholder noen tomme ruter så skal
     * denne raden fjernes, derfor blir øker vi tellevariabelen med 1.
     * Hvis dette ikke er tilfellet og vi ikke er ferdig med hele brettet
     * så skal vi kopiere rad b og sette rad a til å bli denne raden.
     * ellers skal rad a bli satt til en tom rad.
     * @return er antall rader som blir fjernet fra brettet.
     */
    public int removeFullRows() {
        int amountOfRowsToBeRemoved = 0;
        int a = this.rows() - 1;
        int b = this.rows() - 1;
        while (a >= 0) {
            while (b >= 0 && !checkIfValueIsInRow('-', b) && !checkIfValueIsInRow('/', b)) {
                amountOfRowsToBeRemoved++;
                b--;
            }
            if (b >= 0) {
                copyValuesOfARowToAnotherRow(b, a);
            }
            else {
                setAllValuesInRowToValueX(a, '-');
            }
            a--;
            b--;
        }
        return amountOfRowsToBeRemoved;
    }

    /**
     * Denne metoden går gjennom alle kolonnene i en rad
     * og sjekker om et sted i raden har et element.
     * Dersom et sted i raden har det gitte elementet
     * vil metoden returnere true, ellers vil den returnere false.
     * @param value er elementet vi ønsker å finne i raden
     * @return enten true eller false avhengig av om elementet finnes på brettet eller ikke
     */
    private boolean checkIfValueIsInRow(char value, int rowToBeChecked) {
        for (int col = 0 ; col < cols() ; col++) {
            if (this.get(new CellPosition(rowToBeChecked, col)) == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Denne metoden skal brukes til å endre verdien til alle kolonner
     * på en gitt rad på brett
     * @param rowToBeChanged er raden som skal endres på
     * @param value er verdiene som skal gis til kolonnene
     */
    private void setAllValuesInRowToValueX(int rowToBeChanged, char value) {
                for (int col = 0 ; col < this.cols() ; col++) {
                    this.set(new CellPosition(rowToBeChanged, col), value);
                }
    }

    /**
     * Denne metoden skal brukes til å hente ut verdiene til en rad
     * og kopierer disse verdiene over til en annen rad
     * @param rowToBeCopied er raden som skal kopieres
     * @param rowToBecomeCopy er raden som skal "bli" kopien
     */
    private void copyValuesOfARowToAnotherRow(int rowToBeCopied, int rowToBecomeCopy) {
        for (int col = 0 ; col < this.cols() ; col++) {
            char valueToBeSetInNewRow = this.get(new CellPosition(rowToBeCopied, col));
            this.set(new CellPosition(rowToBecomeCopy, col), valueToBeSetInNewRow);
        }
    }
}

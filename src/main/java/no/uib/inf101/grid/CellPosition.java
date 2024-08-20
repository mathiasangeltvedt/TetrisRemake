package no.uib.inf101.grid;

/**
 * Denne klassen brukes kun for å holde på verdien til
 * raden og kolonnen til den oppgitte posisjonen
 * @param row er indeksen til raden
 * @param col er indeksen til kolonnen
 */
public record CellPosition(int row, int col) {}

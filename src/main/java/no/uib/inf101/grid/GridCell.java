package no.uib.inf101.grid;

/**
 * Denne klassen brukes til å representere en celle i en grid
 * @param pos er posisjonen til cellen
 * @param value er verdien som ligger på posisjonen til cellen
 * @param <E> et generisk typeparameter for å kunne gjøre variabelen value til en generisk type
 */
public record GridCell<E>(CellPosition pos, E value) {}

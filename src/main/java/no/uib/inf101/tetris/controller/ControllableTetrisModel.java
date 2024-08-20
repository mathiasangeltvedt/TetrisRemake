package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

    /**
     * Denne metoden skal brukes til å flytte rundt på en Tetrominobrikke
     * @param deltaRow antall rader en brikke skal bevege seg
     * @param deltaCol antall kolonner en brikke skal bevege seg
     * @return en boolean verdi som forteller om flyttingen faktisk skjedde eller ikke;
     * dersom return er false, flyttet den seg ikke, ellers flyttet den seg
     */
    boolean moveTetromino(int deltaRow, int deltaCol);

    /**
     * Denne metoden skal brukes til å flytte skyggen
     * til posisjonen til selve brikken, og gi skyggen sine posisjon-verdier
     * akkurat samme posisjon-verdier som tetrominoen. Dette vil gjøre at når
     * tetrominoen flytter på seg så vil skyggen flytte på seg, og dersom tetrominoen
     * roterer seg så vil skyggen også rotere seg. Vi vil alltid bruke denne metoden
     * før vi dropper skyggen slik at den havner på riktig sted og er riktig vei.
     */
    void moveShadowTetrominoToTetroPos();

    /**
     * Denne metoden skal brukes til å rotere selve brikken
     * Den sjekker om rotering gjør at brikken havner utenfor, eller
     * om den havner oppå en annen brikke. Så lenge den ikke er det så skal
     * spilleren få lov å snu brikken
     * @return en boolean verdi som tilsier om brikken ble snudd eller ikke
     */
    boolean rotateTetromino();

    /**
     * Denne metoden skal brukes for å holde på en tetromino
     * Den sjekker om det er mulig å holde tetrominoen, dette skal kun være
     * mulig om du ikke allerede har har holdt en tetromino før en tetromino
     * festes til brettet. Det vil si at om du får ut en tetromino og holder denne, så kan du ikke holde
     * en ny tetromino før du har festet en tetromino til brettet. Når du har festet en tetromino
     * til brettet og hentet ut en ny, kan du holde en ny tetromino igjen.
     * @return en boolean verdi som tilsier om brikken ble holdt eller ikke
     */
    boolean holdTetromino();

    /**
     * Denne metoden skal brukes for å droppe en brikke
     * Den sjekker om det er mulig å droppe brikken, og så lenge det er lov
     * så skal den droppe brikken.
     * Den går gjennom en løkke som sjekker om dropping er mulig,
     * og når det ikke lenger er mulig skal brikken limes til brettet.
     * Til slutt gjør et kall til hjelpemetoden getNewTetro for å hente en ny fallende brikke
     */
    void dropTetromino();

    /**
     * Denne metoden skal brukes for å sjekke og oppdatere GameState
     * @return statusen til spillet altså GameState sin status
     */
    GameState getGameState();

    /**
     * Denne metoden skal endre GameState i spillet
     * @param gameState ønsket status på spillet
     */
    void setGameState(GameState gameState);

    /**
     * Denne metoden skal brukes for å hente ut hvor mange
     * millisekunder (som integer) det skal være mellom hvert klokkeslag
     * @return antall millisekunder mellom hvert klokkeslag
     */
    Integer amountOfmsBetweenEachTick();

    /**
     * Denne metoden skal brukes for å flytte brikken automatisk
     * en og en rad nedover helt til den ikke kan flyttes nedover mer.
     * Når den ikke kan flyttes nedover mer, skal den limes fast til brettet.
     */
    void clockTick();

    /**
     * Denne metoden skal hente ut det nåværende levelet i spillet
     * @return levelet på spillet
     */
    int getGameLevel();

    /**
     * Denne metoden skal hente ut den nåværende scoren
     * @return antall poeng spilleren har
     */
    int getCurrentScore();

    /**
     * Denne metoden skal brukes for å starte spillet på nytt
     * Her skal vi initere alle feltvariablene tilbake til standarverdier,
     * slik at vi får et helt nytt spill
     */
    void restartGame();
}

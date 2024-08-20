package no.uib.inf101.tetris.view;

import java.awt.*;

public interface ColorTheme {

    /**
     * Denne metoden skal hente ut fargen som skal være på
     * selve cellen i grid
     * @param character er karakteren som skal representere fargen som skal være der
     * @return fargen som skal settes på cellen
     */
    Color getCellColor(char character);

    /**
     * Denne metoden skal hente ut fargen som skal være på rammen rundt
     * selve brettet vi spiller på
     * @return er fargen som skal være rundt brettet
     */
    Color getFrameColor();

    /**
     * Denne metoden skal hente ut fargen som skal være bakgrunnsfarge
     * på selve brettet.
     * @return bakgrunsfargen på selve brettet
     */
    Color getBackgroundColor();

    /**
     * Denne metoden skal hente ut en farge som skal settes på skjermen
     * dersom spillet er i state GAME_OVER
     * @return en passende farge for dette
     */
    Color getTransparentColor();

    /**
     * Denne metoden skal hente ut en farge som skal settes bak
     * den neste tetrominoen som skal komme ut
     * @return en passende farge for dette
     */
    Color getNextAndHoldTetrominoBackground();

    /**
     * Denne metoden skal brukes for å hente ut fargen til
     * tittelen på spillet
     * @return en passende farge for dette
     */
    Color getTitleColor();

    /**
     * Denne metoden skal brukes for å hente ut fargen som brukes på teksten
     * på ulik tekst rundt i spillet
     * @return en passende farge for dette
     */
    Color getTextColor();

    /**
     * Denne metoden skal brukes for å hente ut fargen som skal settes på brettet
     * dersom spilleren setter spiller på pause
     * @return en passende farge for dette
     */
    Color getPauseColor();
}

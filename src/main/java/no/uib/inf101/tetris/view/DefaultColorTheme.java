package no.uib.inf101.tetris.view;

import java.awt.*;

/**
 * Denne klassen brukes enkelt å greit til å hente ut forskjellige farger for forskjellige
 * ting som skal brukes i spillet. For eksempel farge på brikkene, bakgrunn, brettet osv.
 * Denne klassen implementerer ColorTheme slik at TetrisView har tilgang til å hente disse fargene.
 */
public class DefaultColorTheme implements ColorTheme{

    @Override
    public Color getCellColor(char character) {
        Color color = switch(character) {
            case 'I' -> new Color(89,203,232, 255);
            case 'T' -> new Color(199,36,177, 255);
            case 'J' -> new Color(46,103,248, 255);
            case 'L' -> new Color(255,173,0, 255);
            case 'O' -> new Color(247,247,73, 255);
            case 'S' -> new Color(47,249,36, 255);
            case 'Z' -> new Color(225,6,0, 255);
            case 'H' -> new Color(255, 255, 255, 0);
            case '/' -> new Color(255, 255, 255, 75);
            // .... fyll ut dine favorittfarger
            case '-' -> Color.BLACK;
            default -> throw new IllegalArgumentException(
                    "No available color for '" + character + "'");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return new Color(0,47,108, 200);
    }

    @Override
    public Color getBackgroundColor() {
            return new Color(242,233,234, 200);
    }

    @Override
    public Color getTransparentColor() {
        return new Color(0, 0, 0, 128);
    }

    @Override
    public Color getNextAndHoldTetrominoBackground() {
        return Color.BLACK;
    }

    @Override
    public Color getTitleColor() {
        return Color.YELLOW;
    }

    @Override
    public Color getTextColor() {
        return Color.WHITE;
    }

    @Override
    public Color getPauseColor() {
        return Color.BLACK;
    }
}

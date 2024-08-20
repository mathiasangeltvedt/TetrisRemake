package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

/**
 * Denne klassen brukes for å tegne alt som skjer i spillet
 * Den tegner brettet ut ifra hvilken gamestate spillet er i, og etter hvilken verdier
 * den henter ut fra de forskjellige metodene i ViewableTetrisModel.
 * Denne klassen brukes reint for å tegne alt på skjermen.
 */
public class TetrisView extends JPanel {
    // Instansvariabler og klassevariabler
    ViewableTetrisModel model;
    ColorTheme theme;
    Image backgroundImage;
    private static final int normalTextSize = 15;
    private static final int titleTextSize = 30;
    private static final double INNERMARGIN = 1;
    private static final double HEIGHTMARGIN = 70;
    private static final double WIDTHMARGIN = 135;

    // Constructor
    public TetrisView(ViewableTetrisModel model) {
        try {
            this.backgroundImage = ImageIO.read(new File("src/main/java/no/uib/inf101/tetris/view/tetris.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.model = model;
        this.theme = new DefaultColorTheme();
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(500, 600));
    }

    // The paintComponent method is called by the Java Swing framework every time
    // either the window opens or resizes, or we call .repaint() on this object.
    // Note: NEVER call paintComponent directly yourself
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    /**
     * Hjelpemetode for å tegne selve brettet
     * Denne metoden brukes i paintComponent-metoden ved
     * å bruke et Graphics2D objekt
     * Metoden regner ut verdiene for brettet som skal tegnes
     * Den setter bakgrunnsfarge, og bruker drawCells-metoden for å tegne cellene på brettet
     * Metoden tegner også ting som:
     * - Antall levler
     * - Antall poengsum
     * - GameOver skjerm
     * - Welcome skjerm
     * - Controls skjerm
     * - Pause skjerm
     * @param tetrisBoard er et Graphics2D objekt som lages i paintComponent
     */
    private void drawGame(Graphics2D tetrisBoard) {
        double boardWidth = this.getWidth() - 2 * WIDTHMARGIN;
        double boardHeight = this.getHeight() - 2 * HEIGHTMARGIN;

        tetrisBoard.drawImage(backgroundImage, 0, 0, null);

        Rectangle2D boardBackground = new Rectangle2D.Double(WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getBackgroundColor());
        tetrisBoard.fill(boardBackground);

        CellPositionToPixelConverter convertFromPosToRectangle = new CellPositionToPixelConverter(boardBackground,
                model.getDimension(),
                INNERMARGIN);

        Rectangle2D nextTetrominoBackground = new Rectangle2D.Double(boardWidth + WIDTHMARGIN + 5,
                                                                HEIGHTMARGIN + 65, 125, 250);
        tetrisBoard.setColor(theme.getNextAndHoldTetrominoBackground());
        tetrisBoard.fill(nextTetrominoBackground);

        Rectangle2D holdTetrominoBackground = new Rectangle2D.Double(WIDTHMARGIN - 130,
                                                            HEIGHTMARGIN + 65, 125, 120);

        tetrisBoard.setColor(theme.getNextAndHoldTetrominoBackground());
        tetrisBoard.fill(holdTetrominoBackground);

        drawCells(tetrisBoard, model.getTilesOnBoard(), convertFromPosToRectangle, theme);
        drawCells(tetrisBoard, model.getNext1Tetro(), convertFromPosToRectangle, theme);
        drawCells(tetrisBoard, model.getNext2Tetro(), convertFromPosToRectangle, theme);
        drawCells(tetrisBoard, model.getNext3Tetro(), convertFromPosToRectangle, theme);
        drawCells(tetrisBoard, model.getHoldTetro(), convertFromPosToRectangle, theme);
        drawShadow(tetrisBoard, model.getShadowTetro(), convertFromPosToRectangle, theme);
        drawCells(tetrisBoard, model.getFallingTetro(), convertFromPosToRectangle, theme);

        // GRAFIKK FOR TITTEL
        drawText(tetrisBoard, "TETRIS", titleTextSize, (this.getWidth() / 2.0) - 50, 35, 100, 10, theme.getTitleColor());
        // GRAFIKK FOR NESTE TETROMINO
        drawText(tetrisBoard, "NEXT", titleTextSize, boardWidth + WIDTHMARGIN + 20, HEIGHTMARGIN - 10, WIDTHMARGIN / 1.5, 80, theme.getTextColor());
        // GRAFIKK FOR TETROMINO PÅ HOLD
        drawText(tetrisBoard, "HOLD", titleTextSize, 20, HEIGHTMARGIN - 10, WIDTHMARGIN / 1.5 , 80, theme.getTextColor());
        // GRAFIKK FOR SCORE
        drawText(tetrisBoard, "Score: " + model.getCurrentScore(), normalTextSize, WIDTHMARGIN - 80, boardHeight + HEIGHTMARGIN, 100, 70, theme.getTextColor());
        // GRAFIKK FOR LEVEL
        drawText(tetrisBoard, "Level: " + model.getGameLevel(), normalTextSize, WIDTHMARGIN + boardWidth - 20, boardHeight + HEIGHTMARGIN, 100, 70, theme.getTextColor());

        if (model.getGameState() == GameState.GAME_OVER) {
            drawGameOver(tetrisBoard, boardWidth, boardHeight);
        }

        else if (model.getGameState() == GameState.WELCOME) {
            drawWelcome(tetrisBoard, boardWidth, boardHeight);
        }

        else if (model.getGameState() == GameState.CONTROLS) {
            drawControls(tetrisBoard, boardWidth, boardHeight);
        }

        else if (model.getGameState() == GameState.PAUSED) {
            drawPaused(tetrisBoard, boardWidth, boardHeight);
        }
    }

    /**
     * Denne hjelpemetoden brukes for flere ting:
     * Den brukes for å regne ut hvor på brettet rutene skal tegnes
     * Den brukes for å finne ut hvilken farge rutene på brettet skal ha
     * Den brukes for å tegne rutene på brettet
     * @param grid er lerret rutene skal tegnes på
     * @param cells er en samling av rutene som skal tegnes
     * @param converter er en slags kalkulator for å regne ut hvor cellen skal tegnes
     * @param color variabel for å bestemme fargen på ruten som skal tegnes
     */
    private static void drawCells(Graphics2D grid,
                                  Iterable<GridCell<Character>> cells,
                                  CellPositionToPixelConverter converter,
                                  ColorTheme color) {

        for (GridCell<Character> cell : cells) {
            Rectangle2D cellToBeDrawn = converter.getBoundsForCell(cell.pos());
            grid.setColor(color.getCellColor(cell.value()));
            grid.fill(cellToBeDrawn);
        }
    }

    /**
     * Denne metoden fungerer akkurat som den over, bare at den er kun til bruk
     * for å tegne skyggen og ingenting annet.
     * @param grid er brettet
     * @param cells er cellen til skyggen
     * @param converter en "kalkulator" som skal brukes for å regne ut posisjonen til skyggen
     * @param color er en tema-variabel som brukes for å hente ut fargene vi skal bruke
     */
    private static void drawShadow(Graphics2D grid,
                                  Iterable<GridCell<Character>> cells,
                                  CellPositionToPixelConverter converter,
                                  ColorTheme color) {

        for (GridCell<Character> cell : cells) {
            Rectangle2D cellToBeDrawn = converter.getBoundsForCell(cell.pos());
            grid.setColor(color.getCellColor('/'));
            grid.fill(cellToBeDrawn);
        }
    }

    /**
     * Tegner brettet når spilleren er på welcome screen
     * @param tetrisBoard brettet
     * @param boardWidth bredden på brettet
     * @param boardHeight høyden på brettet
     */
    private void drawWelcome(Graphics2D tetrisBoard, double boardWidth, double boardHeight) {
        Rectangle2D welcomeScreen = new Rectangle2D.Double(WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTransparentColor());
        tetrisBoard.fill(welcomeScreen);

        tetrisBoard.setColor(theme.getTitleColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, titleTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "TETRIS", WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTextColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, normalTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "press key down to begin", WIDTHMARGIN, HEIGHTMARGIN + 50, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTextColor());
        Inf101Graphics.drawCenteredString(tetrisBoard, "press c to see the controls", WIDTHMARGIN, HEIGHTMARGIN + 75, boardWidth, boardHeight);
    }

    /**
     * Tegner brettet når spilleren er på gameover screen
     * @param tetrisBoard brettet
     * @param boardWidth bredden på brettet
     * @param boardHeight høyden på brettet
     */
    private void drawGameOver(Graphics2D tetrisBoard, double boardWidth, double boardHeight) {
        Rectangle2D gameOverBackround = new Rectangle2D.Double(WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTransparentColor());
        tetrisBoard.fill(gameOverBackround);

        tetrisBoard.setColor(theme.getTitleColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, titleTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "GAME OVER", WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTextColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, normalTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "Your score: " + model.getCurrentScore(), WIDTHMARGIN, HEIGHTMARGIN + 50, boardWidth, boardHeight);
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, normalTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "press enter to restart", WIDTHMARGIN, HEIGHTMARGIN + 80, boardWidth, boardHeight);
    }

    /**
     * Tegner brettet når spilleren er i kontrollene
     * @param tetrisBoard brettet
     * @param boardWidth bredden på brettet
     * @param boardHeight høyden på brettet
     */
    private void drawControls(Graphics2D tetrisBoard, double boardWidth, double boardHeight) {
        Rectangle2D controlsScreen = new Rectangle2D.Double(WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTransparentColor());
        tetrisBoard.fill(controlsScreen);

        tetrisBoard.setColor(theme.getTitleColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, titleTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "Controls:", WIDTHMARGIN, HEIGHTMARGIN - 100, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getTextColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, normalTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "Arrow left --> move left", WIDTHMARGIN, HEIGHTMARGIN - 50, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "Arrow right --> move right", WIDTHMARGIN, HEIGHTMARGIN - 25, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "Arrow down --> move down", WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "Arrow up --> rotate tetro", WIDTHMARGIN, HEIGHTMARGIN + 25, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "Spacebar --> drop tetro", WIDTHMARGIN, HEIGHTMARGIN + 50, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "C --> hold tetro", WIDTHMARGIN, HEIGHTMARGIN + 75, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "P --> pause/unpause game", WIDTHMARGIN, HEIGHTMARGIN + 100, boardWidth, boardHeight);
        Inf101Graphics.drawCenteredString(tetrisBoard, "press escape to go back", WIDTHMARGIN, HEIGHTMARGIN + 135, boardWidth, boardHeight);
    }

    /**
     * Tegner brettet når spilleren har satt spillet på pause
     * @param tetrisBoard brettet
     * @param boardWidth bredden på brettet
     * @param boardHeight høyden på brettet
     */
    private void drawPaused(Graphics2D tetrisBoard, double boardWidth, double boardHeight) {
        Rectangle2D controlsScreen = new Rectangle2D.Double(WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
        tetrisBoard.setColor(theme.getPauseColor());
        tetrisBoard.fill(controlsScreen);
        tetrisBoard.setColor(theme.getTitleColor());
        tetrisBoard.setFont(new Font("Arial", Font.BOLD, titleTextSize));
        Inf101Graphics.drawCenteredString(tetrisBoard, "PAUSED", WIDTHMARGIN, HEIGHTMARGIN, boardWidth, boardHeight);
    }

    /**
     * Denne metoden skriver tekst på skjermen ved hjelp av drawCenteredString metoden i INF101Graphics
     * @param board er brettet teksten tegnes på
     * @param text er teksten som skal skrives
     * @param textSize er størrelsen på teksten som skal skrives
     * @param x er x verdien øvre venstre hjørne på firkanten som går rundt den sentrerte teksten
     * @param y er y verdien til øvre venstre hjørne på firkanten som går rundt den sentrerte teksten
     * @param width er bredden på firkanten som går rundt den sentrerte teksten
     * @param height er høden på firkanten som går rundt den sentrerte teksten
     * @param color er fargen på teksten som skal skrives
     */
    private static void drawText(Graphics2D board, String text, int textSize, double x, double y, double width, double height, Color color) {
        board.setColor(color);
        board.setFont(new Font("Arial", Font.BOLD, textSize));
        Inf101Graphics.drawCenteredString(board, text, x, y, width, height);
    }
}

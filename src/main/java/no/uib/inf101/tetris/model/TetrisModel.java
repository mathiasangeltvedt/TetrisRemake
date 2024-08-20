package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

/**
 * Denne klassen representerer selve modellen til spillet. Her brukes alle de forskjellige klassene
 * til å opprette et nytt spill. Og det er i modellen alt som faktisk skal skje i spillet, blir implementert
 * gjennom bruken av andre klasser. Denne klassen implementerer ViewableTetrisModel og ControllableTetrisModel
 * ettersom TetrisView skal kunne hente informasjon herfra for å tegne brettet og det som skjer på brettet, mens
 * kontrollen skal kunne hente informasjon om hva som skal skje ved de forskjellige tastetrykkene.
 */
public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

    // Instansvariabler
    TetrisBoard tetrisBoard;
    TetrominoFactory tetrominoMaker;
    Tetromino mainTetromino;
    Tetromino shadowTetromino;
    Tetromino next1Tetromino;
    Tetromino next2Tetromino;
    Tetromino next3Tetromino;
    Tetromino holdTetromino;
    GameState gameState;
    int gameLevel;
    int score;

    // Instansvariabler brukt som "hjelpevariabler" i metoder
    int rowsRemoved;
    int overallHoldCounter;
    int currentTetroHoldCounter;
    int rotateCounter;
    int stickToBoardCounter;

    public TetrisModel(TetrisBoard tetrisBoard, TetrominoFactory tetrominoMaker) {
        this.tetrisBoard = tetrisBoard;
        this.tetrominoMaker = tetrominoMaker;
        this.mainTetromino = tetrominoMaker.getNext();
        this.mainTetromino = this.mainTetromino.shiftedToTopCenterOf(this.tetrisBoard);
        this.shadowTetromino = this.mainTetromino;
        dropShadow();
        this.next1Tetromino = tetrominoMaker.getNext();
        this.next1Tetromino = this.next1Tetromino.shiftedBy(3, 11);
        this.next2Tetromino = tetrominoMaker.getNext();
        this.next2Tetromino = this.next2Tetromino.shiftedBy(6, 11);
        this.next3Tetromino = tetrominoMaker.getNext();
        this.next3Tetromino = this.next3Tetromino.shiftedBy(9, 11);
        this.holdTetromino = tetrominoMaker.getStartHoldTetromino();
        this.holdTetromino = this.holdTetromino.shiftedBy(4, -8);
        this.gameState = GameState.WELCOME;
        this.gameLevel = 1;
        this.score = 0;
        this.rowsRemoved = 0;
        this.overallHoldCounter = 0;
        this.rotateCounter = 0;
        this.stickToBoardCounter = 0;
        this.currentTetroHoldCounter = 0;
    }

    @Override
    public GridDimension getDimension() {
        return this.tetrisBoard;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return this.tetrisBoard;
    }

    @Override
    public Iterable<GridCell<Character>> getFallingTetro() {
        return this.mainTetromino;
    }

    @Override
    public Iterable<GridCell<Character>> getShadowTetro() {
        return this.shadowTetromino;
    }

    @Override
    public Iterable<GridCell<Character>> getNext1Tetro() {
        return this.next1Tetromino;
    }

    @Override
    public Iterable<GridCell<Character>> getNext2Tetro() {
        return this.next2Tetromino;
    }

    @Override
    public Iterable<GridCell<Character>> getNext3Tetro() {
        return this.next3Tetromino;
    }

    @Override
    public Iterable<GridCell<Character>> getHoldTetro() {
        return this.holdTetromino;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public int getGameLevel() {
        return this.gameLevel;
    }

    @Override
    public int getCurrentScore() {
        return this.score;
    }

    @Override
    public void restartGame() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new RandomTetrominoFactory();
        this.tetrisBoard = board;
        this.tetrominoMaker = factory;
        this.mainTetromino = factory.getNext();
        this.mainTetromino = this.mainTetromino.shiftedToTopCenterOf(this.tetrisBoard);
        this.shadowTetromino = this.mainTetromino;
        dropShadow();
        this.next1Tetromino = factory.getNext();
        this.next1Tetromino = this.next1Tetromino.shiftedBy(3, 11);
        this.next2Tetromino = tetrominoMaker.getNext();
        this.next2Tetromino = this.next2Tetromino.shiftedBy(6, 11);
        this.next3Tetromino = tetrominoMaker.getNext();
        this.next3Tetromino = this.next3Tetromino.shiftedBy(9, 11);
        this.holdTetromino = factory.getStartHoldTetromino();
        this.holdTetromino = this.holdTetromino.shiftedBy(4, -8);
        this.gameState = GameState.WELCOME;
        this.gameLevel = 1;
        this.score = 0;
        this.rowsRemoved = 0;
        this.overallHoldCounter = 0;
        this.rotateCounter = 0;
        this.stickToBoardCounter = 0;
        this.currentTetroHoldCounter = 0;
    }

    @Override
    public Integer amountOfmsBetweenEachTick() {
        return 1050 - this.gameLevel * 50;
    }

    @Override
    public void clockTick() {
        if (!this.moveTetromino(1, 0)) {
            stickTetroToBoard();
        }
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        Tetromino possibleNewTetro = this.mainTetromino.shiftedBy(deltaRow, deltaCol);
        if (isTetroAtLegalPos(possibleNewTetro)) {
            this.mainTetromino = possibleNewTetro;
            return true;
        }
        return false;
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino possibleNewTetro = this.mainTetromino.rotateTetro();
        if (isTetroAtLegalPos(possibleNewTetro)) {
            rotateCounter += 1;
            this.mainTetromino = possibleNewTetro;
            return true;
        }
        return false;
    }

    @Override
    public boolean holdTetromino() {
        if (currentTetroHoldCounter <= stickToBoardCounter) {
            currentTetroHoldCounter += 1;
            if (this.overallHoldCounter == 0) {
                overallHoldCounter += 1;
                int tetroHoldRotation = 4 - (this.rotateCounter % 4);
                while (tetroHoldRotation != 0) {
                    this.mainTetromino = mainTetromino.rotateTetro();
                    tetroHoldRotation -= 1;
                }
                this.holdTetromino = this.mainTetromino;
                this.rotateCounter = 0;
                this.holdTetromino = this.holdTetromino.shiftedToTopCenterOf(tetrisBoard);
                this.holdTetromino = this.holdTetromino.shiftedBy(4, -8);
                getNewTetro();
            } else {
                overallHoldCounter += 1;
                Tetromino holder = this.holdTetromino;
                int tetroHoldRotation = 4 - (this.rotateCounter % 4);
                while (tetroHoldRotation != 0) {
                    this.mainTetromino = mainTetromino.rotateTetro();
                    tetroHoldRotation -= 1;
                }
                this.holdTetromino = this.mainTetromino;
                this.rotateCounter = 0;
                this.holdTetromino = this.holdTetromino.shiftedToTopCenterOf(tetrisBoard);
                this.holdTetromino = this.holdTetromino.shiftedBy(4, -8);
                this.mainTetromino = holder.shiftedToTopCenterOf(tetrisBoard);
            }
            return true;
        }
        return false;
    }

    /**
     * HJELPEMETODE
     * Denne metoden skal brukes for å sjekke om en gitt Tetromino er innenfor
     * brettet og at den ikke prøver å gå på en posisjon som allerede er
     * okkupert av en annen Tetromino.
     * @param tetroToBeTested er Tetrominoen som skal sjekkes
     * @return enten en true eller false avhengig av om brikken er innenfor eller ikke
     */
    private boolean isTetroAtLegalPos(Tetromino tetroToBeTested) {
        for (GridCell<Character> gridCell : tetroToBeTested) {
            CellPosition posToBeChecked = gridCell.pos();
            if (!tetrisBoard.positionIsOnGrid(posToBeChecked)) {
                return false;
            }
            if (!tetrisBoard.get(posToBeChecked).equals('-') && !tetrisBoard.get(posToBeChecked).equals('/')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void dropTetromino() {
        while (moveTetromino(1, 0)) {
            moveTetromino(1, 0);
        }
        stickTetroToBoard();
    }

    @Override
    public void moveShadowTetrominoToTetroPos() {
        this.shadowTetromino = this.mainTetromino;
        dropShadow();
    }

    /**
     * HJELPEMETODE
     * Denne hjelpemetoden brukes i moveShadowTetrominoToTetroPos
     * for å gjøre slik at skyggen alltid er på bunnen av brettet
     * Så lenge skyggen kan flyttes nedover så skal den flyttes nedover
     */
    private void dropShadow() {
        while (moveShadow()) {
            moveShadow();
        }
    }

    /**
     * HJELPEMETODE
     * Denne metoden skal gjøre egentlig akkurat det samme som moveTetromino, bare
     * at denne er privat ettersom vi kun skal bruke den som hjelpemetode for å droppe
     * skyggen ned på bunnen av brettet.
     * @return en boolean verdi basert på om den flyttes eller ikke
     */
    private boolean moveShadow() {
        Tetromino possibleNewTetro = this.shadowTetromino.shiftedBy(1, 0);
        if (isTetroAtLegalPos(possibleNewTetro)) {
            this.shadowTetromino = possibleNewTetro;
            return true;
        }
        return false;
    }

    /**
     * HJELPEMETODE
     * Hente ut en ny fallende tetromino. Hente ut en ny brikke og oppdatere instansvariabelen for den fallende brikken
     * Den skal også oppdatere instansvariablene for de neste brikkene som skal komme dersom spillet
     * ikke går i gameover
     */
    private void getNewTetro() {
        stickToBoardCounter = 0;
        TetrominoFactory tetroFactory = new RandomTetrominoFactory();
        if (!isTetroAtLegalPos(this.next1Tetromino.shiftedToTopCenterOf(tetrisBoard))) {
            this.gameState = GameState.GAME_OVER;
        }
        else {
            this.mainTetromino = this.next1Tetromino.shiftedToTopCenterOf(tetrisBoard);
            this.next1Tetromino = this.next2Tetromino.shiftedBy(-3,0);
            this.next2Tetromino = this.next3Tetromino.shiftedBy(-3,0);
            this.next3Tetromino = tetroFactory.getNext();
            this.next3Tetromino = this.next3Tetromino.shiftedBy(9, 11);
            this.rotateCounter = 0;
        }
    }

    /**
     * HJELPEMETODE
     * Lime fast den fallende tetrominoen til brettet.
     * Iterer over koordinatene til tetrominoen, og oppdater verdiene på brettet i disse posisjonene
     * På slutten av metoden, gjør et kall til metoden som henter en ny brikke (getNewTetro).
     * Denne hjelpemetoden blir også brukt for å oppdatere levelet i spillet, og også for å
     * oppdatere den nåværende poengsummen i spillet.
     */
    private void stickTetroToBoard() {
        currentTetroHoldCounter = 0;
        for (GridCell<Character> gCell : this.mainTetromino) {
            CellPosition posToBeUpdated = gCell.pos();
            this.tetrisBoard.set(posToBeUpdated, gCell.value());
        }

        int rowsToBeRemoved = tetrisBoard.removeFullRows();
        rowsRemoved += rowsToBeRemoved;

        if (rowsToBeRemoved == 1) {
            this.score += 100 * this.gameLevel;
        }
        else if (rowsToBeRemoved == 2) {
            this.score += 300 * this.gameLevel;
        }
        else if (rowsToBeRemoved == 3) {
            this.score += 500 * this.gameLevel;
        }
        else if (rowsToBeRemoved >= 4){
            this.score += 800 * this.gameLevel;
        }
        while (rowsRemoved >= 5) {
            rowsRemoved -= 5;
            this.gameLevel += 1;
        }
        getNewTetro();
    }
}

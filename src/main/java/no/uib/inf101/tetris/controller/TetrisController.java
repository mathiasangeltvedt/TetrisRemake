package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Denne klassen representerer kontrollen til spilleren som
 * skal spille tetris. Her styres alt av tastetrykk, og timere som bestemmer
 * når ting skal bevege seg osv. Klassen implementerer KeyListener slik at man
 * kan sjekke når et tastetrykk er gjort.
 */
public class TetrisController implements java.awt.event.KeyListener{

    // Instansvariabler
    ControllableTetrisModel controllableTetrisModel;
    TetrisView tetrisView;
    Timer timer;
    TetrisSong tetrisSong;

    public TetrisController(ControllableTetrisModel controllableTetrisModel, TetrisView tetrisView) {
        this.controllableTetrisModel = controllableTetrisModel;
        this.tetrisView = tetrisView;
        tetrisSong = new TetrisSong();
        tetrisView.setFocusable(true);
        tetrisView.addKeyListener(this);
        this.timer = new Timer(controllableTetrisModel.amountOfmsBetweenEachTick(), this::clockTick);
        timer.start();
        tetrisSong.run();
    }

    /**
     * I denne metoden gjør vi slik at brikken faktisk
     * går en og en rad nedover så lenge spillet er i
     * ACTIVE_GAME gamestate.
     * @param actionEvent er en ActionEvent type som blir brukt for at noe faktisk skal skje i spillet
     */
    public void clockTick(ActionEvent actionEvent) {
        if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            controllableTetrisModel.clockTick();
            delaySetter();
            tetrisView.repaint();
        }
    }

    /**
     * Denne metoden brukes kun for å sette delay-verdiene
     * på timer-objektet vi har
     */
    private void delaySetter() {
        int delay = controllableTetrisModel.amountOfmsBetweenEachTick();
        timer.setDelay(delay);
        timer.setInitialDelay(delay);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (controllableTetrisModel.getGameState() == GameState.WELCOME) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                controllableTetrisModel.setGameState(GameState.ACTIVE_GAME);
            }
            else if (e.getKeyCode() == KeyEvent.VK_C) {
                controllableTetrisModel.setGameState(GameState.CONTROLS);
            }
            tetrisView.repaint();
        }

        else if (controllableTetrisModel.getGameState() == GameState.CONTROLS) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                controllableTetrisModel.setGameState(GameState.WELCOME);
            }
        }

        else if (controllableTetrisModel.getGameState() == GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                controllableTetrisModel.restartGame();
            }
            tetrisView.repaint();
        }

        else if (controllableTetrisModel.getGameState() == GameState.PAUSED) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                controllableTetrisModel.setGameState(GameState.ACTIVE_GAME);
            }
        }

        else if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                // Left arrow was pressed
                if (controllableTetrisModel.moveTetromino(0, -1)) {
                    controllableTetrisModel.moveShadowTetrominoToTetroPos();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                // Right arrow was pressed
                if (controllableTetrisModel.moveTetromino(0, 1)) {
                    controllableTetrisModel.moveShadowTetrominoToTetroPos();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                // Down arrow was pressed
                if (controllableTetrisModel.moveTetromino(1, 0)) {
                    timer.restart();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                // Up arrow was pressed
                if (controllableTetrisModel.rotateTetromino()) {
                    controllableTetrisModel.moveShadowTetrominoToTetroPos();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                // Spacebar was pressed
                controllableTetrisModel.dropTetromino();
                controllableTetrisModel.moveShadowTetrominoToTetroPos();
                timer.restart();
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                controllableTetrisModel.setGameState(GameState.PAUSED);
            } else if (e.getKeyCode() == KeyEvent.VK_C) {
                if (controllableTetrisModel.holdTetromino()) {
                    controllableTetrisModel.moveShadowTetrominoToTetroPos();
                }
            }
            tetrisView.repaint();
        }

        else {
            tetrisView.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

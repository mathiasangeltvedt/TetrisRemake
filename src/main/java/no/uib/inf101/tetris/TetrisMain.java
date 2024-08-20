package no.uib.inf101.tetris;

import no.uib.inf101.tetris.controller.TetrisController;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.TetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.TetrisView;

import javax.swing.*;

public class TetrisMain {
  public static final String WINDOW_TITLE = "INF101 Tetris";

  public static void main(String[] args) {

    TetrisBoard board = new TetrisBoard(20, 10);

    TetrominoFactory factory = new RandomTetrominoFactory();
    TetrisModel model = new TetrisModel(board, factory);
    TetrisView view = new TetrisView(model);
    TetrisController controller = new TetrisController(model, view);

    // The JFrame is the "root" application window.
    // We here set som properties of the main window,
    // and tell it to display our tetrisView
    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Here we set which component to view in our window
    frame.setContentPane(view);
    
    // Call these methods to actually display the window
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
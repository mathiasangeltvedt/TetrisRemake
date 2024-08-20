## Overview of Architecture

Here, we will first provide a bird's-eye view of the code we are going to create; in the guide linked below, the creation of these classes will be done in more detail.

We base our model on the design principle of Model-View-Controller, where we aim as much as possible to separate:

    the logic and data structures in the model,
    how the models are displayed, and
    how the model is controlled by the user.

In this guide, we also divide our code into three main packages:

    inf101.tetris.model
    inf101.tetris.view
    inf101.tetris.controller

Additionally, we will use a data structure for grids that is more general and will be in the package:

    inf101.grid

Everything is run from TetrisMain in the package inf101.tetris, which contains the main method. For now, it only shows a simple GUI with some examples of random shapes, but when we’re finished, we’ll start Tetris from the main method here.

## Model

To represent a game of Tetris, there are two main elements we need to keep track of:

    a board with tiles, and
    a falling piece.

We identify the following classes as suitable for the inf101.tetris.model package:

    TetrisModel, a class that represents the state of a complete Tetris game. This class will have field variables representing the board with tiles and the falling piece, as well as information on whether the game is over.
    TetrisBoard, a class that represents a board with tiles. This is essentially a grid.
    Tetromino, a class that represents a Tetris piece.

The model is in many ways the most important part of the code, and we want the code in the model to be well-tested.

## View

To display the model graphically, we will have a class TetrisView in the inf101.tetris.view package, which is responsible for drawing the Tetris model. To draw Tetris, the plan for TetrisView is to:

    first draw the Tetris board, and
    then draw the falling piece "on top" of the board.

TetrisView needs to have access to the model to draw it, but we want to avoid unintentionally changing the model when doing things in TetrisView. To encapsulate our model, we will create an interface ViewableTetrisModel in the inf101.tetris.view package, which describes the methods TetrisView needs to draw a Tetris board. The TetrisModel will then implement this interface. TetrisView will never actually know that it’s working with a TetrisModel; it only knows that it’s a ViewableTetrisModel.

## Control

In the inf101.tetris.controller package, we will have a class TetrisController responsible for modifying the model based on user input, as well as controlling things that happen automatically (such as the piece falling down one step at regular intervals).

As with the view, the controller depends on access to the model. At the same time, we want to encapsulate the model as much as possible. Therefore, we will create an interface ControllableTetrisModel in the inf101.tetris.controller package, which describes the methods the controller needs access to, and then let our model TetrisModel implement this interface.

## Copyright

Tetris is a game where The Tetris Company holds all rights. Therefore, you cannot publish your version of Tetris publicly when you are done; your Tetris game is only for you and your closest friends, and we are recreating it solely for educational purposes.
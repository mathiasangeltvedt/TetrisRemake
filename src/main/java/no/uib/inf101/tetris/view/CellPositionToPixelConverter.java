package no.uib.inf101.tetris.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverter {

    // Instansvariabler
    Rectangle2D box;
    GridDimension gd;
    double margin;

    public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
        this.box = box;
        this.gd = gd;
        this.margin = margin;
    }

    public Rectangle2D getBoundsForCell(CellPosition pos) {
        int row = pos.row();
        int col = pos.col();

        double marginX = this.margin*(gd.cols() + 1);
        double widthLeftForCellsX = box.getWidth() - marginX;
        double cellWidth = widthLeftForCellsX / gd.cols();

        double cellX = box.getX() + (this.margin * (col + 1)) + cellWidth * col;

        double marginY = this.margin * (gd.rows() + 1);
        double heightLeftForCellsY = box.getHeight() - marginY;
        double cellHeight = heightLeftForCellsY / gd.rows();

        double cellY = box.getY() + (this.margin * (row + 1)) + cellHeight * row;

        return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
    }

}

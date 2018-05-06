/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Steven
 * @author Eyleen
 */
public class DrawGrid {

    private int tempX = 0;
    private int tempY = 0;
    private int x = 0;
    private int y = 0;

    public int[] drawGrid1(GraphicsContext gc, int dimension, double xp, double yp) {
        gc.setStroke(Color.CYAN);
        int[] cont = new int[2];
        double i;
        double j;

        if (xp > yp) {
            for (i = 0; i < xp; i++) {
                if (this.tempX >= xp || (this.tempX + dimension) > xp) {
                    i = xp;
                    break;
                }
                cont[1] = 0;
                for (j = 0; j < yp; j++) {
                    gc.strokeRect(x + i * dimension, y + j * dimension, dimension, dimension);
                    this.tempY += dimension;

                    if (this.tempY >= yp || (this.tempY + dimension) > yp) {
                        j = yp;
                    }

                    cont[1] = cont[1] + dimension;
                }
                cont[0] = cont[0] + dimension;
                this.tempY = 0;
                this.tempX += dimension;
            }
        } else {
            for (i = 0; i < yp; i++) {
                if (this.tempY >= yp || (this.tempY + dimension) > yp) {
                    i = yp;
                    break;
                }
                cont[0] = 0;
                for (j = 0; j < xp; j++) {
                    gc.strokeRect(y + j * dimension, x + i * dimension, dimension, dimension);
                    this.tempX += dimension;

                    if (this.tempX >= xp || (this.tempX + dimension) > xp) {
                        j = xp;
                    }

                    cont[0] = cont[0] + dimension;
                }
                cont[1] = cont[1] + dimension;
                this.tempX = 0;
                this.tempY += dimension;
            }
        }
        this.tempX = 0;
        this.tempY = 0;
        this.x = 0;
        this.y = 0;
        return cont;
    }

    public int drawGrid2(GraphicsContext gc, int dimension, int size, int j, int i) {
        gc.setStroke(Color.CYAN);
        if (i * dimension >= size || (i * dimension) + dimension > size) {
            int aux = this.tempX;
            this.tempY = 0;
            this.tempX = 0;
            this.x = 0;
            this.y = 0;
            return aux;
        } else {
            if (this.tempY <= size) {
                gc.strokeRect(i * dimension, j * dimension, dimension, dimension);
                this.tempY += dimension;
                return drawGrid2(gc, dimension, size, j + 1, i);
            } else {
                this.tempY = 0;
                this.tempX += dimension;
                return drawGrid2(gc, dimension, size, 0, i + 1);
            }
        }
    }

    public int drawGrid3(GraphicsContext gc, int dimension, int size, int j, int i, boolean[][] trueFalse) {
        if (i * dimension >= size || (i * dimension) + dimension > size) {
            int aux = this.tempX;
            this.tempY = 0;
            this.tempX = 0;
            return aux;
        } else {
            if (this.tempY <= size) {
                if (!trueFalse[i][j]) {
                    gc.clearRect(i * dimension, j * dimension, dimension, dimension);
                }
                this.tempY += dimension;
                return drawGrid3(gc, dimension, size, j + 1, i, trueFalse);
            } else {
                this.tempY = 0;
                this.tempX += dimension;
                return drawGrid3(gc, dimension, size, 0, i + 1, trueFalse);
            }
        }
    }
}

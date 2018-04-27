/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Steven
 * @author Eyleen
 */
public class DrawGrid {

    
    public int[] drawGrid1(GraphicsContext gc, int dimension, double xp, double yp) {
        gc.setStroke(Color.CYAN);
        int[] cont = new int[2];
        int tempx = 0;
        int tempy = 0;
        int x = 0;
        int y = 0;
        double i;
        double j;

        if (xp > yp) {
            for (i = 0; i < xp; i++) {
                if (tempx >= xp || (tempx + dimension) > xp) {
                    i = xp;
                    break;
                }
                cont[1] = 0;
                for (j = 0; j < yp; j++) {
                    gc.strokeRect(x + i * dimension, y + j * dimension, dimension, dimension);
                    tempy += dimension;

                    if (tempy >= yp || (tempy + dimension) > yp) {
                        j = yp;
                    }

                    cont[1] = cont[1] + dimension;
                }
                cont[0] = cont[0] + dimension;
                tempy = 0;
                tempx += dimension;
            }
        } else {
            for (i = 0; i < yp; i++) {
                if (tempy >= yp || (tempy + dimension) > yp) {
                    i = yp;
                    break;
                }
                cont[0] = 0;
                for (j = 0; j < xp; j++) {
                    gc.strokeRect(y + j * dimension, x + i * dimension, dimension, dimension);
                    tempx += dimension;

                    if (tempx >= xp || (tempx + dimension) > xp) {
                        j = xp;
                    }

                    cont[0] = cont[0] + dimension;
                }
                cont[1] = cont[1] + dimension;
                tempx = 0;
                tempy += dimension;
            }
        }
        return cont;
    }

    public int [] drawGrid2(GraphicsContext gc, int dimension, int size) {
        gc.setStroke(Color.CYAN);
        int[] cont = new int[2];
        int tempx = 0;
        int tempy = 0;
        int x = 0;
        int y = 0;
        double i;
        double j;

        for (i = 0; i < size; i++) {
            if (tempx >= size) {
                i = size;
                break;
            }

            cont[1] = 0;
            for (j = 0; j < size; j++) {
                gc.strokeRect(x + i * dimension, y + j * dimension, dimension, dimension);
                tempy += dimension;

                if (tempy >= size) {
                    j = size;
                }
                cont[1] = cont[1] + dimension;
            }
            cont[0] = cont[0] + dimension;
            tempy = 0;
            tempx += dimension;
        }
        return cont;
    }
}


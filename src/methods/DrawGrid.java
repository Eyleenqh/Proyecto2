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

    //Dibuja una cuadricula que dividira la imagen
    public int[] drawGrid1(GraphicsContext gc, int pixels, double xp, double yp) {
        gc.setStroke(Color.CYAN);
        int[] cont = new int[2];
        double i;
        double j;

        if (xp > yp) {
            //recorre las columnas de la cuadricula
            for (i = 0; i < xp; i++) {
                //cuando se cumpla la condicion de este if es porque se ha completado la cuadricula y se saldra del ciclo
                if (this.tempX >= xp || (this.tempX + pixels) > xp) {
                    i = xp;
                    break;
                }
                cont[1] = 0;
                //recorre las filas de la cuadricula
                for (j = 0; j < yp; j++) {
                    //dibuja bloque por bloque la cuadricula
                    gc.strokeRect(x + i * pixels, y + j * pixels, pixels, pixels);
                    //aumenta tempY para seguir dibujando en la siguiente fila
                    this.tempY += pixels;

                    //valida que se ha completado la columna de la cuadricula
                    if (this.tempY >= yp || (this.tempY + pixels) > yp) {
                        j = yp;
                    }

                    cont[1] = cont[1] + pixels;
                }
                cont[0] = cont[0] + pixels;
                this.tempY = 0;
                //aumenta tempX para seguir dibujando en la siguiente columna
                this.tempX += pixels;
            }
        } else {
            //recorre las filas de la cuadricula
            for (i = 0; i < yp; i++) {
                //cuando se cumpla la condicion de este if es porque se ha completado la cuadricula y se saldra del ciclo
                if (this.tempY >= yp || (this.tempY + pixels) > yp) {
                    i = yp;
                    break;
                }
                cont[0] = 0;
                //recorre las columnas de la cuadricula
                for (j = 0; j < xp; j++) {
                    //dibuja bloque por bloque la cuadricula
                    gc.strokeRect(y + j * pixels, x + i * pixels, pixels, pixels);
                    //aumenta tempX para seguir dibujando en la siguiente columna
                    this.tempX += pixels;

                    //valida que se ha completado la fila de la cuadricula
                    if (this.tempX >= xp || (this.tempX + pixels) > xp) {
                        j = xp;
                    }

                    cont[0] = cont[0] + pixels;
                }
                cont[1] = cont[1] + pixels;
                this.tempX = 0;
                //aumenta tempY para seguir dibujando en la siguiente fila
                this.tempY += pixels;
            }
        }
        this.tempX = 0;
        this.tempY = 0;
        this.x = 0;
        this.y = 0;
        //retorna el tamanio de la cuadricula
        return cont;
    }

    //metodo recursivo que dibuja una cuadricula sobre la que se hace el nuevo mosaico
    public int drawGrid2(GraphicsContext gc, int pixels, int size, int j, int i) {
        gc.setStroke(Color.CYAN);
        //caso base
        if (i * pixels >= size || (i * pixels) + pixels > size) {
            int aux = this.tempX;
            this.tempY = 0;
            this.tempX = 0;
            this.x = 0;
            this.y = 0;
            return aux;
        } else {
            //verifica que no se cambien de columna hasta que se hayan completado las lines
            if (this.tempY <= size) {
                //dibuja bloque por bloque la cuadricula
                gc.strokeRect(i * pixels, j * pixels, pixels, pixels);
                this.tempY += pixels;
                return drawGrid2(gc, pixels, size, j + 1, i);
            } else {
                this.tempY = 0;
                //aumenta tempX para pasar a crear la siguiente columna
                this.tempX += pixels;
                return drawGrid2(gc, pixels, size, 0, i + 1);
            }
        }
    }

    //metodo recursivo borrar los bloques que no contienen una imagen
    public int drawGrid3(GraphicsContext gc, int pixels, int size, int j, int i, boolean[][] trueFalse) {
        //caso base
        if (i * pixels >= size || (i * pixels) + pixels > size) {
            int aux = this.tempX;
            this.tempY = 0;
            this.tempX = 0;
            return aux;
        } else {
            //verifica que no se cambien de columna hasta que se hayan completado las lines
            if (this.tempY <= size) {
                //valida que si el valor de trueFalse en la posicion "i" y "j" sea false para borrar ese bloque
                if (!trueFalse[i][j]) {
                    gc.clearRect(i * pixels, j * pixels, pixels, pixels);
                }
                //si no, solo aumenta para seguir en la siguiente fila
                this.tempY += pixels;
                return drawGrid3(gc, pixels, size, j + 1, i, trueFalse);
            } else {
                this.tempY = 0;
                //aumenta tempX para pasar a verificar si es necesario borrar en la siguiente columna
                this.tempX += pixels;
                return drawGrid3(gc, pixels, size, 0, i + 1, trueFalse);
            }
        }
    }
}
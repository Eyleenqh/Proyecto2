/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 * @author Steven
 * @author Eyleen
 */
public abstract class Project implements Serializable, Cloneable {

    //atributos
    private byte[] image;
    private byte[] useImage;
    private int pixels;
    private int sizeMosaic;
    private boolean[][] logicMatrix;

    //constructores
    public Project() {
        this.image = null;
        this.useImage = null;
        this.pixels = 0;
        this.sizeMosaic = 0;
        this.logicMatrix = null;
    }

    public Project(byte[] image, byte[] useImage, int dimension, int sizeMosaic, boolean[][] logicMatrix) {
        this.image = image;
        this.useImage = useImage;
        this.pixels = dimension;
        this.sizeMosaic = sizeMosaic;
        this.logicMatrix = logicMatrix;
    }

    //metodo de clonado
    public Project clone() throws CloneNotSupportedException {
        return (Project) super.clone();
    }

    //metodos accesores
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getUseImage() {
        return useImage;
    }

    public void setUseImage(byte[] useImage) {
        this.useImage = useImage;
    }

    public int getPixels() {
        return pixels;
    }

    public void setPixels(int pixels) {
        this.pixels = pixels;
    }

    public int getSizeMosaic() {
        return sizeMosaic;
    }

    public void setSizeMosaic(int sizeMosaic) {
        this.sizeMosaic = sizeMosaic;
    }

    public boolean[][] getLogicMatrix() {
        return logicMatrix;
    }

    public void setLogicMatrix(boolean[][] logicMatrix) {
        this.logicMatrix = logicMatrix;
    }
}
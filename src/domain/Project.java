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
public class Project implements Serializable {

    private byte[] image;
    private byte[] useImage;
    private int dimension;
    private int sizeMosaic;

    public Project() {
    }

    public Project(byte[] image, byte[] useImage, int dimension, int sizeMosaic) {
        this.image = image;
        this.useImage = useImage;
        this.dimension = dimension;
        this.sizeMosaic = sizeMosaic;
    }

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

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getSizeMosaic() {
        return sizeMosaic;
    }

    public void setSizeMosaic(int sizeMosaic) {
        this.sizeMosaic = sizeMosaic;
    }

}

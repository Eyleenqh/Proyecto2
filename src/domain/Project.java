/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * @author Steven
 * @author Eyleen
 */
public class Project implements Serializable {
    
  //  private Image image;
  //  private Image useImage;
    private String image;
    private String useImage;
    private int dimension;
    private int sizeMosaic;

    public Project() {
        this.image = "";
        this.useImage = "";
        this.dimension = 0;
        this.sizeMosaic = 0;
    }

    public Project(String image, String useImage, int dimension, int sizeMosaic) {
        this.image = image;
        this.useImage = useImage;
        this.dimension = dimension;
        this.sizeMosaic = sizeMosaic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUseImage() {
        return useImage;
    }

    public void setUseImage(String useImage) {
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

    @Override
    public String toString() {
        return "Project{" + "image=" + image + ", useImage=" + useImage + ", dimension=" + dimension + ", sizeMosaic=" + sizeMosaic + '}';
    }
    
    
}

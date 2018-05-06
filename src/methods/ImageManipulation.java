/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import GUI.MainWindow;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 * @author Steven
 * @author Eyleen
 */
public class ImageManipulation {

    private Image imagePiece;
    private ImageView imageView; 
    private SnapshotParameters snapshot = new SnapshotParameters(); 

    public Image selectedImage(Image image, int dimension, int x, int y) {
        int cutX = (x / dimension) * dimension;
        int cutY = (y / dimension) * dimension;
        imageView = new ImageView(image);
        snapshot.setViewport(new Rectangle2D(cutX, cutY, dimension, dimension));
        return imageView.snapshot(snapshot, null);
    }

    public void pasteImage(GraphicsContext gc2, Image image, int dimension, int x, int y) {
        int pasteX = (x / dimension) * dimension;
        int pasteY = (y / dimension) * dimension;
        gc2.drawImage(image, pasteX, pasteY);
    }

    //Metodo que rota la imagen a la derecha
    public void rotateRight(Image im, int x, int y, int dimension, GraphicsContext gc2) {
        imageView = new ImageView(im);
        snapshot.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snapshot, null);
        imageView.setImage(im);
        imageView.setRotate(imageView.getRotate() + 90);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        gc2.drawImage(rotatedImage, x, y);

        imagePiece = null;
        imageView = null;
    }

    //Metodo que rota a la izquierda
    public void rotateLeft(Image im, int x, int y, int dimension, GraphicsContext gc2) {
        imageView = new ImageView(im);
        snapshot.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snapshot, null);
        imageView.setImage(im);
        imageView.setRotate(imageView.getRotate() - 90);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        gc2.drawImage(rotatedImage, x, y);

        imagePiece = null;
        imageView = null;
    }

    //Metodo que voltea verticalmente
    public void verticalFlip(Image im, int x, int y, int dimension, GraphicsContext gc2) {
        imageView = new ImageView(im);
        SnapshotParameters snap = new SnapshotParameters();
        snap.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snap, null);
        gc2.drawImage(im, 0, 0, dimension, dimension, x + dimension, y, -dimension, dimension);
    }

    //Metodo que voltea horizontalmente
    public void horizontalFlip(Image im, int x, int y, int dimension, GraphicsContext gc2) {
        imageView = new ImageView(im);
        SnapshotParameters snap = new SnapshotParameters();
        snap.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snap, null);
        gc2.drawImage(im, 0, 0, dimension, dimension, x, y + dimension, dimension, -dimension);
    }

    public void delete(GraphicsContext gc2, int x, int y, int dimension) {
        gc2.setFill(Color.WHITE);
        gc2.setStroke(Color.CYAN);
        gc2.fillRect(x, y, dimension, dimension);
        gc2.strokeRect(x, y, dimension, dimension);
    }
    
    public byte[] convertImage(Image image) {
        BufferedImage bytesImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytesArray = null;
        try {
            ImageIO.write(bytesImage, "png", baos);
            bytesArray = baos.toByteArray();
            baos.close();
            return bytesArray;
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

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

    //copia el cuadrado seleccionado de la imagen
    public Image selectedImage(Image image, int pixels, int x, int y) {
        int cutX = (x / pixels) * pixels;
        int cutY = (y / pixels) * pixels;
        imageView = new ImageView(image);
        snapshot.setViewport(new Rectangle2D(cutX, cutY, pixels, pixels));
        return imageView.snapshot(snapshot, null);
    }

    //coloca la imagen en la posicion dada por "pasteX" y "pasteY"
    public void pasteImage(GraphicsContext gc2, Image image, int pixels, int x, int y) {
        int pasteX = (x / pixels) * pixels;
        int pasteY = (y / pixels) * pixels;
        gc2.drawImage(image, pasteX, pasteY);
    }

    //Metodo que rota la imagen a la derecha
    public void rotateRight(Image im, int x, int y, int pixels, GraphicsContext gc2) {
        //permite que la imagen se pueda editar
        imageView = new ImageView(im);
        //selecciona el bloque que se va a rotar
        snapshot.setViewport(new Rectangle2D(x, y, pixels, pixels));
        im = imageView.snapshot(snapshot, null);
        imageView.setImage(im);
        //rota la imagen
        imageView.setRotate(imageView.getRotate() + 90);

        //coloca la imagen en el mismo lugar donde fue seleccionada
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        gc2.drawImage(rotatedImage, x, y);

        imagePiece = null;
        imageView = null;
    }

    //Metodo que rota a la izquierda
    public void rotateLeft(Image im, int x, int y, int pixels, GraphicsContext gc2) {
        //permite que la imagen se pueda editar
        imageView = new ImageView(im);
        //selecciona el bloque que se va a rotar
        snapshot.setViewport(new Rectangle2D(x, y, pixels, pixels));
        im = imageView.snapshot(snapshot, null);
        imageView.setImage(im);
        //rota la imagen
        imageView.setRotate(imageView.getRotate() - 90);

        //coloca la imagen en el mismo lugar donde fue seleccionada
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        gc2.drawImage(rotatedImage, x, y);

        imagePiece = null;
        imageView = null;
    }

    //Metodo que voltea verticalmente
    public void verticalFlip(Image im, int x, int y, int pixels, GraphicsContext gc2) {
        //permite que la imagen se pueda editar
        imageView = new ImageView(im);
        SnapshotParameters snap = new SnapshotParameters();
        //selecciona el bloque que se va a voltear
        snap.setViewport(new Rectangle2D(x, y, pixels, pixels));
        im = imageView.snapshot(snap, null);
        //voltea la imagen
        gc2.drawImage(im, 0, 0, pixels, pixels, x + pixels, y, -pixels, pixels);
    }

    //Metodo que voltea horizontalmente
    public void horizontalFlip(Image im, int x, int y, int pixels, GraphicsContext gc2) {
        //permite que la imagen se pueda editar
        imageView = new ImageView(im);
        SnapshotParameters snap = new SnapshotParameters();
        //selecciona el bloque que se va a voltear
        snap.setViewport(new Rectangle2D(x, y, pixels, pixels));
        im = imageView.snapshot(snap, null);
        //voltea la imagen
        gc2.drawImage(im, 0, 0, pixels, pixels, x, y + pixels, pixels, -pixels);
    }

    //metodo que elimina la imagen 
    public void delete(GraphicsContext gc2, int x, int y, int pixels) {
        gc2.setFill(Color.WHITE);
        gc2.setStroke(Color.CYAN);
        gc2.fillRect(x, y, pixels, pixels);
        gc2.strokeRect(x, y, pixels, pixels);
    }

    //metodo que convierte la imagen a bytes
    public byte[] convertImage(Image image) {
        BufferedImage bytesImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytesArray = null;
        try {
            ImageIO.write(bytesImage, "png", baos);
            //coloca los bytes de la imagen en un arreglo de bytes
            bytesArray = baos.toByteArray();
            baos.close();
            return bytesArray;
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Eyleen
 * @author Steven
 */
public class MainWindow extends Application {

    private final int WIDTH = 1366;
    private final int HEIGHT = 720;
    private Button newImage;
    private Button save;
    //private Button size;
    private Label label;
    private TextField n;

    private Pane panel;
    private ScrollPane pane;
    private Scene scene;
    private Canvas canvas1;
    private Canvas canvas2;
    private GraphicsContext gc;

    private Image image;
    private ImageView imageView; //modifica una imagen
    private SnapshotParameters snapshot; //le da atributos a la hora de modificar una imagen
    private PixelReader pixel; //se encarga de leer pixel por pixel
    private WritableImage writable; //convierte pixeles en una imagen

    FileChooser fileChooser;
    private int dimension;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Maker");
        initComponents(primaryStage);
        primaryStage.show();

        /*   newImage.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    openImage(file);
                }
            }
        });*/
    }

    private void initComponents(Stage primaryStage) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select a new image");

        this.panel = new Pane();

        this.pane = new ScrollPane();
        this.pane.setPrefSize(600, 400);
        this.pane.relocate(25, 100);

        this.scene = new Scene(panel, WIDTH, HEIGHT);
        this.canvas1 = new Canvas(4096, 2160);
        this.canvas2 = new Canvas(683, 720);
        this.canvas2.relocate(683, 0);

        this.newImage = new Button("New Image");
        //  newImage.setDisable(true);
        this.save = new Button("Save Project");
        // this.size = new Button("Create grid");
        this.label = new Label("Dimension");
        this.n = new TextField();
        this.n.setPrefSize(80, 10);

        this.newImage.relocate(25, 25);
        this.save.relocate(1250, 25);

        this.label.relocate(150, 5);
        this.n.relocate(150, 25);
        // this.size.relocate(240, 25);

        this.newImage.setOnAction(newImageAction);
        // this.save.setOnAction(saveAction);
        // this.size.setOnAction(sizeAction);

        this.panel.getChildren().add(pane);
        this.pane.setContent(this.canvas1);
        //this.pane.getChildren().add(this.canvas2);
        this.panel.getChildren().add(this.newImage);
        this.panel.getChildren().add(this.save);
        this.panel.getChildren().add(this.label);
        this.panel.getChildren().add(this.n);
        // this.panel.getChildren().add(this.size);

        this.gc = this.canvas1.getGraphicsContext2D();//hay que analizar si se necesita otro gc
        //  drawGrid(this.gc);

        primaryStage.setScene(this.scene);
    }

    private void drawGrid(GraphicsContext gc, int dimension, double xp, double yp) {
        gc.setStroke(Color.WHITE);
        int tempx = 0;
        int tempy = 0;
        int x = 0;
        int y = 0;
        double i;
        double j;

        if (xp > yp) {
            for (i = 0; i < xp; i++) {
                if (tempx >= xp) {
                    i = xp;
                }
                for (j = 0; j < yp; j++) {
                    gc.strokeRect(x + i * dimension, y + j * dimension, dimension, dimension);
                    tempy += dimension;

                    if (tempy >= yp) {
                        j = yp;
                    }
                }
                tempy = 0;
                tempx += dimension;
            }
        } else {
            for (i = 0; i < yp; i++) {
                if (tempy >= yp) {
                    i = yp;
                }
                for (j = 0; j < xp; j++) {
                    gc.strokeRect(y + j * dimension, x + i * dimension, dimension, dimension);
                    tempx += dimension;
                    if (tempx >=xp) {
                        j = xp;
                    }
                }
                tempx = 0;
                tempy += dimension;
            }
        }
        
    }

    EventHandler<ActionEvent> newImageAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String text = n.getText();
            if (text.equals("")) {
                System.out.println("Ingrese la dimension");

                /*drawGrid(gc, dimension);
                n.setDisable(true);*/
            } else {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    openImage(file, gc);
                }
                //drawGrid(gc, dimension);
                n.setDisable(true);
            }
        }
    };

    /*  EventHandler<ActionEvent> sizeAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            dimension = Integer.parseInt(n.getText());
            drawGrid(gc, dimension);
            n.setDisable(true);
            size.setDisable(true);
            newImage.setDisable(false);
        }
    };*/
    //este metodo establece un filtro para abrir unicamente imagenes
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPE", "*.jpe")
        );
    }

    private void openImage(File file, GraphicsContext gc) {
        //gc.clearRect(25, 100, 683, 720);
        try {
            gc.clearRect(0, 0, 683, 720);
            this.image = new Image(new FileInputStream(file.getPath()));
            this.imageView = new ImageView(this.image); //recibe por parametro la imagen a modificar
            this.pixel = this.image.getPixelReader();
            this.snapshot = new SnapshotParameters();

            /*
            //Cambiar tamaño
            //imageView.setPreserveRatio(true); //evita que la imagen se distorcione
            this.imageView.setFitHeight(500); //Nuevo tamaño en alto
            this.imageView.setFitWidth(500); //Nuevo tamaño en ancho
            this.image = imageView.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
            this.snapshot.setFill(Color.TRANSPARENT);*/
            //Pintar
            dimension = Integer.parseInt(n.getText());
            double xp = image.getWidth();
            double yp = image.getHeight();

            gc.drawImage(this.image, 0, 0);
            drawGrid(gc, dimension, xp, yp);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

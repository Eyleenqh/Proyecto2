/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import File.ProjectFile;
import domain.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;

/**
 * @author Eyleen
 * @author Steven
 */
public class MainWindow extends Application {

    private final int WIDTH = 1366;
    private final int HEIGHT = 720;
    private Button newImage;
    private Button save;
    private Button size;
    private Button newProject;
    private Button loadProject;
    private Button saveStatus;
    private Label label;
    private TextField n;
    private TextField mosaicSize;

    private ContextMenu cMenu;
    private MenuItem rightRotateMenu;
    private MenuItem leftRotateMenu;
    private MenuItem verticalFlipMenu;
    private MenuItem horizontalFlipMenu;
    private MenuItem deleteMenu;

    private Pane panel;
    private ScrollPane pane;
    private ScrollPane pane2;
    private Scene scene;
    private Canvas canvas1;
    private Canvas canvas2;
    private GraphicsContext gc;
    private GraphicsContext gc2;

    private Image image;
    private Image tempImage;
    private Image imagePiece;
    private ImageView imageView; //modifica una imagen
    private SnapshotParameters snapshot; //le da atributos a la hora de modificar una imagen
    private PixelReader pixel; //se encarga de leer pixel por pixel
    private WritableImage writable; //convierte pixeles en una imagen

    private FileChooser fileChooser;
    private FileChooser fileChooser2;
    private int dimension;

    private int[] cont;
    private int editX;
    private int editY;
    DrawGrid dg;
    private Alert alert;
    
    Project project;
    ProjectFile projectFile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Maker");
        initComponents(primaryStage);
        primaryStage.show();
    }

    private void initComponents(Stage primaryStage) {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Select a new image");
        this.fileChooser2 = new FileChooser();
        this.fileChooser2.setTitle("Save Image");

        this.alert = new Alert(Alert.AlertType.ERROR);

        this.cMenu = new ContextMenu();
        this.rightRotateMenu = new MenuItem("Rotate rigth");
        this.leftRotateMenu = new MenuItem("Rotate left");
        this.verticalFlipMenu = new MenuItem("Vertical flip");
        this.horizontalFlipMenu = new MenuItem("Horizontal flip");
        this.deleteMenu = new MenuItem("Delete");
        this.cMenu.getItems().addAll(rightRotateMenu, leftRotateMenu, verticalFlipMenu, horizontalFlipMenu, deleteMenu);

        this.panel = new Pane();
        this.pane = new ScrollPane();
        this.pane2 = new ScrollPane();
        this.pane.setPrefSize(600, 400);
        this.pane.relocate(25, 100);
        this.pane2.setPrefSize(600, 400);
        this.pane2.relocate(740, 100);

        this.scene = new Scene(panel, WIDTH, HEIGHT);
        this.canvas1 = new Canvas(4096, 2160);
        this.canvas2 = new Canvas(4096, 2160);
        this.canvas1.setDisable(true);
        this.canvas2.setDisable(true);

        this.newImage = new Button("New Image");
        this.save = new Button("Save Project");
        this.size = new Button("Width x Height");
        this.newProject = new Button("New Project");
        this.loadProject = new Button("Load Project");
        this.saveStatus = new Button("Save Status");

        this.label = new Label("Pixels:");
        this.n = new TextField();
        this.n.setPrefSize(80, 10);
        this.mosaicSize = new TextField();
        this.mosaicSize.setPrefSize(80, 10);

        this.newImage.relocate(25, 25);
        this.save.relocate(1250, 25);
        this.size.relocate(900, 25);
        this.newProject.relocate(520, 640);
        this.loadProject.relocate(630, 640);
        this.saveStatus.relocate(740, 640);

        this.label.relocate(150, 5);
        this.n.relocate(150, 25);
        this.mosaicSize.relocate(1050, 25);

        this.newImage.setOnAction(newImageAction);
        this.save.setOnAction(saveAction);
        this.size.setOnAction(sizeAction);
        this.newProject.setOnAction(newProjectAction);
        this.loadProject.setOnAction(loadProjectAction);
        this.saveStatus.setOnAction(saveStatusAction);
        this.rightRotateMenu.setOnAction(rightRotateAction);
        this.leftRotateMenu.setOnAction(leftRotateAction);
        this.verticalFlipMenu.setOnAction(verticalFlipAction);
        this.horizontalFlipMenu.setOnAction(horizontalFlipAction);
        this.deleteMenu.setOnAction(deleteAction);

        this.canvas1.setOnMouseClicked(canvas1MouseEvent);
        this.canvas2.setOnMouseClicked(canvas2MouseEvent);

        this.panel.getChildren().add(pane);
        this.panel.getChildren().add(pane2);
        this.pane.setContent(this.canvas1);
        this.pane2.setContent(this.canvas2);
        this.panel.getChildren().add(this.newImage);
        this.panel.getChildren().add(this.save);
        this.panel.getChildren().add(this.newProject);
        this.panel.getChildren().add(this.loadProject);
        this.panel.getChildren().add(this.label);
        this.panel.getChildren().add(this.n);
        this.panel.getChildren().add(this.size);
        this.panel.getChildren().add(this.mosaicSize);
        this.panel.getChildren().add(this.saveStatus);

        this.gc = this.canvas1.getGraphicsContext2D();
        this.gc2 = this.canvas2.getGraphicsContext2D();

        this.dg = new DrawGrid();

        primaryStage.setScene(this.scene);
    }

    EventHandler<ActionEvent> newImageAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String text = n.getText();
            if (n.getText().isEmpty()) {
                alert.setContentText("Insert a dimension");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    openImage(file, gc);
                }
                n.setDisable(true);
            }
        }
    };

    EventHandler<ActionEvent> saveAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            if (canvas2.isDisable()) {
                alert.setContentText("There isn't an image");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                fileChooser2.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
                File file = fileChooser2.showSaveDialog(null);

                if (file != null) {
                    writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                    Image canvasImage = canvas2.snapshot(new SnapshotParameters(), writable);

                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(canvasImage,
                                null), "PNG", file);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                n.setDisable(true);
            }
        }
    };

    EventHandler<ActionEvent> sizeAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (n.getText().isEmpty() || mosaicSize.getText().isEmpty() || (n.getText().isEmpty() && mosaicSize.getText().isEmpty())) {
                alert.setContentText("Insert a dimension and the size of the mosaic.");
                alert.setHeaderText(null);
                alert.showAndWait();

            } else {
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                dimension = Integer.parseInt(n.getText());

                if (sizeMosaic >= dimension * 2) {
                    mosaicSize.setDisable(true);
                    n.setDisable(true);
                    size.setDisable(true);
                    canvas2.setWidth(4096);
                    canvas2.setHeight(2160);

                    gc2.setFill(Color.WHITE);
                    gc2.fillRect(0, 0, 4096, 2160);

                    cont = dg.drawGrid2(gc2, dimension, sizeMosaic);
                    canvas2.setWidth(cont[0]);
                    canvas2.setHeight(cont[1]);
                    canvas2.setDisable(false);
                } else {
                    alert.setContentText("The size of the mosaic should be higher than the dimension.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
        }
    };

    EventHandler<ActionEvent> newProjectAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            gc.clearRect(0, 0, 4096, 2160);
            gc2.clearRect(0, 0, 4096, 2160);
            mosaicSize.setDisable(false);
            n.setDisable(false);
            size.setDisable(false);
            mosaicSize.setText("");
            n.setText("");
            tempImage = null;
            canvas2.setDisable(true);
        }
    };

    EventHandler<ActionEvent> loadProjectAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
             //projectFile.;
           /*
            Image img = new Image(new ByteArrayInputStream(buffer));
            gc2.drawImage(img, 0, 0, project.getSizeMosaic(), project.getSizeMosaic());*/
           
           //bloquear textField y boton de la cuadricula
        }
    };

    EventHandler<ActionEvent> saveStatusAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int sizeMosaic = Integer.parseInt(mosaicSize.getText());
            writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
            Image canvasImage = canvas2.snapshot(new SnapshotParameters(), writable);

            FileChooser fileChooser3 = new FileChooser();
            File file = fileChooser3.showSaveDialog(null);
            project = new Project("bu", path, dimension, sizeMosaic);
            if (file != null) {
                try {
                    projectFile = new ProjectFile();
                    projectFile.newProjectFile(project, file);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    EventHandler<ActionEvent> rightRotateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            rotateRight(imagePiece, editX, editY, dimension);
        }
    };

    EventHandler<ActionEvent> leftRotateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            rotateLeft(imagePiece, editX, editY, dimension);
        }
    };

    EventHandler<ActionEvent> verticalFlipAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            verticalFlip(imagePiece, editX, editY, dimension);
        }
    };
    
    EventHandler<ActionEvent> horizontalFlipAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            horizontalFlip(imagePiece, editX, editY, dimension);
        }
    };

    EventHandler<ActionEvent> deleteAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            gc2.setFill(Color.WHITE);
            gc2.setStroke(Color.CYAN);
            gc2.fillRect(editX, editY, dimension, dimension);
            gc2.strokeRect(editX, editY, dimension, dimension);
        }
    };

    EventHandler<MouseEvent> canvas1MouseEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.PRIMARY) {
                int x = (int) e.getX();
                int y = (int) e.getY();
                int dimension = Integer.parseInt(n.getText());
                int cutX = (x / dimension) * dimension;
                int cutY = (y / dimension) * dimension;
                imageView = new ImageView(image);
                snapshot.setViewport(new Rectangle2D(cutX, cutY, dimension, dimension));
                tempImage = imageView.snapshot(snapshot, null);
            }
        }
    };

    EventHandler<MouseEvent> canvas2MouseEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            int dimension = Integer.parseInt(n.getText());
            if (e.getButton() == MouseButton.PRIMARY) {
                int x = (int) e.getX();
                int y = (int) e.getY();

                int pasteX = (x / dimension) * dimension;
                int pasteY = (y / dimension) * dimension;
                gc2.drawImage(tempImage, pasteX, pasteY);
            }

            if (e.getButton() == MouseButton.SECONDARY) {
                int x = (int) e.getX();
                int y = (int) e.getY();

                editX = (x / dimension) * dimension;
                editY = (y / dimension) * dimension;

                imagePiece = canvas2.snapshot(new SnapshotParameters(), writable);

                cMenu.show(canvas2, e.getScreenX(), e.getScreenY());
            }
        }
    };

    //Metodo que rota la imagen a la derecha
    public void rotateRight(Image im, int x, int y, int dimension) {
        imageView = new ImageView(im);
        snapshot.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snapshot, null);
        imageView.setImage(im);
        imageView.setRotate(imageView.getRotate() + 90);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        gc2.drawImage(rotatedImage, x, y);

        writable = null;
        imagePiece = null;
        imageView = null;
        pixel = null;
    }

    //Metodo que rota a la izquierda
    public void rotateLeft(Image im, int x, int y, int dimension) {
        imageView = new ImageView(im);
        snapshot.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snapshot, null);
        imageView.setImage(im);
        imageView.setRotate(imageView.getRotate() - 90);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        gc2.drawImage(rotatedImage, x, y);

        writable = null;
        imagePiece = null;
        imageView = null;
        pixel = null;
    }

    //Metodo que voltea verticalmente
    public void verticalFlip(Image im, int x, int y, int dimension) {
        imageView = new ImageView(im);
        SnapshotParameters snap = new SnapshotParameters();
        snap.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snap, null);
        gc2.drawImage(im, 0, 0, dimension, dimension, x+dimension, y, -dimension, dimension);
    }
    
    //Metodo que voltea horizontalmente
    public void horizontalFlip(Image im, int x, int y, int dimension) {
        imageView = new ImageView(im);
        SnapshotParameters snap = new SnapshotParameters();
        snap.setViewport(new Rectangle2D(x, y, dimension, dimension));
        im = imageView.snapshot(snap, null);
        gc2.drawImage(im, 0, 0, dimension, dimension, x, y+dimension, dimension, -dimension);
    }

    //este metodo establece un filtro
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
        try {
            gc.clearRect(0, 0, 4096, 2160);
            this.image = new Image(new FileInputStream(file.getPath()));
            this.snapshot = new SnapshotParameters();

            dimension = Integer.parseInt(n.getText());
            double xp = image.getWidth();
            double yp = image.getHeight();

            this.canvas1.setWidth(4096);
            this.canvas1.setHeight(2160);
            gc.drawImage(this.image, 0, 0);
            cont = dg.drawGrid1(gc, dimension, xp, yp);
            this.canvas1.setWidth(cont[0]);
            this.canvas1.setHeight(cont[1]);
            this.canvas1.setDisable(false);
            tempImage = null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

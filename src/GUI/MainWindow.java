/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import methods.DrawGrid;
import File.ProjectFile;
import methods.ImageManipulation;
import methods.FileChooserProperties;
import domain.Project;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.WindowEvent;
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
    private ImageView imageView;
    private SnapshotParameters snapshot;
    private WritableImage writable;

    private FileChooser fileChooser;
    private FileChooser fileChooser2;
    private FileChooser fileChooser3;
    private FileChooser fileChooser4;

    private int dimension;
    private int[] sizeCanvas1;
    private int sizeCanvas2;
    private int editX;
    private int editY;
    private boolean saveCondition;
    private Alert alert;

    private int j;
    private int i;
    private boolean trueFalse[][];
    private DrawGrid dg;
    private Project project;
    private ProjectFile projectFile;
    private ImageManipulation imageManipulation;
    private FileChooserProperties fileChooserProperties;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Maker");
        initComponents(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(closeAction);
    }

    private void initComponents(Stage primaryStage) {
        this.i = 0;
        this.j = 0;

        this.fileChooser = new FileChooser();
        this.fileChooser2 = new FileChooser();
        this.fileChooser3 = new FileChooser();
        this.fileChooser4 = new FileChooser();

        this.alert = new Alert(Alert.AlertType.ERROR);
        this.saveCondition = false;

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
        this.pane.setVisible(false);
        this.pane2.setVisible(false);

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
        this.label.setTextFill(Color.WHITE);
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
        this.imageManipulation = new ImageManipulation();
        this.fileChooserProperties = new FileChooserProperties();
        setBackground();
        primaryStage.setScene(this.scene);
    }

    EventHandler<WindowEvent> closeAction = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            if (pane.isVisible() && pane2.isVisible()) {
                if (!saveCondition) {
                    if (!closeWindow()) {
                        event.consume();
                    }
                }
            }
        }
    };

    EventHandler<ActionEvent> newImageAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String text = n.getText();
            if (n.getText().isEmpty() || Integer.parseInt(n.getText())<50) {
                alert.setContentText("Insert a valid pixel's cuantity. (Minimum: 50 pixels)");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                fileChooserProperties.configureFileChooser1(fileChooser);
                File file = fileChooser.showOpenDialog(null);
                if (file == null) {

                } else {
                    if (file != null) {
                        openImage(file, gc);
                        saveCondition = false;
                    }
                    n.setDisable(true);
                }
            }
        }
    };

    EventHandler<ActionEvent> saveAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            i = 0;
            j = 0;

            if (canvas2.isDisable()) {
                alert.setContentText("There isn't an image");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                Image canvasImageAux = canvas2.snapshot(new SnapshotParameters(), writable);
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                dg.drawGrid3(gc2, dimension, sizeMosaic, j, i, trueFalse);

                fileChooserProperties.configureFileChooser2(fileChooser2);
                File file = fileChooser2.showSaveDialog(null);
                if (file == null) {
                    gc2.drawImage(canvasImageAux, 0, 0);
                } else {
                    writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setFill(Color.TRANSPARENT);
                    Image canvasImage = canvas2.snapshot(sp, writable);
                    gc2.drawImage(canvasImageAux, 0, 0);

                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(canvasImage,
                                null), "PNG", file);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    n.setDisable(true);
                }
            }
        }
    };

    EventHandler<ActionEvent> sizeAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (n.getText().isEmpty() || mosaicSize.getText().isEmpty() || (n.getText().isEmpty() && mosaicSize.getText().isEmpty())) {
                alert.setContentText("Insert the pixels and the size of the mosaic.");
                alert.setHeaderText(null);
                alert.showAndWait();

            } else {
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                dimension = Integer.parseInt(n.getText());

                if (Integer.parseInt(n.getText())>=50 && sizeMosaic >= dimension * 2 && sizeMosaic<=2160) {
                    mosaicSize.setDisable(true);
                    n.setDisable(true);
                    size.setDisable(true);
                    canvas2.setWidth(4096);
                    canvas2.setHeight(2160);
                    gc2.setFill(Color.WHITE);
                    gc2.fillRect(0, 0, 4096, 2160);
                    sizeCanvas2 = dg.drawGrid2(gc2, dimension, sizeMosaic, j, i);
                    
                    
                    canvas2.setWidth(sizeCanvas2);
                    canvas2.setHeight(sizeCanvas2);
                    
                    trueFalse = new boolean[(sizeCanvas2 / dimension) + 1][(sizeCanvas2 / dimension) + 1];
                    for (int i = 0; i < trueFalse.length; i++) {
                        for (int j = 0; j < trueFalse.length; j++) {
                            trueFalse[i][j] = false;
                        }
                    }
                    canvas2.setDisable(false);
                    pane2.setVisible(true);
                } else {
                    alert.setContentText("The size of the mosaic should be higher than the pixels and the pixels must be 50 or higher.");
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
            pane.setVisible(false);
            pane2.setVisible(false);
            saveCondition = false;
            j = 0;
            i = 0;
        }
    };

    EventHandler<ActionEvent> loadProjectAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            //variable booleana true
            fileChooserProperties.configureFileChooser3(fileChooser3);
            File file = fileChooser3.showOpenDialog(null);

            if (file == null) {

            } else {
                try {
                    projectFile = new ProjectFile();
                    project = projectFile.fileRead(file);
                    Image useImage = new Image(new ByteArrayInputStream(project.getUseImage()));
                    Image mosaicImage = new Image(new ByteArrayInputStream(project.getImage()));
                    mosaicSize.setText(String.valueOf(project.getSizeMosaic()));
                    n.setText(String.valueOf(project.getDimension()));
                    dimension = project.getDimension();

                    image = useImage;
                    imageView = new ImageView(image);
                    snapshot = new SnapshotParameters();
                    gc.clearRect(0, 0, 4096, 2160);
                    gc2.clearRect(0, 0, 4096, 2160);
                    gc.drawImage(image, 0, 0);
                    gc2.drawImage(mosaicImage, 0, 0);
                    canvas2.setWidth(mosaicImage.getWidth());
                    canvas2.setHeight(mosaicImage.getHeight());
                    sizeCanvas1 = dg.drawGrid1(gc, dimension, useImage.getWidth(), useImage.getHeight());
                    canvas1.setWidth(sizeCanvas1[0]);
                    canvas1.setHeight(sizeCanvas1[1]);

                    mosaicSize.setDisable(true);
                    n.setDisable(true);
                    size.setDisable(true);
                    canvas1.setDisable(false);
                    canvas2.setDisable(false);
                    pane.setVisible(true);
                    pane2.setVisible(true);
                    tempImage = null;
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    EventHandler<ActionEvent> saveStatusAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (pane.isVisible() && pane2.isVisible()) {
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                Image canvasImage = canvas2.snapshot(new SnapshotParameters(), writable);

                fileChooserProperties.configureFileChooser4(fileChooser4);
                File file = fileChooser4.showSaveDialog(null);
                project = new Project(imageManipulation.convertImage(canvasImage), imageManipulation.convertImage(image), dimension, sizeMosaic);

                if (file != null) {
                    try {
                        projectFile = new ProjectFile();
                        projectFile.newProjectFile(project, file);
                        saveCondition = true;
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                alert.setContentText("There isn't status to save.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    };

    EventHandler<ActionEvent> rightRotateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.rotateRight(imagePiece, editX, editY, dimension, gc2);
        }
    };

    EventHandler<ActionEvent> leftRotateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.rotateLeft(imagePiece, editX, editY, dimension, gc2);
        }
    };

    EventHandler<ActionEvent> verticalFlipAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.verticalFlip(imagePiece, editX, editY, dimension, gc2);
        }
    };

    EventHandler<ActionEvent> horizontalFlipAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.horizontalFlip(imagePiece, editX, editY, dimension, gc2);
        }
    };

    EventHandler<ActionEvent> deleteAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.delete(gc2, editX, editY, dimension);
            trueFalse[editX / dimension][editY / dimension] = false;
        }
    };

    EventHandler<MouseEvent> canvas1MouseEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.PRIMARY) {
                int x = (int) e.getX();
                int y = (int) e.getY();
                int dimension = Integer.parseInt(n.getText());
                tempImage = imageManipulation.selectedImage(image, dimension, x, y);
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
                trueFalse[x / dimension][y / dimension] = true;
                imageManipulation.pasteImage(gc2, tempImage, dimension, x, y);
            }

            if (e.getButton() == MouseButton.SECONDARY) {
                int x = (int) e.getX();
                int y = (int) e.getY();

                editX = (x / dimension) * dimension;
                editY = (y / dimension) * dimension;

                imagePiece = canvas2.snapshot(new SnapshotParameters(), writable);
                cMenu.show(canvas2, e.getScreenX(), e.getScreenY());
            }
            saveCondition = false;
        }
    };

    private void openImage(File file, GraphicsContext gc) {
        try {
            gc.clearRect(0, 0, 4096, 2160);
            this.image = new Image(new FileInputStream(file.getPath()));
            this.snapshot = new SnapshotParameters();

            dimension = Integer.parseInt(n.getText());
            double xp = image.getWidth();
            double yp = image.getHeight();

            if (xp > dimension && yp > dimension) {
                this.canvas1.setWidth(4096);
                this.canvas1.setHeight(2160);
                gc.drawImage(this.image, 0, 0);
                sizeCanvas1 = dg.drawGrid1(gc, dimension, xp, yp);
                this.canvas1.setWidth(sizeCanvas1[0]);
                this.canvas1.setHeight(sizeCanvas1[1]);
                this.canvas1.setDisable(false);
                this.pane.setVisible(true);
                tempImage = null;
            } else {
                alert.setContentText("The image doesn't fit with the specified dimension");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean closeWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Save the project?");
        alert.setContentText("");

        ButtonType buttonTypeOne = new ButtonType("Save");
        ButtonType buttonTypeTwo = new ButtonType("Cancel");
        ButtonType buttonTypeCancel = new ButtonType("Exit");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            ActionEvent event = new ActionEvent();
            saveStatusAction.handle(event);
            return true;
        } else {
            if (result.get() == buttonTypeTwo) {
                return false;
            }
        }
        return true;
    }

    private void setBackground() {
        Image back = new Image("/image/background.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.panel.setBackground(background);
    }
}

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
import domain.ProjectClone;
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
    private TextField pixelsQuantity;
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

    private int pixels;
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
        this.pixelsQuantity = new TextField();
        this.pixelsQuantity.setPrefSize(80, 10);
        this.mosaicSize = new TextField();
        this.mosaicSize.setPrefSize(80, 10);

        this.newImage.relocate(25, 25);
        this.save.relocate(1250, 25);
        this.size.relocate(900, 25);
        this.newProject.relocate(520, 640);
        this.loadProject.relocate(630, 640);
        this.saveStatus.relocate(740, 640);

        this.label.relocate(150, 5);
        this.pixelsQuantity.relocate(150, 25);
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
        this.panel.getChildren().add(this.pixelsQuantity);
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

    //Evento que ocurre cuando se va a cerrar la ventana
    EventHandler<WindowEvent> closeAction = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            /*se valida si los canvas estan visibles y 
            si saveCondition retorna un false(lo que signifca que el estado del proyecto no ha sido guardado)*/
            if (pane.isVisible() && pane2.isVisible()) {
                if (!saveCondition) {
                    if (!closeWindow()) {
                        event.consume();
                    }
                }
            }
        }
    };

    //Evento de boton con el que se abre el filechooser para cargar una imagen
    EventHandler<ActionEvent> newImageAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String text = pixelsQuantity.getText();
            //valida el campo de texto que contiene el tamanio de los pixeles, que no este vacio y que sea mayor a 50
            if (pixelsQuantity.getText().isEmpty() || Integer.parseInt(pixelsQuantity.getText()) < 50) {
                alert.setContentText("Insert a valid pixel's cuantity. (Minimum: 50 pixels)");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                fileChooserProperties.configureFileChooser1(fileChooser);
                File file = fileChooser.showOpenDialog(null);
                //valida que el archivo no sea nulo
                if (file != null) {
                    //llama al metodo para que abra la imagen y la muestre en el canvas
                    openImage(file, gc);
                    saveCondition = false;
                }
                pixelsQuantity.setDisable(true);
            }
        }
    };

    //Evento de boton que permite guardar lo que se encuentra en el canvas2 como una imagen en formato PNG
    EventHandler<ActionEvent> saveAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            i = 0;
            j = 0;

            //valida que el canvas este habilitado
            if (canvas2.isDisable()) {
                alert.setContentText("There isn't an image");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                //Se obtiene lo que contiene el canvas2 para guardarlo en una variable auxiliar
                writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                Image canvasImageAux = canvas2.snapshot(new SnapshotParameters(), writable);
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                //llama al metodo para elminar los bloques vacios
                dg.drawGrid3(gc2, pixels, sizeMosaic, j, i, trueFalse);

                fileChooserProperties.configureFileChooser2(fileChooser2);
                File file = fileChooser2.showSaveDialog(null);
                if (file == null) {
                    //dibuja la imagen en el canvas
                    gc2.drawImage(canvasImageAux, 0, 0);
                } else {
                    //Se obtiene lo que contiene el canvas2 ya con los bloques eliminados
                    writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setFill(Color.TRANSPARENT);
                    Image canvasImage = canvas2.snapshot(sp, writable);
                    //dibuja la imagen en el canvas
                    gc2.drawImage(canvasImageAux, 0, 0);

                    try {
                        //Guarda la imagen en formato PNG
                        ImageIO.write(SwingFXUtils.fromFXImage(canvasImage,
                                null), "PNG", file);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pixelsQuantity.setDisable(true);
                }
            }
        }
    };

    //evento de boton que permite crear la cuadricula para el mosaico
    EventHandler<ActionEvent> sizeAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            //valida que los espacios para ingresar texto no esten vacios
            if (pixelsQuantity.getText().isEmpty() || mosaicSize.getText().isEmpty() || (pixelsQuantity.getText().isEmpty() && mosaicSize.getText().isEmpty())) {
                alert.setContentText("Insert the pixels and the size of the mosaic.");
                alert.setHeaderText(null);
                alert.showAndWait();

            } else {
                //obtiene lo que que el usario ingreso en los textField
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                pixels = Integer.parseInt(pixelsQuantity.getText());

                //valida que los pixeles sean mayor a 50 y menor a 2160
                if (Integer.parseInt(pixelsQuantity.getText()) >= 50 && sizeMosaic >= pixels * 2 && sizeMosaic <= 2160) {
                    mosaicSize.setDisable(true);
                    pixelsQuantity.setDisable(true);
                    size.setDisable(true);
                    canvas2.setWidth(4096);
                    canvas2.setHeight(2160);
                    gc2.setFill(Color.WHITE);
                    gc2.fillRect(0, 0, 4096, 2160);

                    //llama al metodo que dibuja la cuadricula
                    sizeCanvas2 = dg.drawGrid2(gc2, pixels, sizeMosaic, j, i);
                    //llama al metodo que genera la matriz booleana
                    generateLogicMatrix(sizeMosaic, pixels);

                    //establece el tamanio del canvas
                    canvas2.setWidth(sizeCanvas2);
                    canvas2.setHeight(sizeCanvas2);

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

    //evento de boton que permite crear un nuevo proyecto
    EventHandler<ActionEvent> newProjectAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            gc.clearRect(0, 0, 4096, 2160);
            gc2.clearRect(0, 0, 4096, 2160);
            //Habilita los botones y cuadros de textos bloqueados, limpia los canvas y vuelve nulas las varibles que lo requiren
            mosaicSize.setDisable(false);
            pixelsQuantity.setDisable(false);
            size.setDisable(false);
            mosaicSize.setText("");
            pixelsQuantity.setText("");
            tempImage = null;
            canvas2.setDisable(true);
            pane.setVisible(false);
            pane2.setVisible(false);
            saveCondition = false;
            j = 0;
            i = 0;
        }
    };

    //Evento de boton que carga los proyectos guardados anteriormente
    EventHandler<ActionEvent> loadProjectAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            fileChooserProperties.configureFileChooser3(fileChooser3);
            File file = fileChooser3.showOpenDialog(null);
            Image useImage;
            Image mosaicImage;

            //valida que le archivo no sea nulo
            if (file == null) {

            } else {
                try {
                    projectFile = new ProjectFile();
                    //se verifica si ya se instancio un objecto de tipo project
                    //si ya se creo lo clona, si no, lo instancia
                    if (saveCondition) {
                        //se clona el objecto
                        Project projectTemp = project.clone();
                        //se le dan nuevos valores a los atributos del clon
                        projectTemp.setImage(projectFile.fileRead(file, projectTemp).getImage());
                        projectTemp.setUseImage(projectFile.fileRead(file, projectTemp).getUseImage());
                        projectTemp.setPixels(projectFile.fileRead(file, projectTemp).getPixels());
                        projectTemp.setSizeMosaic(projectFile.fileRead(file, projectTemp).getSizeMosaic());
                        projectTemp.setLogicMatrix(projectFile.fileRead(file, projectTemp).getLogicMatrix());

                        //se extraen los valores del proyecto para asignarlos a la ventana
                        useImage = new Image(new ByteArrayInputStream(projectTemp.getUseImage()));
                        mosaicImage = new Image(new ByteArrayInputStream(projectTemp.getImage()));
                        mosaicSize.setText(String.valueOf(projectTemp.getSizeMosaic()));
                        pixelsQuantity.setText(String.valueOf(projectTemp.getPixels()));
                        pixels = projectTemp.getPixels();
                        trueFalse = projectTemp.getLogicMatrix();
                    } else {
                        //instacia el proyecto
                        project = new ProjectClone();
                        //se le dan nuevos valores a los atributos del proyecto
                        project.setImage(projectFile.fileRead(file, project).getImage());
                        project.setUseImage(projectFile.fileRead(file, project).getUseImage());
                        project.setPixels(projectFile.fileRead(file, project).getPixels());
                        project.setSizeMosaic(projectFile.fileRead(file, project).getSizeMosaic());
                        project.setLogicMatrix(projectFile.fileRead(file, project).getLogicMatrix());

                        //se extraen los valores del proyecto para asignarlos a la ventana
                        useImage = new Image(new ByteArrayInputStream(project.getUseImage()));
                        mosaicImage = new Image(new ByteArrayInputStream(project.getImage()));
                        mosaicSize.setText(String.valueOf(project.getSizeMosaic()));
                        pixelsQuantity.setText(String.valueOf(project.getPixels()));
                        pixels = project.getPixels();
                        trueFalse = project.getLogicMatrix();
                    }

                    image = useImage;
                    imageView = new ImageView(image);
                    snapshot = new SnapshotParameters();
                    
                    //ajusta el tamanio de los canvas
                    canvas1.setHeight(2160);
                    canvas1.setWidth(4096);
                    canvas2.setHeight(2160);
                    canvas2.setWidth(4096);
                    
                    //limpia los canvas
                    gc.clearRect(0, 0, 4096, 2160);
                    gc2.clearRect(0, 0, 4096, 2160);
                    
                    //ajusta el tamanio de los canvas
                    canvas1.setHeight(image.getHeight());
                    canvas1.setWidth(image.getWidth());
                    canvas2.setHeight(mosaicImage.getHeight());
                    canvas2.setWidth(mosaicImage.getWidth());
                    //dibuja las imagenes sobre los canvas
                    gc.drawImage(image, 0, 0);
                    gc2.drawImage(mosaicImage, 0, 0);
                    //establece el tamanio del canvas2
                    canvas2.setWidth(mosaicImage.getWidth());
                    canvas2.setHeight(mosaicImage.getHeight());
                    //llama al metodo que dibuja la cuadricula
                    sizeCanvas1 = dg.drawGrid1(gc, pixels, useImage.getWidth(), useImage.getHeight());
                    //establece el tamanio del canvas1
                    canvas1.setWidth(sizeCanvas1[0]);
                    canvas1.setHeight(sizeCanvas1[1]);

                    //bloquea botones y textField necesarios, habilita y vuelve visible los canvas
                    mosaicSize.setDisable(true);
                    pixelsQuantity.setDisable(true);
                    size.setDisable(true);
                    canvas1.setDisable(false);
                    canvas2.setDisable(false);
                    pane.setVisible(true);
                    pane2.setVisible(true);
                    tempImage = null;
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    //evento de boton que permite guardar el estado del proyecto en un archivo
    EventHandler<ActionEvent> saveStatusAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            //valida que los scrollPane sean visibles
            if (pane.isVisible() && pane2.isVisible()) {
                int sizeMosaic = Integer.parseInt(mosaicSize.getText());
                //obtiene lo que se encuentra en el canvas como varible tipo imagen
                writable = new WritableImage((int) canvas2.getWidth(), (int) canvas2.getHeight());
                Image canvasImage = canvas2.snapshot(new SnapshotParameters(), writable);

                fileChooserProperties.configureFileChooser4(fileChooser4);
                File file = fileChooser4.showSaveDialog(null);
                //se verifica si ya se instancio un objecto de tipo project
                //si ya se creo lo clona, si no, lo instancia
                if (!saveCondition) {
                    //instacia el proyecto
                    project = new ProjectClone();
                    //se le dan nuevos valores a los atributos del proyecto
                    project.setImage(imageManipulation.convertImage(canvasImage));
                    project.setUseImage(imageManipulation.convertImage(image));
                    project.setPixels(pixels);
                    project.setSizeMosaic(sizeMosaic);
                    project.setLogicMatrix(trueFalse);
                } else {
                    try {
                        //se clona el objecto project
                        Project projectTemp = project.clone();
                        //se le dan nuevos valores a los atributos del clon
                        projectTemp.setImage(imageManipulation.convertImage(canvasImage));
                        projectTemp.setUseImage(imageManipulation.convertImage(image));
                        projectTemp.setPixels(pixels);
                        projectTemp.setSizeMosaic(sizeMosaic);
                        projectTemp.setLogicMatrix(trueFalse);
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (file != null) {
                    try {
                        //crea el nuevo archivo y guarda el proyecto
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

    //evento que llama al metodo que rota el bloque hacia la derecha
    EventHandler<ActionEvent> rightRotateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.rotateRight(imagePiece, editX, editY, pixels, gc2);
        }
    };

    //evento que llama al metodo que rota el bloque hacia la izquierda
    EventHandler<ActionEvent> leftRotateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.rotateLeft(imagePiece, editX, editY, pixels, gc2);
        }
    };

    //evento que llama al metodo que voltea el bloque verticalmente
    EventHandler<ActionEvent> verticalFlipAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.verticalFlip(imagePiece, editX, editY, pixels, gc2);
        }
    };

    //evento que llama al metodo que voltea el bloque horizontalmente
    EventHandler<ActionEvent> horizontalFlipAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.horizontalFlip(imagePiece, editX, editY, pixels, gc2);
        }
    };

    //evento que llama al metodo que borra el bloque
    EventHandler<ActionEvent> deleteAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            imageManipulation.delete(gc2, editX, editY, pixels);
            trueFalse[editX / pixels][editY / pixels] = false;
        }
    };

    //Evento del mouse que permite seleecionar una bloque
    EventHandler<MouseEvent> canvas1MouseEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.PRIMARY) {
                //obtiene las coordenadas de donde se activo el evento y tamanio en pixeles ingresado por el usuario
                int x = (int) e.getX();
                int y = (int) e.getY();
                int pixel = Integer.parseInt(pixelsQuantity.getText());
                //llama al meto que selecciona el bloque unicado en las coordenadas seleccionadas
                tempImage = imageManipulation.selectedImage(image, pixel, x, y);
            }
        }
    };

    //Evento del mouse que colocar un bloque y manipularlo 
    EventHandler<MouseEvent> canvas2MouseEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            int dimension = Integer.parseInt(pixelsQuantity.getText());
            //si el boton primario es seleccionado la imagen contenida en tempImage es colocada en el mosaico
            if (e.getButton() == MouseButton.PRIMARY) {
                //obtiene las coordenadas de donde se activo el evento 
                int x = (int) e.getX();
                int y = (int) e.getY();
                trueFalse[x / dimension][y / dimension] = true;
                //llama al metodo que pegara la imagen
                imageManipulation.pasteImage(gc2, tempImage, dimension, x, y);
            }

            //si el boton secundario es seleccionado se deplegara un menu contextual
            if (e.getButton() == MouseButton.SECONDARY) {
                //obtiene las coordenadas de donde se activo el evento 
                int x = (int) e.getX();
                int y = (int) e.getY();

                editX = (x / dimension) * dimension;
                editY = (y / dimension) * dimension;

                imagePiece = canvas2.snapshot(new SnapshotParameters(), writable);
                //se despliega el menu contextual en las coordenadas dode se genero el evento
                cMenu.show(canvas2, e.getScreenX(), e.getScreenY());
            }
            saveCondition = false;
        }
    };

    //Abre la imagen que escoge el usuario por medio del filechooser
    private void openImage(File file, GraphicsContext gc) {
        try {
            //se limpia el canvas
            gc.clearRect(0, 0, 4096, 2160);
            //se asigna la imagen seleccionada a una varible
            this.image = new Image(new FileInputStream(file.getPath()));
            this.snapshot = new SnapshotParameters();

            //se obtiene la dimension dada por el usuario y el tamanio de la imagen
            pixels = Integer.parseInt(pixelsQuantity.getText());
            double xp = image.getWidth();
            double yp = image.getHeight();

            //valida que el tamanio de la imagen sea mayor a la de los pixeles, si no, no la abre
            if (xp > pixels && yp > pixels) {
                this.canvas1.setWidth(4096);
                this.canvas1.setHeight(2160);
                //dibuja la imagen y la cuadricula en el canvas
                gc.drawImage(this.image, 0, 0);
                sizeCanvas1 = dg.drawGrid1(gc, pixels, xp, yp);
                //establece el tamanio del cavas de acuerdo con el de la cuadricula
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

    //metodo que genera una matriz de tipo booleana 
    public void generateLogicMatrix(int sizeCanvas2, int pixels) {
        //crea la matriz deacuerdo al tamanio del canvas
        trueFalse = new boolean[(sizeCanvas2 / pixels) + 1][(sizeCanvas2 / pixels) + 1];
        //convierte todos los valores a "false"
        for (int i = 0; i < trueFalse.length; i++) {
            for (int j = 0; j < trueFalse.length; j++) {
                trueFalse[i][j] = false;
            }
        }
    }

    //metodo que pregunta al usuario si quiere guardar el estado del proyecto,salir o cancelar.
    public boolean closeWindow() {
        //Ventana que se muestra para obtener la respuesta del usuario sobre lo que se desea hacer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Save the project?");
        alert.setContentText("");

        //Botones que contiene dicha ventana
        ButtonType buttonTypeOne = new ButtonType("Save");
        ButtonType buttonTypeTwo = new ButtonType("Cancel");
        ButtonType buttonTypeCancel = new ButtonType("Exit");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        //Obtiene la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            //Se hace llamado a saveStatusAction para que se guarde el estdo del proyecto
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

    //Coloca una imagen en el panel de la ventana
    private void setBackground() {
        Image back = new Image("/image/background.jpg");
        //asigna el tamanio que va a tener la imagen
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        //establece la imagen como fondo
        BackgroundImage backgroundImage = new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.panel.setBackground(background);
    }
}
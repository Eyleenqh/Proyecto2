/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author Eyleen
 */
public class FileChooserProperties {

    //este metodo establece un filtro para el filechooser que abre la imagenes
    public void configureFileChooser1(final FileChooser fileChooser) {
        fileChooser.setTitle("Select a new image");
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

    //este metodo establece un filtro para el filechooser que guarda la imagen en formato PNG
    public void configureFileChooser2(final FileChooser fileChooser2) {
        fileChooser2.setTitle("Save Image");
        fileChooser2.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser2.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
    }

    //este metodo establece un filtro para el filechooser que abre el archivo que contiene el proyecto 
    public void configureFileChooser3(final FileChooser fileChooser3) {
        fileChooser3.setTitle("Open project");
        fileChooser3.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser3.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("dat files (*.dat)", "*.dat"));

    }

    //este metodo establece un filtro para el filechooser que guarda en estatus del proyecto
    public void configureFileChooser4(final FileChooser fileChooser4) {
        fileChooser4.setTitle("Save project");
        fileChooser4.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser4.getExtensionFilters().add(new FileChooser.ExtensionFilter("dat files (*.dat)", "*.dat"));
    }
}

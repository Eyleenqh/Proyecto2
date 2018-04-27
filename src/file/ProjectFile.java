package File;

import domain.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Eyleen
 * @author Steven
 */
public class ProjectFile {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    Project project;
    //File file;

    //metodo en el que se crean los archivos serializables
    public ProjectFile() throws FileNotFoundException, IOException {
        // file=new File("Project.obj");
      //  ois = new ObjectInputStream(new FileInputStream(file));
        // oos = new ObjectOutputStream(new FileOutputStream(file));

    }

    //metodo que escribe el archivo
    public void newProjectFile(Project project, File file) throws IOException {
        oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(project);
        oos.flush();
        oos.close();
    }

    public static void lecturaBinario() {
        /*  try{
         Project auxi=(Project)ois.readObject();//casting apersona, aqui se lee el primer onjeto
         while(auxi!=null){
             
         }
         ois.close();
    }catch(Exception e2){
        System.out.println("Error de E/S");
    }*/
    }

}

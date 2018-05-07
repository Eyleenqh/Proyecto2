package File;

import domain.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eyleen
 * @author Steven
 */
public class ProjectFile {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    //metodo que escribe el estado del proyecto en un archivo
    public void newProjectFile(Project project, File file) throws IOException {
        oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(project);
        oos.flush();
        oos.close();
    }

    //metodo que lee el archivo que contiene el estado del proyecto
    public Project fileRead(File file, Project p) throws FileNotFoundException, IOException {
        ois = new ObjectInputStream(new FileInputStream(file));
        try {
            p = (Project) ois.readObject();
            ois.close();
            return p;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}

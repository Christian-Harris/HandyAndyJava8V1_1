package handler;

import application.editor.SaveableEditor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import menu.UserMenu;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * The SaveHandler class handles the saving of the current state of a loaded work order. The work order is saved as a collection of 3
 * files. The input document, the output document, and the internal state of the editor object.
 * @author Christian Harris
 */
public class SaveHandler implements EventHandler<ActionEvent>{
    private final UserMenu userMenu;
    
    public SaveHandler(UserMenu userMenu){
        this.userMenu = userMenu;
    }
    
    @Override
    public void handle(ActionEvent e){
        if(userMenu.getEditor() != null){
            Stage stage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Save");
            File directory = directoryChooser.showDialog(stage);
            if(directory != null){
                this.save(directory, this.userMenu.getInputDocument(), this.userMenu.getEditor().getDocument(), this.userMenu.getEditor().generateSaveableEditor());
            }
        }
    }
    
    private void save(File directory, PDDocument inputDocument, PDDocument outputDocument, SaveableEditor saveableEditor){
        String jobNumber = userMenu.getEditor().getJobNumber();
        File saveDirectory = new File(directory.toString() + "\\" + jobNumber);
        if(saveDirectory.exists() || saveDirectory.mkdir()){
            File input = new File(saveDirectory.toString() + "\\" + jobNumber + "Jensen.pdf");
            File output = new File(saveDirectory.toString() + "\\" + jobNumber + "HandyAndy.pdf");
            File workOrder = new File(saveDirectory.toString() + "\\" + jobNumber + "WorkOrder.mrc");
            try{
                inputDocument.save(input);
                outputDocument.save(output);
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(workOrder));
                outputStream.writeObject(saveableEditor);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        
        
        /*
        if(directory.mkdir()){
            System.out.println(file.toString());
            String jobNumber = userMenu.getEditor().getJobNumber();
            File input = new File(file.toString() + "\\" + jobNumber + "Jensen.pdf");
            File output = new File(file.toString() + "\\" + jobNumber + "HandyAndy.pdf");
            System.out.println(input.toString());
            try{
                
                inputDocument.save(input);
                outputDocument.save(output);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }*/
    }
    
}

package handler;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import menu.UserMenu;
import application.editor.SaveableEditor;
import application.editor.Editor;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * The OpenWorkOrderHandler class handles the opening of a previously saved work order.
 * @author Christian Harris
 */
public class OpenWorkOrderHandler implements EventHandler<ActionEvent>{
    private final UserMenu userMenu;
    
    public OpenWorkOrderHandler(UserMenu userMenu){
        this.userMenu = userMenu;
    }
    
    @Override
    public void handle(ActionEvent e){
        Stage stage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Open");
            File directory = directoryChooser.showDialog(stage);
            if(directory != null){
                this.load(directory);
            }
    }
    
    public void load(File directory){
        try{
            File input;
            File output;
            File workOrder;
            for(String name : directory.list()){
                if(name.contains("Jensen.pdf")){
                    input = new File(directory.toString() + "\\" + name);
                    this.userMenu.setInputDocument(PDDocument.load(input));
                }
                else if(name.contains("WorkOrder.mrc")){
                    workOrder = new File(directory.toString() + "\\" + name);
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(workOrder));
                    SaveableEditor sEditor = (SaveableEditor)inputStream.readObject();
                    this.userMenu.setEditor(new Editor(sEditor));
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex){
            
        }
    }
}

package handler;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menu.UserMenu;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import parser.JensenPropertyManagementParser;

/**
 *
 * @author Christian Harris
 */
public class NewWorkOrderHandler implements EventHandler<ActionEvent>{
    private final UserMenu userMenu;
    
    public NewWorkOrderHandler(UserMenu userMenu){
        this.userMenu = userMenu;
    }
    
    @Override
    public void handle(ActionEvent e){
        if(this.userMenu.getInputDocument() != null && !(this.userMenu.getInputDocument().getDocument().isClosed())){
            try{
                this.userMenu.getInputDocument().close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try{
                this.userMenu.setInputDocument(PDDocument.load(file));
                this.userMenu.setEditor(JensenPropertyManagementParser.parse(file));                
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
}

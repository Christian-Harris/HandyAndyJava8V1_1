package menu;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import handler.LogoutHandler;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Christian Harris
 */

public class UserMenu extends BorderPane{
    private MenuBar menuBar;
    
    private Menu fileMenu;
    private MenuItem openFile;
    
    private Menu userMenu;
    private MenuItem logout;
    
    private  File inputFile;
    private  PDDocument inputDocument;
    private  Image inputImage;
    private  ImageView inputView;
    
    private  File outputFile;
    private  PDDocument outputDocument;
    private  Image outputImage;
    private  ImageView outputView;
    
    private PDFRenderer renderer;
    
    public UserMenu(){
        menuBar = new MenuBar();
        
        fileMenu = new Menu("File");
        openFile = new MenuItem("Open File");
        openFile.setOnAction(event -> openFile(event));
        fileMenu.getItems().add(openFile);
        
        userMenu = new Menu("User");
        logout = new MenuItem("Logout");
        logout.setOnAction(new LogoutHandler());
        userMenu.getItems().add(logout);
        
        menuBar.getMenus().addAll(fileMenu, userMenu);
        
        this.setTop(menuBar);
    }
    
    private void openFile(ActionEvent e){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try{
                inputFile = file;
                try{
                    inputDocument = PDDocument.load(file);
                }
                catch(IOException ex){
                    //Catches in case file was not a pdf needs to be restructured around entire method.
                }
                renderer = new PDFRenderer(inputDocument);
                BufferedImage bImage = renderer.renderImage(0);
                inputImage = SwingFXUtils.toFXImage(bImage, null);
                inputView = new ImageView();
                inputView.setImage(inputImage);
                setLeft(inputView);
                
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(inputDocument);
                setRight(new Text(text));
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}

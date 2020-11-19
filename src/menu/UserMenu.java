package menu;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;

import handler.LogoutHandler;


import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author Christian Harris
 */

public class UserMenu extends BorderPane{
    private static MenuBar menuBar;
    
    private static Menu fileMenu;
    private static MenuItem openFile;
    
    private static Menu userMenu;
    private static MenuItem logout;
    
    File file;
    PDDocument inputDocument;
    PDDocument outputDocument;
    
    PDFRenderer renderer;
    
    public UserMenu(){
        menuBar = new MenuBar();
        
        fileMenu = new Menu("File");
        openFile = new MenuItem("Open File");
        fileMenu.getItems().add(openFile);
        
        userMenu = new Menu("User");
        logout = new MenuItem("Logout");
        logout.setOnAction(new LogoutHandler());
        userMenu.getItems().add(logout);
        
        menuBar.getMenus().addAll(fileMenu, userMenu);
        
        this.setTop(menuBar);
        
        
        
        //temporary code for testing.
        try{
            file = new File("C:\\Workspace\\maint.pdf");
            inputDocument = PDDocument.load(file);
            renderer = new PDFRenderer(inputDocument);
            BufferedImage bImage = renderer.renderImage(0);
            Image fxImage = SwingFXUtils.toFXImage(bImage, null);
            //ByteArrayOutputStream os = new ByteArrayOutputStream();
            //InputStream is = new ByteArrayInputStream(os.toByteArray());
            //Image image = new Image(fxImage);
            //Image image = new Image("File:C:\\Workspace\\Test\\image\\AmericanFlag.jpg");
            ImageView iv = new ImageView();
            iv.setImage(fxImage);
            this.setCenter(iv);
            
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

package menu;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import handler.LogoutHandler;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import parser.JensenPropertyManagementParser;

/**
 *
 * @author Christian Harris
 */

public final class UserMenu extends BorderPane{
    private static MenuBar menuBar;
    
    private static Menu fileMenu;
    private static MenuItem openFile;
    
    private static Menu userMenu;
    private static MenuItem logout;
    
    private static Pane leftPane;
    private  static File inputFile;
    private  static PDDocument inputDocument;
    private static int currentInputPage;
    private  static Image inputImage;
    private  static ImageView inputView;
    
    private static Pane rightPane;
    private  static File outputFile;
    private  static PDDocument outputDocument;
    private static int currentOutputPage;
    private  static Image outputImage;
    private  static ImageView outputView;
    
    private static PDFRenderer inputRenderer;
    private static PDFRenderer outputRenderer;
    
    private static Text output = new Text();
    
    public UserMenu(){
        menuBar = new MenuBar();
        inputView = new ImageView();
        leftPane = new StackPane();
        rightPane = new StackPane();
        
        fileMenu = new Menu("File");
        openFile = new MenuItem("Open File");
        openFile.setOnAction(event -> openFile(event));
        fileMenu.getItems().add(openFile);
        
        userMenu = new Menu("User");
        logout = new MenuItem("Logout");
        logout.setOnAction(new LogoutHandler());
        userMenu.getItems().add(logout);
        
        menuBar.getMenus().addAll(fileMenu, userMenu);
        
        leftPane.setStyle("-fx-border-color: grey; -fx-border-width: 5px; -fx-alignment: top-center");
        leftPane.setOnScroll(event -> scrollInput(event));
        
        rightPane.setStyle("-fx-border-color: grey; -fx-border-width: 5px; -fx-alignment: center");
        
        
        this.setTop(menuBar);
    }
    
    
    private void openFile(ActionEvent e){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try{
                inputFile = file;
                inputDocument = PDDocument.load(file);
                inputRenderer = new PDFRenderer(inputDocument);
                currentInputPage = 0;
                BufferedImage bInputImage = inputRenderer.renderImage(currentInputPage);
                inputImage = SwingFXUtils.toFXImage(bInputImage, null);
                inputView = new ImageView();
                inputView.setImage(inputImage);
                leftPane.getChildren().add(inputView);
                setLeft(leftPane);
                
                
                outputDocument = JensenPropertyManagementParser.parse(inputFile);
                System.out.println(outputDocument.getNumberOfPages());
                outputRenderer = new PDFRenderer(outputDocument);
                currentOutputPage = 0;
                BufferedImage bOutputImage = outputRenderer.renderImage(currentOutputPage);
                outputImage = SwingFXUtils.toFXImage(bOutputImage, null);
                outputView = new ImageView();
                outputView.setImage(outputImage);
                rightPane.getChildren().add(outputView);
                setRight(rightPane);
                
                
                /*
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(inputDocument);
                output.setText(text);
                rightPane.getChildren().add(output);
                setRight(rightPane);
                */
                
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void scrollInput(ScrollEvent e){
        if(e.getDeltaY() > 0){
            if(currentInputPage != 0){
                try{
                    currentInputPage--;
                    BufferedImage bImage = inputRenderer.renderImage(currentInputPage);
                    inputImage = SwingFXUtils.toFXImage(bImage, null);
                    inputView.setImage(inputImage);
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        else if(e.getDeltaY() < 0){
            if(currentInputPage != inputDocument.getNumberOfPages() && (currentInputPage + 1 ) < inputDocument.getNumberOfPages()){
                try{
                    currentInputPage++;
                    BufferedImage bImage = inputRenderer.renderImage(currentInputPage);
                    inputImage = SwingFXUtils.toFXImage(bImage, null);
                    inputView.setImage(inputImage);
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public static void close() throws IOException{
        if(inputDocument != null){
            inputDocument.close();
        }
        if(outputDocument != null){
            outputDocument.close();
        }
    }
}

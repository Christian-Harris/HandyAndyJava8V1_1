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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import application.HandyAndyApplication;
import application.editor.Editor;

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
    private final MenuBar menuBar;
    
    private final Menu fileMenu;
    private final MenuItem openFile;
    
    private final Menu userMenu;
    private final MenuItem logoutItem;
    
    private final Pane leftPane;
    private  File inputFile;
    private  PDDocument inputDocument;
    private int currentInputPage;
    private  Image inputImage;
    private  ImageView inputView;
    
    private final Pane rightPane;
    private  File outputFile;
    private  PDDocument outputDocument;
    private int currentOutputPage;
    private  Image outputImage;
    private  ImageView outputView;
    
    private final Pane centerPane;
    
    private PDFRenderer inputRenderer;
    private PDFRenderer outputRenderer;
    private Editor editor;
    
    private final HandyAndyApplication application;
    
    public UserMenu(HandyAndyApplication application){
        this.application = application;
        menuBar = new MenuBar();
        inputView = new ImageView();
        leftPane = new StackPane();
        rightPane = new StackPane();
        centerPane = new StackPane();
        
        fileMenu = new Menu("File");
        openFile = new MenuItem("Open File");
        openFile.setOnAction(event -> openFile(event));
        fileMenu.getItems().add(openFile);
        
        userMenu = new Menu("User");
        logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(event -> logout(event));
        userMenu.getItems().add(logoutItem);
        
        menuBar.getMenus().addAll(fileMenu, userMenu);
        
        leftPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-alignment: top-center");
        leftPane.setOnScroll(event -> scrollInput(event));
        
        rightPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-alignment: center");
        
        centerPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px 1px 2px 1px; -fx-alignment: center");
        
        
        this.setTop(menuBar);
    }
    
    
    private void openFile(ActionEvent e){
        if(inputDocument != null && !(inputDocument.getDocument().isClosed())){
            try{
                inputDocument.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        if(outputDocument != null && (outputDocument.getDocument().isClosed())){
            try{
                outputDocument.close();
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
                this.inputFile = file;
                this.inputDocument = PDDocument.load(file);
                this.inputRenderer = new PDFRenderer(inputDocument);
                this.currentInputPage = 0;
                this.inputImage = SwingFXUtils.toFXImage(inputRenderer.renderImage(currentInputPage), null);
                this.inputView = new ImageView();
                this.inputView.setImage(inputImage);
                this.leftPane.getChildren().clear();
                this.leftPane.getChildren().add(inputView);
                this.setLeft(leftPane);
                
                editor = JensenPropertyManagementParser.parse(file);
                ScrollPane editorScroller = new ScrollPane();
                editorScroller.setContent(editor.getEditorBox());
                this.centerPane.getChildren().clear();
                this.centerPane.getChildren().add(editorScroller);
                this.setCenter(centerPane);
                
                this.outputDocument = editor.generateDocument();
                this.outputRenderer = new PDFRenderer(outputDocument);
                this.currentOutputPage = 0;
                this.outputImage = SwingFXUtils.toFXImage(outputRenderer.renderImage(currentOutputPage), null);
                this.outputView = new ImageView();
                this.outputView.setImage(outputImage);
                this.rightPane.getChildren().clear();
                this.rightPane.getChildren().add(outputView);
                this.setRight(rightPane);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void scrollInput(ScrollEvent e){
        if(e.getDeltaY() > 0){
            if(this.currentInputPage != 0){
                try{
                    this.currentInputPage--;
                    this.inputImage = SwingFXUtils.toFXImage(this.inputRenderer.renderImage(this.currentInputPage), null);
                    this.inputView.setImage(inputImage);
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        else if(e.getDeltaY() < 0){
            if(this.currentInputPage != this.inputDocument.getNumberOfPages() && (this.currentInputPage + 1 ) < this.inputDocument.getNumberOfPages()){
                try{
                    this.currentInputPage++;
                    this.inputImage = SwingFXUtils.toFXImage(this.inputRenderer.renderImage(this.currentInputPage), null);
                    this.inputView.setImage(this.inputImage);
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void close() throws IOException{
        if(this.inputDocument != null){
            this.inputDocument.close();
        }
        if(this.outputDocument != null){
            this.outputDocument.close();
        }
    }
    
    public void logout(ActionEvent e){
        this.application.setCurrentUser(null);
        this.application.changeToLoginMenu();
    }
    
}

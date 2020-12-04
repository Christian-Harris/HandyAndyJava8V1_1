package menu;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

import application.HandyAndyApplication;
import application.editor.Editor;
import handler.AddUserToWorkOrderHandler;
import handler.NewWorkOrderHandler;
import handler.OpenWorkOrderHandler;
import handler.SaveHandler;
import handler.ScrollInputHandler;

import java.io.IOException;
import java.sql.Connection;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author Christian Harris
 */

public final class UserMenu extends BorderPane{
    private final MenuBar menuBar;
    
    private final Menu fileMenu;
    private final MenuItem newWorkOrder;
    private final MenuItem openWorkOrder;
    private final MenuItem save;
    private final MenuItem export;
    
    private final Menu userMenu;
    private final MenuItem logoutItem;
    private final MenuItem addUserToWorkOrderItem;
    private final MenuItem addUser;
    
    private final Menu tools;
    private final MenuItem emailWorkOrder;
    
    private final Pane leftPane;
    private  PDDocument inputDocument;
    private int currentInputPage;
    private  ImageView inputView;
    
    private final Pane rightPane;
    
    ScrollPane editorScroller;
    private final Pane centerPane;
    
    private PDFRenderer inputRenderer;
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
        newWorkOrder = new MenuItem("New Work Order");
        newWorkOrder.setOnAction(new NewWorkOrderHandler(this));
        openWorkOrder = new MenuItem("Open Work Order");
        openWorkOrder.setOnAction(new OpenWorkOrderHandler(this));
        save = new MenuItem("Save");
        save.setOnAction(new SaveHandler(this));
        export = new MenuItem("Export");
        //export.setOnAction(new ExportHandler(this));
        fileMenu.getItems().addAll(newWorkOrder, openWorkOrder, save, export);
        
        userMenu = new Menu("User");
        logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(event -> logout(event));
        addUserToWorkOrderItem = new MenuItem("Add Worker");
        addUserToWorkOrderItem.setOnAction(new AddUserToWorkOrderHandler(this));
        addUser = new MenuItem("Add User");
        //addUser.setOnAction(new AddUserHandler(this));
        userMenu.getItems().addAll(logoutItem, addUserToWorkOrderItem, addUser);
        
        tools = new Menu("Tools");
        emailWorkOrder = new MenuItem("Email Work Order");
        //emailWorkOrder.setOnAction(new EmailWorkOrderHandler(this));
        
        menuBar.getMenus().addAll(fileMenu, userMenu, tools);
        
        leftPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-alignment: top-center");
        leftPane.setOnScroll(new ScrollInputHandler(this));
        
        rightPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-alignment: center");
        
        centerPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px 1px 2px 1px; -fx-alignment: center");
        
        inputView = new ImageView();
        leftPane.getChildren().add(inputView);
        
        editorScroller = new ScrollPane();
        centerPane.getChildren().add(editorScroller);
        
        
        this.setTop(menuBar);
        this.setLeft(leftPane);
        this.setCenter(centerPane);
        this.setRight(rightPane);
    }
    
    public void close() throws IOException{
        if(this.inputDocument != null){
            this.inputDocument.close();
        }
    }
    
    public void logout(ActionEvent e){
        this.application.setCurrentUser(null);
        this.application.changeToLoginMenu();
    }
    
    public Connection getDatabaseConnection(){
        return this.application.getDatabaseConnection();
    }
    
    public Editor getEditor(){
        return this.editor;
    }
    
    public PDDocument getInputDocument(){
        return this.inputDocument;
    }
    
    public void setInputDocument(PDDocument document) throws IOException{
        this.inputDocument = document;
        this.inputRenderer = new PDFRenderer(inputDocument);
        this.currentInputPage = 0;
        Image image = SwingFXUtils.toFXImage(inputRenderer.renderImage(currentInputPage), null);
        this.inputView.setImage(image);
    }
    
    public void setEditor(Editor editor){
        this.editor = editor;
        this.editorScroller.setContent(editor.getEditorBox());
        this.rightPane.getChildren().add(editor.getDocumentView());
    }
    
    public int getCurrentInputPage(){
        return this.currentInputPage;
    }
    
    public void setCurrentInputPage(int currentInputPage){
        if(currentInputPage >= 0){
            this.currentInputPage = currentInputPage;
        }
    }
    
    public void setInputImage(Image image){
        this.inputView.setImage(image);
    }
    
    public PDFRenderer getInputRenderer(){
        return this.inputRenderer;
    }
    
}

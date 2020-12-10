package menu;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import handler.LoginHandler;
import application.HandyAndyApplication;

import java.sql.Connection;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Christian Harris
 */
public final class LoginMenu extends StackPane{
    
    private final VBox loginBox;
    
    private final Label userLabel;
    private final TextField userTextField;
    private final HBox userBox;
    
    private final Label passwordLabel;
    private final PasswordField passwordField;
    private final HBox passwordBox;
    
    private final Button loginButton;
    
    private final Label loginMessage;
    private final HandyAndyApplication application;
    
    public LoginMenu(HandyAndyApplication application){
        this.application = application;
        userLabel = new Label("Username:");
        userLabel.setStyle("-fx-text-alignment: right; -fx-font: 16 helvetica");
        userTextField = new TextField();
        userTextField.setPrefSize(128, 16);
        userBox = new HBox(4, userLabel, userTextField);
        userBox.setStyle("-fx-alignment: center");
        
        passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-alignment: right; -fx-font: 16 helvetica");
        passwordField = new PasswordField();
        passwordField.setPrefSize(128, 16);
        passwordBox = new HBox(4, passwordLabel, passwordField);
        passwordBox.setStyle("-fx-alignment: center");
        
        loginButton = new Button("Login");
        loginButton.setOnAction(new LoginHandler(this));
        
        loginMessage = new Label("");
        
        loginBox = new VBox(8, userBox, passwordBox, loginButton, loginMessage);
        loginBox.setStyle("-fx-alignment: center");
        
        this.setStyle("-fx-background-color: #355E99; -fx-alignment: center");
        this.getChildren().add(loginBox);
        
    }
    
    public void reset(){
        this.userTextField.setText("");
        this.passwordField.setText("");
        this.loginMessage.setText("");
    }
    
    public String getUsername(){
        return this.userTextField.getText();
    }
    
    public String getPassword(){
        return Integer.toString(this.passwordField.getText().hashCode());
    }
    
    public void setLoginMessage(String message){
        this.loginMessage.setText(message);
    }
    
    public HandyAndyApplication getApplication(){
        return this.application;
    }
    
    public Connection getDatabaseConnection(){
        return this.application.getDatabaseConnection();
    }
}

package menu;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import handler.LoginHandler;

/**
 *
 * @author Christian Harris
 */
public class LoginMenu extends Pane{
    
    private final VBox loginBox;
    
    private final Label userLabel;
    private final TextField userTextField;
    private final HBox userBox;
    
    private final Label passwordLabel;
    private final PasswordField passwordField;
    private final HBox passwordBox;
    
    private final Button loginButton;
    
    private final Label loginMessage;
    
    public LoginMenu(){
        userLabel = new Label("Username: ");
        userTextField = new TextField("username");
        userBox = new HBox(userLabel, userTextField);
        
        passwordLabel = new Label("Password: ");
        passwordField = new PasswordField();
        passwordBox = new HBox(passwordLabel, passwordField);
        
        loginButton = new Button("Login");
        loginButton.setOnAction(new LoginHandler());
        
        loginMessage = new Label("");
        
        loginBox = new VBox(userBox, passwordBox, loginButton, loginMessage);
        
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
}

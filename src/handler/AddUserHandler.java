package handler;

import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import menu.UserMenu;

/**
 *
 * @author Christian Harris
 */
public class AddUserHandler implements EventHandler<ActionEvent>{
    private final UserMenu userMenu;
    
    private Label usernameLabel;
    private TextField usernameTF;
    private HBox usernameBox;
    private Label passwordLabel;
    private TextField passwordTF;
    private HBox passwordBox;
    private Label emailLabel;
    private TextField emailTF;
    private HBox emailBox;
    private Label firstNameLabel;
    private TextField firstNameTF;
    private HBox firstNameBox;
    private Label lastNameLabel;
    private TextField lastNameTF;
    private HBox lastNameBox;
    private Label middleInitialLabel;
    private TextField middleInitialTF;
    private HBox middleInitialBox;
    private Label addressStreetLabel;
    private TextField addressStreetTF;
    private HBox addressStreetBox;
    private Label addressCityLabel;
    private TextField addressCityTF;
    private HBox addressCityBox;
    private Label addressStateLabel;
    private TextField addressStateTF;
    private HBox addressStateBox;
    private Label addressZipLabel;
    private TextField addressZipTF;
    private HBox addressZipBox;
    private Label userTypeLabel;
    private TextField userTypeTF;
    private HBox userTypeBox;
    
    private Button confirmButton;
    private Label errorLabel;
    
    private Stage stage;
    private VBox pane;
    
    public AddUserHandler(UserMenu userMenu){
        this.userMenu = userMenu;
        usernameLabel = new Label("*Username:");
        usernameTF = new TextField("");
        usernameBox = new HBox(usernameLabel, usernameTF);
        passwordLabel = new Label("*Pasword");
        passwordTF = new TextField("");
        passwordBox = new HBox(passwordLabel, passwordTF);
        emailLabel = new Label("Email:");
        emailTF = new TextField("");
        emailBox = new HBox(emailLabel, emailTF);
        firstNameLabel = new Label("*First Name:");
        firstNameTF = new TextField("");
        firstNameBox = new HBox(firstNameLabel, firstNameTF);
        lastNameLabel = new Label("*Last Name:");
        lastNameTF = new TextField("");
        lastNameBox = new HBox(lastNameLabel, lastNameTF);
        middleInitialLabel = new Label("Middle Initial");
        middleInitialTF = new TextField("");
        middleInitialBox = new HBox(middleInitialLabel, middleInitialTF);
        addressStreetLabel = new Label("Addres Street:");
        addressStreetTF = new TextField("");
        addressStreetBox = new HBox(addressStreetLabel, addressStreetTF);
        addressCityLabel = new Label("Address City:");
        addressCityTF = new TextField("");
        addressCityBox = new HBox(addressCityLabel, addressCityTF);
        addressStateLabel = new Label("Address State:");
        addressStateTF = new TextField("");
        addressStateBox = new HBox(addressStateLabel, addressStateTF);
        addressZipLabel = new Label("Address Zip");
        addressZipTF = new TextField("");
        addressZipBox = new HBox(addressZipLabel, addressZipTF);
        userTypeLabel = new Label("User Type");
        userTypeTF = new TextField("");
        
        confirmButton = new Button("Confirm");
        confirmButton.setOnAction(new ConfirmHandler());
        errorLabel = new Label("");
        
        VBox pane = new VBox(usernameBox, passwordBox, emailBox, firstNameBox, lastNameBox, middleInitialBox, addressStreetBox, addressCityBox, addressStateBox, addressZipBox, userTypeBox, confirmButton, errorLabel);
        Scene scene = new Scene(pane, 500, 1000);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New User");
        stage.show();

    }
    
    @Override
    public void handle(ActionEvent e){
        if(this.userMenu.getEditor() != null){
            stage = new Stage();
            usernameLabel = new Label("*Username:");
            usernameTF = new TextField();
            passwordLabel = new Label("*Pasword");
            passwordTF = new TextField();
            emailLabel = new Label("*Email:");
            emailTF = new TextField();
            firstNameLabel = new Label("*First Name:");
            firstNameTF = new TextField();
            lastNameLabel = new Label("*Last Name:");
            lastNameTF = new TextField();
            middleInitialLabel = new Label("Middle Initial");
            middleInitialTF = new TextField();
            
            pane = new VBox();
            
        }
    }
    
    private class ConfirmHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            String errorString = "";
            if(usernameTF.getText().trim().equals("")){
                errorString += "Username is required. ";
            }
            if(passwordTF.getText().trim().equals("")){
                errorString += "Password is required. ";
            }
            if(emailTF.getText().trim().equals("")){
                errorString += "Email is required. ";
            }
            if(firstNameTF.getText().trim().equals("")){
                errorString += "First Name is required. ";
            }
            if(lastNameTF.getText().trim().equals("")){
                errorString += "Last Name is required.";
            }
            
            if(errorString.length() > 0){
                errorLabel.setText(errorString);
            }
            else{
                Connection databaseConnection = userMenu.getDatabaseConnection();
                String fields = "username, password, email, firstName, lastName";
                if(!middleInitialTF.getText().trim().equals("")){
                    fields += ", middleInitial";
                }
                if(!addressStreetTF.getText().trim().equals("")){
                    fields += ", addressStreet";
                }
                if(!addressCityTF.getText().trim().equals("")){
                    fields += ", addressCity";
                }
                if(!addressStateTF.getText().trim().equals("")){
                    fields += ", addressState";
                }
                if(!addressZipTF.getText().trim().equals("")){
                    fields += ", addressZip";
                }
                if(!userTypeTF.getText().trim().equals("")){
                    fields += ", userType";
                }
                //String query = "INSERT INTO users (" + fields + ") VALUES ('cHarris', 655583201, 'harris.chrw@gmail.com', 'Christian', 'Harris', 'W', '86 East 750 South', 'St. George', 'UT', '84770', 'Admin')";
            }
        }
    }
    
}

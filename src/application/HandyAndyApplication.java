package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

import menu.LoginMenu;
import user.User;
import menu.UserMenu;

/**
 *
 * @author Christian Harris
 */

public final class HandyAndyApplication extends Application{
    private User currentUser = null;
    
    private String applicationUsername = "application";
    private String applicationPassword = "applicationPassword";
    private String connectionString = "jdbc:mysql://localhost:3306/handyandydb";
    private Connection databaseConnection = null;
    
    private LoginMenu loginMenu;
    private UserMenu userMenu;
    
    private Scene scene;
    
    public static void main(String[] args){
        launch(args);
    } 
    
    @Override
    public void start(Stage stage){
        stage.setOnCloseRequest(event -> close(event));
        try{
            this.connectToDatabase(connectionString, applicationUsername, applicationPassword);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        
        loginMenu = new LoginMenu(this);
        userMenu = new UserMenu(this);
        
        scene = new Scene(loginMenu, 600, 400);
        
        stage.setScene(scene);
        stage.setTitle("HandyAndyApplication");
        stage.show();
    }
    
    public void changeToLoginMenu(){
        this.scene.setRoot(loginMenu);
    }
    
    public void changeToUserMenu(){
        this.scene.setRoot(userMenu);
    }
    
    public void setCurrentUser(User user){
        currentUser = user;
    }
    
    public User getCurrentUser(){
        return this.currentUser;
    }
    
    public Connection getDatabaseConnection(){
        return this.databaseConnection;
    }
    
    public void setConnectionParameters(String connectionString, String applicationUsername, String applicationPassword){
        this.connectionString = connectionString;
        this.applicationUsername = applicationUsername;
        this.applicationPassword = applicationPassword;
    }
    
    public void connectToDatabase(String connectionString, String applicationUsername, String applicationPassword) throws SQLException{
        databaseConnection = DriverManager.getConnection(connectionString, applicationUsername, applicationPassword);
    }
    
    public void close(WindowEvent e){
        try{
            this.databaseConnection.close();
            this.userMenu.close();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        Platform.exit();
        System.exit(0);
    }
}

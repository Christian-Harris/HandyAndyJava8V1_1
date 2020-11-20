package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import menu.LoginMenu;
import user.User;
import menu.UserMenu;

/**
 *
 * @author Christian Harris
 */

public class HandyAndyApplication extends Application{
    
    private static User currentUser = null;
    
    private static final String APP_USER = "application";
    private static final String APP_PSWD = "applicationPassword";
    private static final String CONN_STR = "jdbc:mysql://localhost:3306/handyandydb";
    private static Connection databaseConnection = null;
    
    private static LoginMenu loginMenu;
    private static UserMenu userMenu;
    
    private static Scene scene;
    
    @Override
    public void start(Stage stage){
        
        try{
            databaseConnection = DriverManager.getConnection(CONN_STR, APP_USER, APP_PSWD);
            System.out.println("Connected to database.");
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                try{
                    databaseConnection.close();
                    System.out.println("Disconnected from database.");
                    UserMenu.close();
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
        });
        
        loginMenu = new LoginMenu();
        userMenu = new UserMenu();
        
        scene = new Scene(loginMenu, 600, 400);
        
        stage.setScene(scene);
        stage.setTitle("HandyAndyApplication");
        stage.show();
    }
    
    public static void setCurrentUser(User user){
        currentUser = user;
    }
    
    public static Connection getDatabaseConnection(){
        return HandyAndyApplication.databaseConnection;
    }
    
    public static void main(String[] args){
        launch(args);
    } 
    
    public static void setSceneUserMenu(){
        scene.setRoot(userMenu);
    }
    
    public static void setSceneLoginMenu(){
        scene.setRoot(loginMenu);
    }
}

package handler;

import application.HandyAndyApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import menu.LoginMenu;
import user.User;
import user.UserType;

/**
 *
 * @author Christian Harris
 */
public class LoginHandler implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent e){   
        try{
            LoginMenu loginMenu = ((LoginMenu)((VBox)((Button)e.getSource()).getParent()).getParent());
            Connection databaseConnection = loginMenu.getApplication().getDatabaseConnection(); 
            String query = "SELECT * from users WHERE username = " + "'" + loginMenu.getUsername() + "'";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(loginMenu.getPassword().equals(resultSet.getString("password"))){
                    User user = new User(resultSet.getString("username"), resultSet.getString("password"), UserType.getUserType(resultSet.getString("userType")));
                    if(resultSet.getString("email") != null){
                        user.setEmail(resultSet.getString("email"));
                    }
                    loginMenu.reset();
                    loginMenu.getApplication().setCurrentUser(user);
                    loginMenu.getApplication().changeToUserMenu();
                }
                else{
                    loginMenu.setLoginMessage("Invalid username or password.");
                }
            }
            else{
                loginMenu.setLoginMessage("Invalid username or password.");
            }
            
            statement.close();
            resultSet.close();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
}

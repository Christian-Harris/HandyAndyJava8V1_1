package handler;

import application.HandyAndyApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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
        Connection databaseConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        LoginMenu loginMenu = null;
        
        try{
            databaseConnection = HandyAndyApplication.getDatabaseConnection();      
            //statement = databaseConnection.createStatement();
            loginMenu = (LoginMenu)(((VBox)(((Button)(e.getSource())).getParent())).getParent());
            String query = "SELECT * from users WHERE username = " + "'" + loginMenu.getUsername() + "'";
            statement = databaseConnection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(loginMenu.getPassword().equals(resultSet.getString("password"))){
                    User user = new User(resultSet.getString("username"), resultSet.getString("password"), UserType.getUserType(resultSet.getString("userType")));
                    if(resultSet.getString("email") != null){
                        user.setEmail(resultSet.getString("email"));
                    }
                    loginMenu.reset();
                    HandyAndyApplication.setCurrentUser(user);
                    HandyAndyApplication.setSceneUserMenu();
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

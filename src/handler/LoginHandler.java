package handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import menu.LoginMenu;
import user.User;
import user.UserType;

/**
 *The LoginHandler class handles the action of logging into the Handy Andy Application. The handler reads the input from the login
 * prompts and queries the Handy Andy database for a matching user. If a match is found a database connection is formed and the appliation
 * loads the user menu.
 * @author Christian Harris
 */
public class LoginHandler implements EventHandler<ActionEvent>{
    private final LoginMenu loginMenu;
    
    public LoginHandler(LoginMenu loginMenu){
        this.loginMenu = loginMenu;
    }
    @Override
    public void handle(ActionEvent e){   
        try{
            //LoginMenu loginMenu = ((LoginMenu)((VBox)((Button)e.getSource()).getParent()).getParent());
            Connection databaseConnection = loginMenu.getDatabaseConnection(); 
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

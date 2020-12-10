package handler;

import application.Emailer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import menu.UserMenu;

/**
 *The EmailWorkOrderHandler object handles the action of creating an Emailer object and running that object on a thread.
 * The Emailer is populated with the current work orders PDDocument and is mailed to all users who have been added to the current
 * work order.
 * @author Christian Harris
 */
public class EmailWorkOrderHandler implements EventHandler<ActionEvent>{
    private final UserMenu userMenu;
    
    public EmailWorkOrderHandler(UserMenu userMenu){
        this.userMenu = userMenu;
    }
    
    @Override
    public void handle(ActionEvent e){
        if(this.userMenu.getEditor() != null){
            try{
                Connection databaseConnection = userMenu.getDatabaseConnection();
                String query = "SELECT * from users";
                PreparedStatement statement = databaseConnection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    String recipient = resultSet.getString("email");
                    if(this.userMenu.getEditor().hasUser(resultSet.getString("username"))){
                        Emailer emailer = new Emailer(recipient, this.userMenu.getEditor().getDocument());
                        Thread thread = new Thread(emailer);
                        thread.start();
                    }
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}

package menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Christian Harris
 */
public class AddUserToWorkOrderMenu extends VBox{
    
    private final UserMenu userMenu;
    private final Stage stage;
    private final VBox usersBox;
    
    private final Button confirm;
    
    public AddUserToWorkOrderMenu(UserMenu userMenu, Stage stage){
        this.userMenu = userMenu;
        this.stage = stage;
        confirm = new Button("Confirm");
        confirm.setOnAction(new ConfirmHandler());
        usersBox = new VBox();
        this.getChildren().addAll(usersBox, confirm);
        try{
            Connection databaseConnection = userMenu.getDatabaseConnection();
            String query = "SELECT * from users";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String username = resultSet.getString("username");
                User user = new User(username, resultSet.getString("firstName"), resultSet.getString("lastName"));
                if(this.userMenu.getEditor().hasUser(resultSet.getString("username"))){
                    user.setSelected(true);
                }
                usersBox.getChildren().add(user);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    class ConfirmHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            userMenu.getEditor().clearWorkOrderUsers();
            for(int i = 0; i < usersBox.getChildren().size(); i++){
                if(((User)usersBox.getChildren().get(i)).isSelected()){
                    userMenu.getEditor().addWorkOrderUser(((User)usersBox.getChildren().get(i)).getUsername());
                }
            }
            stage.close();
        }
    }
    
    class User extends HBox{
        CheckBox checkBox;
        String username;
        Label firstName;
        Label lastName;
        
        User(String username, String firstName, String lastName){
            this.checkBox = new CheckBox();
            this.checkBox.setSelected(false);
            this.username = username;
            this.firstName = new Label(firstName);
            this.lastName = new Label(lastName);
            this.getChildren().addAll(checkBox, this.firstName, this.lastName);
        }
        
        boolean isSelected(){
            return this.checkBox.isSelected();
        }
        
        void setSelected(boolean selected){
            this.checkBox.setSelected(selected);
        }
        
        String getUsername(){
            return this.username;
        }
    }
    
}

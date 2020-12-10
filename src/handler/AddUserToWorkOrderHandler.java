package handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import menu.AddUserToWorkOrderMenu;
import menu.UserMenu;

/**
 *The AddUserToWorkOrderHandler class handles the action of adding a user defined in the Handy Andy database to the currently loaded work
 * order. The handler generates a check menu with all of the current users. The chosen users can then be selected for future processing.
 * @author Christian Harris
 */
public class AddUserToWorkOrderHandler implements EventHandler<ActionEvent>{
    private final UserMenu userMenu;
    Stage stage;
    
    
    public AddUserToWorkOrderHandler(UserMenu userMenu){
        this.userMenu = userMenu;
    }
    
    @Override
    public void handle(ActionEvent e){
        if(userMenu.getEditor() != null){
            stage = new Stage();
            AddUserToWorkOrderMenu menu = new AddUserToWorkOrderMenu(this.userMenu, this.stage);
            Scene scene = new Scene(menu, 400, 800);
            stage.setScene(scene);
            stage.show();
        }
        
    }
}

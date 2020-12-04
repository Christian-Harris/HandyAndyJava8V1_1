package handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import menu.AddUserToWorkOrderMenu;
import menu.UserMenu;

/**
 *
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

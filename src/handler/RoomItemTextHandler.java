package handler;

import application.editor.RoomItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The RoomItemTextHandler class handles the action of changing the text of a RoomItem. The handler simply calls the roomItem's update method.
 * @author Christian Harris
 */
public class RoomItemTextHandler implements EventHandler<ActionEvent>{
    private final RoomItem roomItem;
    
    public RoomItemTextHandler(RoomItem roomItem){
        this.roomItem = roomItem;
    }
    
    @Override
    public void handle(ActionEvent e){
        roomItem.update();
    }
}

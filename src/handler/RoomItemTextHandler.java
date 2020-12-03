package handler;

import application.editor.RoomItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
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

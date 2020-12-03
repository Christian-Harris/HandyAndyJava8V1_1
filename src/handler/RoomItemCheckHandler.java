package handler;

import application.editor.RoomItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Christian Harris
 */
public class RoomItemCheckHandler implements EventHandler<ActionEvent>{
    private final RoomItem roomItem;
    
    public RoomItemCheckHandler(RoomItem roomItem){
        this.roomItem = roomItem;
    }
    
    @Override
    public void handle(ActionEvent e){
        roomItem.update();
    }
}

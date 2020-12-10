package handler;

import application.editor.RoomItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The RoomItemCheckHandler handles the event of selecting or deselecting a RoomItem. Only selected items are rendered. After the selection
 * is made a call to the RoomItems update method is made.
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

package handler;

import application.editor.Room;
import application.editor.RoomItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The NewRoomItemHandler class handles the addition of a new RoomItem object to the Editor. A new reference is created and added to the editor
 * followed by a call to the Rooms update method. This method allows the Room to reconstruct itself rendering the new RoomItem.
 * @author Christian Harris
 */
public class NewRoomItemHandler implements EventHandler<ActionEvent>{
    private final Room room;
    
    public NewRoomItemHandler(Room room){
        this.room = room;
    }
    @Override
    public void handle(ActionEvent e){
        this.room.addRoomItem(new RoomItem(this.room));
        this.room.update();
    }
}

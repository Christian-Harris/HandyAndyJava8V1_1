package handler;

import application.editor.Room;
import application.editor.RoomItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
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

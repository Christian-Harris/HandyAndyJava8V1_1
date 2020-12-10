package handler;

import application.editor.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The RoomCheckHandler class handles the action of selecting or deselecting a room in the editor. Only selected rooms are rendered
 * to the output so once the selection is changed a call to the rooms update is made.
 * @author Christian Harris
 */
public class RoomCheckHandler implements EventHandler<ActionEvent>{
    private final Room room;
    
    public RoomCheckHandler(Room room){
        this.room = room;
    }
    
    @Override
    public void handle(ActionEvent e){
        for(int i = 0; i < room.getNumberOfRoomItems(); i++){
            if(!room.isSelected()){
                room.getRoomItem(i).setSelected(false);
            }
            else{
                room.getRoomItem(i).setSelected(true);
            }
        }
        room.update();
    }
    
}

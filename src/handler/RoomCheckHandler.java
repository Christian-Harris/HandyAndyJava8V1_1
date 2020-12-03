package handler;

import application.editor.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
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

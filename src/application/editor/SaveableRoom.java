package application.editor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *The SaveableRoom class stores all the necessary information to generate a Room object. It's purpose is to implement the Serializable
 *interface so that the state of a Room object can be saved.
 * @author Christian Harris
 */
public class SaveableRoom implements Serializable{
    String name;
    boolean checked;
    ArrayList<SaveableRoomItem> roomItems;
    
    public SaveableRoom(String name, boolean checked, ArrayList<SaveableRoomItem> roomItems){
        this.name = name;
        this.checked = checked;
        this.roomItems = roomItems;
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean getChecked(){
        return this.checked;
    }
    
    public ArrayList<SaveableRoomItem> getRoomItems(){
        return this.roomItems;
    }
}

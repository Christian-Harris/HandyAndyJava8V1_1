package application.editor;

import java.io.Serializable;

/**
 *The SaveableRoomItem class stores all the necessary information to generate a RoomItem object. It's purpose is to implement the Serializable
 *interface so that the state of a RoomItem object can be saved.
 * @author Christian Harris
 */
public class SaveableRoomItem implements Serializable{
    String name;
    boolean checked;
    
    public SaveableRoomItem(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean getChecked(){
        return this.checked;
    }
}

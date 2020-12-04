package application.editor;

import java.io.Serializable;

/**
 *
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

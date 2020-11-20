package application.editor;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;

/**
 *
 * @author Christian Harris
 */
public final class Room extends TitledPane{
    
    private static ArrayList<RoomItem> roomItems = new ArrayList<RoomItem>();
    
    public Room(){
        super();
    }
    public Room(String title, Node content){
        super(title, content);
    }
}

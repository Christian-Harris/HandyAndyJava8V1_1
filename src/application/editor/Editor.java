package application.editor;

import java.util.ArrayList;

import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;

/**
 *
 * @author Christian Harris
 */

public final class Editor extends VBox{
    private static ArrayList<Room> rooms;
    private static Accordion editor;
    
    public Editor(){
        super();
        rooms = new ArrayList<Room>();
        editor = new Accordion();
    }
    
    protected static void update(){
        editor.getPanes().clear();
        editor.getPanes().addAll(rooms);
    }
    
    public static void addRoom(Room room){
        rooms.add(room);
        update();
    }
    
    public static void removeRoom(Room room){
        rooms.remove(room);
        update();
    }
}

package application.editor;

import java.util.ArrayList;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Christian Harris
 */

public final class Editor extends VBox{
    
    String jobNumber;
    String address;
    private static ArrayList<Room> rooms;
    private static Accordion editor;
    
    public Editor(){
        rooms = new ArrayList<Room>();
        editor = new Accordion();
        this.getChildren().add(editor);
    }
    
    public void setJobNumber(String jobNumber){
        this.jobNumber = jobNumber;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    protected void update(){
        editor.getPanes().clear();
        editor.getPanes().add(new TitledPane(jobNumber, new Pane()));
        editor.getPanes().addAll(rooms);
    }
    
    public void addRoom(Room room){
        rooms.add(room);
        update();
    }
    
    public void removeRoom(Room room){
        rooms.remove(room);
        update();
    }
    
    public static Editor testBuild(){
        Editor testEditor = new Editor();
        testEditor.setJobNumber("1234");
        Room room = new Room("My Room");
        RoomItem roomItem = new RoomItem("Clean");
        room.addRoomItem(roomItem);
        testEditor.addRoom(room);
        return testEditor;
    }
}

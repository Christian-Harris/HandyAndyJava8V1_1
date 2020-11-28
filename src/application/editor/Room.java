package application.editor;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Christian Harris
 */
public final class Room extends TitledPane{
    private final Label roomName;
    private final Button rename;
    private final HBox nameBox;
    private final ArrayList<RoomItem> roomItems;
    private final VBox roomItemsBox;
    
    public Room(){
        super("untitled", new Pane());
        this.roomName = new Label("untitled");
        rename = new Button("Rename");
        Region spacing = new Region();
        roomItems = new ArrayList<RoomItem>();
        
        HBox.setHgrow(spacing, Priority.ALWAYS);
        nameBox = new HBox(this.roomName, spacing, rename);
        roomItemsBox = new VBox(nameBox);
        this.setContent(roomItemsBox);
    }
    
    public Room(String roomName){
        super(roomName, new Pane());
        this.roomName = new Label(roomName);
        rename = new Button("Rename");
        Region spacing = new Region();
        roomItems = new ArrayList<RoomItem>();
        
        HBox.setHgrow(spacing, Priority.ALWAYS);
        nameBox = new HBox(this.roomName, spacing, rename);
        roomItemsBox = new VBox(nameBox);
        this.setContent(roomItemsBox);
    }
    public Room(String roomName, ArrayList<RoomItem> roomItems){
        this.roomName = new Label(roomName);
        rename = new Button("Rename");
        Region spacing = new Region();
        this.roomItems = roomItems;
        
        HBox.setHgrow(spacing, Priority.ALWAYS);
        nameBox = new HBox(this.roomName, spacing, rename);
        roomItemsBox = new VBox(nameBox);
        for(int i = 0; i < roomItems.size(); i++){
            roomItemsBox.getChildren().add(roomItems.get(i));
        }
        this.setContent(roomItemsBox);
    }
    
    public void addRoomItem(RoomItem roomItem){
        this.roomItems.add(roomItem);
    }
}

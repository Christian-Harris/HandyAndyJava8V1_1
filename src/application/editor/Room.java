package application.editor;

//import java.util.ArrayList;

import handler.NewRoomItemHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Christian Harris
 */
public final class Room extends TitledPane{
    
    private final Editor editor;
    private final VBox contentPane;
    private final VBox roomItemPane;
    private final HBox controlPane;
    
    private final Button newItem;
    private final Button renameRoom;
    private final CheckBox checkBox;
    
    public Room(Editor editor){
        this.editor = editor;
        newItem = new Button("New Item");
        newItem.setOnAction(new NewRoomItemHandler(this));
        renameRoom = new Button("Rename Room");
        checkBox = new CheckBox();
        checkBox.setSelected(true);
        
        controlPane = new HBox(12, newItem, renameRoom, checkBox);
        
        roomItemPane = new VBox();
        contentPane = new VBox(roomItemPane, controlPane);
        
        this.setText("untitled");
        this.setContent(contentPane);
    }
    
    public Room(Editor editor, String text){
        this(editor);
        this.setText(text);
    }
    
    public void setTitle(String title){
        this.setText(title);
        this.update();
    }
    
    public String getTitle(){
        return this.getText();
    }
    
    /*public ArrayList<RoomItem> getRoomItems(){
        return this.roomItems;
    }*/
    
    public boolean isSelected(){
        return this.checkBox.isSelected();
    }
    
    public void addRoomItem(RoomItem roomItem){
        //this.contentPane.getChildren().add(0, roomItem);
        this.roomItemPane.getChildren().add(roomItem);
    }
    
    public RoomItem getRoomItem(int index){
        return (RoomItem)(this.roomItemPane.getChildren().get(index));
    }
    
    public int getNumberOfRoomItems(){
        return this.roomItemPane.getChildren().size();
    }
    
    public void update(){
        editor.generate();
    }
}

package application.editor;

import handler.NewRoomItemHandler;
import handler.RenameRoomHandler;
import handler.RoomCheckHandler;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;

/**
 *The Room class is a container object for the Editor class. This class extends TitledPane and adds various controls for added functionality.
 * In particular the room controls generating new RoomItems for itself, the ability to rename itself, and a selected state.
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
        renameRoom.setOnAction(new RenameRoomHandler(this));
        checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.setOnAction(new RoomCheckHandler(this));
        
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
    
    public Room(Editor editor, String text, boolean checked){
        this(editor, text);
        this.checkBox.setSelected(checked);
    }
    
    public void setTitle(String title){
        this.setText(title);
        this.update();
    }
    
    public String getTitle(){
        return this.getText();
    }
    
    public boolean isSelected(){
        return this.checkBox.isSelected();
    }
    
    public void addRoomItem(RoomItem roomItem){
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
    
    public int getNumberOfCheckedRoomItems(){
        int count = 0;
        for(int i = 0; i < this.getNumberOfRoomItems(); i++){
            if(this.getRoomItem(i).isSelected()){
                count++;
            }
        }
        return count;
    }
    
    public SaveableRoom generateSaveableRoom(){
        ArrayList<SaveableRoomItem> saveableRoomItems = new ArrayList<>();
        for(int i = 0; i < this.roomItemPane.getChildren().size(); i++){
            saveableRoomItems.add(((RoomItem)this.roomItemPane.getChildren().get(i)).generateSaveableRoomItem());
        }
        return new SaveableRoom(this.getText(), this.checkBox.isSelected(), saveableRoomItems);
    }
}

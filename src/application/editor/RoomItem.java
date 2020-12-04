package application.editor;

import handler.RoomItemCheckHandler;
import handler.RoomItemTextHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


/**
 *
 * @author Christian Harris
 */
public final class RoomItem extends HBox{
    private final Room room;
    private final TextField textField;
    private final CheckBox checkBox;
    
    public RoomItem(Room room){
        this.room = room;
        textField = new TextField("");
        //textArea.setWrapText(true);
        textField.setOnAction(new RoomItemTextHandler(this));
        checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.setOnAction(new RoomItemCheckHandler(this));
        this.getChildren().addAll(checkBox, textField);
    }
    
    public RoomItem(Room room, String text){
        this(room);
        textField.setText(text);
    }
    
    public RoomItem(Room room, String text, boolean checked){
        this(room, text);
        this.checkBox.setSelected(checked);
    }
    
    public String getText(){
        return textField.getText();
    }
    
    public boolean isSelected(){
        return this.checkBox.isSelected();
    }
    
    public void setSelected(boolean selected){
        this.checkBox.setSelected(selected);
    }
    
    public void update(){
        room.update();
    }
    
    public SaveableRoomItem generateSaveableRoomItem(){
        SaveableRoomItem value = new SaveableRoomItem(this.textField.getText(), this.checkBox.isSelected());
        return value;
    }
}

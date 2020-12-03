package application.editor;

import handler.RoomItemCheckHandler;
import handler.RoomItemTextHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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
}

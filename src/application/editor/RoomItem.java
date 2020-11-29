package application.editor;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 *
 * @author Christian Harris
 */
public final class RoomItem extends HBox{
    
    private final TextArea textArea;
    private final CheckBox checkBox;
    
    public RoomItem(){
        textArea = new TextArea("");
        textArea.setWrapText(true);
        checkBox = new CheckBox();
        checkBox.setSelected(true);
        this.getChildren().addAll(checkBox, textArea);
    }
    
    public RoomItem(String text){
        this();
        textArea.setText(text);
    }
    
    public String getText(){
        return textArea.getText();
    }
    
    public boolean isSelected(){
        return this.checkBox.isSelected();
    }
}

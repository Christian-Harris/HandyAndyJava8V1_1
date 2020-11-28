package application.editor;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 *
 * @author Christian Harris
 */
public final class RoomItem extends VBox{
    
    private final TextArea textArea;
    
    private final HBox buttonBox;
    private final CheckBox checkBox;
    private final Button delete;
    
    public RoomItem(){
        textArea = new TextArea();
        checkBox = new CheckBox();
        delete = new Button("Delete");
        Region spacing = new Region();
        buttonBox = new HBox(checkBox, delete);
        
        checkBox.setSelected(true);
        HBox.setHgrow(spacing, Priority.ALWAYS);
        
        this.getChildren().addAll(textArea, buttonBox);
        
        
    }
    
    public RoomItem(String text){
        this();
        textArea.setText(text);
    }
}

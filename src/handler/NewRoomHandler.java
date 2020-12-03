package handler;

import application.editor.Editor;
import application.editor.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Christian Harris
 */
public class NewRoomHandler implements EventHandler<ActionEvent>{
    private final Editor editor;
    
    public NewRoomHandler(Editor editor){
        this.editor = editor;
    }
    
    @Override
    public void handle(ActionEvent e){
        this.editor.addRoom(new Room(editor));
        this.editor.generate();
    }
    
    
}

package handler;

import application.editor.Editor;
import application.editor.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The NewRoomHandler class handles the addition of a new Room object to the Editor. A new reference is created and added to the editor
 * followed by a call to the editors generate method. This method allows the editor to reconstruct itself rendering the new Room.
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

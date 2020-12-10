package handler;

import application.editor.Editor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UpdateHandler implements EventHandler<ActionEvent>{
    private final Editor editor;
    
    public UpdateHandler(Editor editor){
        this.editor = editor;
    }
    
    @Override
    public void handle(ActionEvent e){
        editor.generate();
    }
}

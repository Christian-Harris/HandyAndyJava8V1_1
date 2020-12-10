package handler;

import application.editor.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The RenameRoomHandler class handles the dialog required to allow a user to rename a room. When the handle is called a dialog opens
 * allowing the user to enter the new room name and the confirm with a button.
 * @author Christian Harris
 */
public class RenameRoomHandler implements EventHandler<ActionEvent>{
    private final Room room;
    Stage stage;
    VBox pane;
    TextField textField;
    Button confirm;
    Scene scene;
    
    public RenameRoomHandler(Room room){
        this.room = room;
    }
    
    @Override
    public void handle(ActionEvent e){
        ConfirmHandler confirmHandler = new ConfirmHandler();
        stage = new Stage();
        pane = new VBox();
        textField = new TextField("");
        textField.setOnAction(confirmHandler);
        confirm = new Button("Confirm");
        confirm.setOnAction(confirmHandler);
        pane.getChildren().addAll(textField, confirm);
        scene = new Scene(pane, 150, 150);
        stage.setScene(scene);
        stage.show();
    }
    
    class ConfirmHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            room.setText(textField.getText());
            room.update();
            stage.close();
        }
    }
}

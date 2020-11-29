package application.editor;

import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Christian Harris
 */

public final class Editor extends VBox{
    
    private final TextField jobNumber;
    private final TextField address;
    private final HBox jobInfo;
    private final Accordion editor;
    private final Button newRoom;
    private final HBox controlPane;
    
    
    public Editor(){
        jobNumber = new TextField("");
        address = new TextField("");
        jobInfo = new HBox(12, jobNumber, address);
        editor = new Accordion();
        newRoom = new Button("New Room");
        controlPane = new HBox(12, newRoom);
        this.getChildren().addAll(jobInfo, editor, controlPane);
    }
    
    public void setJobNumber(String jobNumber){
        this.jobNumber.setText(jobNumber);
    }
    
    public String getJobNumber(){
        return this.jobNumber.getText();
    }
    
    public void setAddress(String address){
        this.address.setText(address);
    }
    
    public String getAddress(){
        return this.address.getText();
    }
    
    public void addRoom(Room room){
        editor.getPanes().add(0, room);
    }
    
    public static Editor testBuild(){
        Editor testEditor = new Editor();
        testEditor.setJobNumber("Job Number");
        testEditor.setAddress("Address");
        Room room = new Room("My Room");
        RoomItem roomItem1 = new RoomItem("Room Item 1");
        RoomItem roomItem2 = new RoomItem("Room Item 2");
        
        room.addRoomItem(roomItem1);
        room.addRoomItem(roomItem2);
        testEditor.addRoom(room);
        return testEditor;
    }
}

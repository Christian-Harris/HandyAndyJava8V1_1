package application.editor;

import java.util.ArrayList;
import handler.NewRoomHandler;
import handler.UpdateHandler;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *The Editor class controls the dynamic editing of a parsed Handy Andy work order. The primary editor takes the form of a JavaFX
 * accordion. Changes made within the accordion are instantly rendered out into an output PDDocument which is also viewed as part of 
 * the editor.
 * @author Christian Harris
 */

public final class Editor{
    
    private final Label jobNumberLabel;
    private final Label addressLabel;
    private final VBox jobInfoLabel;
    private final TextField jobNumber;
    private final TextField address;
    private final VBox jobInfoText;
    private final HBox jobInfo;
    private final Accordion editor;
    private final Button newRoom;
    //private final Button update;
    private final HBox controlPane;
    private final VBox editorBox;
    
    private PDDocument document;
    private final ImageView documentView;
    private int currentOutputPage;
    
    private final ArrayList<String> workOrderUsers;
    
    public Editor(){
        UpdateHandler updateHandler = new UpdateHandler(this);
        jobNumberLabel = new Label("Job Number:");
        jobNumberLabel.setStyle("-fx-text-alignment: right");
        addressLabel = new Label("Address:");
        addressLabel.setStyle("-fx-text-alignment: right");
        jobNumber = new TextField("");
        jobNumber.setOnAction(updateHandler);
        address = new TextField("");
        address.setPrefWidth(200);
        address.setOnAction(updateHandler);
        jobInfoLabel = new VBox(jobNumberLabel, addressLabel);
        jobInfoLabel.setStyle("-fx-alignment: center-right");
        jobInfoText = new VBox(jobNumber, address);
        jobInfo = new HBox(4, jobInfoLabel, jobInfoText);
        editor = new Accordion();
        newRoom = new Button("New Room");
        newRoom.setOnAction(new NewRoomHandler(this));
        //update = new Button("Update");
        //update.setOnAction(updateHandler);
        //controlPane = new HBox(12, newRoom, update);
        controlPane = new HBox(12, newRoom);
        editorBox = new VBox(jobInfo, editor, controlPane);
        editorBox.setStyle("-fx-padding: 12px");
        currentOutputPage = 0;
        documentView = new ImageView();
        workOrderUsers = new ArrayList<>();
        //editor.setStyle("-fx-border-width: 4px; -fx-border-color: blue; -fx-scale-shape: true");
    }
    
    public Editor(SaveableEditor sEditor){
       UpdateHandler updateHandler = new UpdateHandler(this);
        jobNumberLabel = new Label("Job Number:");
        addressLabel = new Label("Address:");
        jobNumber = new TextField(sEditor.getJobNumber());
        jobNumber.setOnAction(updateHandler);
        address = new TextField(sEditor.getAddress());
        address.setOnAction(updateHandler);
        jobInfoLabel = new VBox(jobNumberLabel, addressLabel);
        jobInfoText = new VBox(jobNumber, address);
        jobInfo = new HBox(jobInfoLabel, jobInfoText);
        editor = new Accordion();
        newRoom = new Button("New Room");
        newRoom.setOnAction(new NewRoomHandler(this));
        //update = new Button("Update");
        //update.setOnAction(updateHandler);
        //controlPane = new HBox(12, newRoom, update);
        controlPane = new HBox(12, newRoom);
        editorBox = new VBox(jobInfo, editor, controlPane);
        editorBox.setStyle("-fx-padding: 24px");
        currentOutputPage = 0;
        documentView = new ImageView();
        workOrderUsers = sEditor.getWorkOrderUsers();
        
        for(SaveableRoom room: sEditor.getRooms()){
            Room currentRoom = new Room(this, room.getName(), room.getChecked());
            for(SaveableRoomItem roomItem: room.getRoomItems()){
                currentRoom.addRoomItem(new RoomItem(currentRoom, roomItem.getName(), roomItem.getChecked()));
            }
            this.addRoom(currentRoom);
        }
        this.generate();
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
        //editor.getPanes().add(0, room);
        editor.getPanes().add(room);
    }
    
    public void generateDocument(){
        float margin = 18.0f;
        float tab = 9.0f;
        float leading = 14.0f;
        
        PDDocument outputDocument = new PDDocument();
        PDPage page = new PDPage();
        outputDocument.addPage(page);
        PDPageContentStream contentStream;
        try{
            contentStream = new PDPageContentStream(outputDocument, page, PDPageContentStream.AppendMode.APPEND, false);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(leading);
            //A PDPage is 612 by 792 points and (0,0) is at the bottom left corner.
            contentStream.newLineAtOffset(margin, page.getMediaBox().getHeight() - margin);
            contentStream.showText("Job Number: " + this.jobNumber.getText());
            contentStream.newLine();
            contentStream.showText("Address: " + this.address.getText());
            contentStream.newLine();
            contentStream.newLine();
            
            for(int i = 0; i < this.editor.getPanes().size(); i++){
                Room currentRoom = (Room)this.editor.getPanes().get(i);
                if(currentRoom.isSelected() && currentRoom.getNumberOfCheckedRoomItems() > 0){
                    contentStream.showText(currentRoom.getText());
                    contentStream.newLineAtOffset(tab, -leading);
                    for(int j = 0; j < currentRoom.getNumberOfRoomItems(); j++){
                        if(currentRoom.getRoomItem(j).isSelected()){
                            contentStream.showText(currentRoom.getRoomItem(j).getText());
                            contentStream.newLine();
                        }
                    }
                    contentStream.newLineAtOffset(-tab, -leading);
                }
            }
            contentStream.endText();
            contentStream.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        if(this.document != null){
            try{
                this.document.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        this.document = outputDocument;
        //return document;
    }
    
    public void generateImage(){
        PDFRenderer renderer = new PDFRenderer(this.document);
        try{
            Image image = SwingFXUtils.toFXImage(renderer.renderImage(this.currentOutputPage), null);
            this.documentView.setImage(image);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public VBox getEditorBox(){
        return this.editorBox;
    }
    
    public PDDocument getDocument(){
        return this.document;
    }
    
    public ImageView getDocumentView(){
        return this.documentView;
    }
    
    public void generate(){
        this.generateDocument();
        this.generateImage();
    }
    
    public boolean hasUser(String username){
        boolean value = false;
        for(int i = 0; i < this.workOrderUsers.size(); i++){
            if(this.workOrderUsers.get(i).equals(username)){
                value = true;
            }
        }
        return value;
    }
    
    public void addWorkOrderUser(String username){
        this.workOrderUsers.add(username);
    }
    
    public void clearWorkOrderUsers(){
        this.workOrderUsers.clear();
    }
    
    public SaveableEditor generateSaveableEditor(){
        ArrayList<SaveableRoom> rooms = new ArrayList<>();
        for(int i = 0; i < this.editor.getPanes().size(); i++){
            rooms.add(((Room)this.editor.getPanes().get(i)).generateSaveableRoom());
        }
        return new SaveableEditor(this.jobNumber.getText(), this.address.getText(), this.workOrderUsers, rooms);
    }
}

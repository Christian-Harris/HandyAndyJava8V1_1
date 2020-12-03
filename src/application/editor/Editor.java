package application.editor;

import handler.NewRoomHandler;
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
 *
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
    private final HBox controlPane;
    private final VBox editorBox;
    
    private PDDocument document;
    private ImageView documentView;
    private int currentOutputPage;
    
    public Editor(){
        jobNumberLabel = new Label("Job Number:");
        addressLabel = new Label("Address:");
        jobNumber = new TextField("");
        address = new TextField("");
        jobInfoLabel = new VBox(jobNumberLabel, addressLabel);
        jobInfoText = new VBox(jobNumber, address);
        jobInfo = new HBox(jobInfoLabel, jobInfoText);
        editor = new Accordion();
        newRoom = new Button("New Room");
        newRoom.setOnAction(new NewRoomHandler(this));
        controlPane = new HBox(12, newRoom);
        editorBox = new VBox(jobInfo, editor, controlPane);
        editorBox.setStyle("-fx-padding: 24px");
        currentOutputPage = 0;
        documentView = new ImageView();
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
        
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream;
        try{
            contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, false);
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
                if(currentRoom.isSelected() && currentRoom.getNumberOfRoomItems() > 0){
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
        this.document = document;
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
}

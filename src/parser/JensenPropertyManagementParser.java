package parser;

import application.editor.Editor;
import application.editor.Room;
import application.editor.RoomItem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.fontbox.type1.Type1Font;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Christian Harris
 */
public final class JensenPropertyManagementParser {
    
    public static Editor parse(File file){
        Editor editor = new Editor();
        
        try{
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            Scanner parser = new Scanner(text);
            //Extract off the job number and the address.
            while(parser.hasNext()){
                if(parser.next().equalsIgnoreCase("For:")){
                    editor.setJobNumber(parser.next());
                    String address = "";
                    while(true){
                        String temp = parser.next().trim();
                        if(!temp.equalsIgnoreCase("Inspected")){
                            address += (temp + " ");
                        }
                        else{
                            address = address.trim();
                            editor.setAddress(address);
                            break;
                        }
                    }
                    break;
                }
            }
            int numberOfPages = document.getNumberOfPages();
            for(int i = 0; i < numberOfPages; i++){
                parser.reset();
                parser.useDelimiter("\n");
                while(parser.hasNext()){
                    if(!parser.nextLine().equalsIgnoreCase("Condition Notes")){
                        continue;
                    }
                    else{
                        break;
                    }
                }
                Room currentRoom = new Room();
                while(parser.hasNext()){
                    String nextLine = parser.next().trim();
                    if(nextLine.toLowerCase().contains("move-out pictures")){
                        break;
                    }
                    if(nextLine.toLowerCase().contains("(cont'd)")){
                        continue;
                    }
                    if(nextLine.toLowerCase().contains("report generated")){
                        continue;
                    }
                    if(nextLine.toLowerCase().contains("note:")){
                        continue;
                    }
                    if(nextLine.toLowerCase().contains("amer pride:")){
                        continue;
                    }
                    if(nextLine.toLowerCase().contains("atlas")){
                        continue;
                    }
                    if(nextLine.toLowerCase().contains("condition notes")){
                        continue;
                    }
                    if(!nextLine.toLowerCase().contains("rpr") && !nextLine.toLowerCase().contains("rpl")){
                        currentRoom = new Room(nextLine);
                        editor.addRoom(currentRoom);
                        //System.out.println("Room: " + nextLine);
                    }
                    else if (nextLine.toLowerCase().contains("rpr") || nextLine.toLowerCase().contains("rpl")){
                        currentRoom.addRoomItem(new RoomItem(nextLine));
                        //System.out.println("Item: " + nextLine);
                    }
                }
            }
            document.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
        return editor;
    }
}

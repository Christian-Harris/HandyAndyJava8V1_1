package parser;

import application.editor.Editor;
import application.editor.Room;
import application.editor.RoomItem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * The JensenPropertymanagementPaser class is designed to parse a Jensen Property Management work order. The parser loads a file of a
 * work order and then utilizes a PDFTextStripper to strip the raw text as a String. This String is then parsed for the relevant information.
 * Finally an editor object is generated and passed back as the return value.
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
            parser.reset();
            parser.useDelimiter("\n");
            //for(int i = 0; i < numberOfPages; i++){
                //parser.reset();
                //parser.useDelimiter("\n");
                while(parser.hasNext()){
                    if(!parser.nextLine().equalsIgnoreCase("Condition Notes")){
                        continue;
                    }
                    else{
                        break;
                    }
                }
                Room currentRoom = new Room(editor);
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
                    if(nextLine.toLowerCase().contains("com needs complete painting fc full clean rpl needs replacement rpr needs repair w wipe down")){
                        continue;
                    }
                    if(!nextLine.toLowerCase().contains("rpr") && !nextLine.toLowerCase().contains("rpl")){
                        currentRoom = new Room(editor, nextLine);
                        editor.addRoom(currentRoom);
                        //System.out.println("Room: " + nextLine);
                    }
                    else if (nextLine.toLowerCase().contains("rpr") || nextLine.toLowerCase().contains("rpl")){
                        currentRoom.addRoomItem(new RoomItem(currentRoom, nextLine));
                        //System.out.println("Item: " + nextLine);
                    }
                }
            //}
            document.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        editor.generateDocument();
        editor.generateImage();
        return editor;
    }
}

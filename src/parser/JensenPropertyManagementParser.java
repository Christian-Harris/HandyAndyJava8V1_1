package parser;

import application.editor.Editor;
import application.editor.Room;
import application.editor.RoomItem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
                        String temp = parser.next();
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
            parser.reset();
            document.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        /*
        try{
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            Scanner parser = new Scanner(text);
            //Extract off the job number and the address.
            while(parser.hasNext()){
                //First we find the first instance of For:
                if(parser.next().equalsIgnoreCase("For:")){
                    editor.setJobNumber(parser.next());
                    parser.useDelimiter("\n");
                    editor.setAddress(parser.next() + parser.next());
                    parser.next();
                    parser.next();
                    parser.next();
                    parser.next();
                    break;
                }
            }
            //Now we construct each room and the room items.
            Room room = new Room();
            while(parser.hasNext()){
                if(parser.hasNext("[RPL]++") || parser.hasNext("[RPR]++")){
                    room.addRoomItem(new RoomItem(parser.next()));
                }
                else{
                    room = new Room(parser.next());
                    editor.addRoom(room);
                }
            }
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        */
        
        return editor;
    }
}

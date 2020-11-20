package parser;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Christian Harris
 */
public final class JensenPropertyManagementParser {
    public static PDDocument parse(File file){
        PDDocument inputDocument;
        try{
            inputDocument = PDDocument.load(file);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}

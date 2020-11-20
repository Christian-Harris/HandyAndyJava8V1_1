package parser;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.fontbox.type1.Type1Font;

/**
 *
 * @author Christian Harris
 */
public final class JensenPropertyManagementParser {
    public static PDDocument parse(File file) throws IOException{
        PDDocument inputDocument;
        PDDocument outputDocument = new PDDocument();
        PDPage firstPage = new PDPage();
        outputDocument.addPage(firstPage);
        
        PDFont font = PDType1Font.HELVETICA;
        PDPageContentStream contentStream = new PDPageContentStream(outputDocument, firstPage);
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setLeading(12);
        contentStream.newLineAtOffset(25, 725);
        contentStream.showText("Handy Andy");
        contentStream.newLine();
        contentStream.showText("Oh Yea");
        contentStream.newLine();
        contentStream.showText("sup?");
        contentStream.newLine();
        contentStream.endText();
        contentStream.close();
        try{
            inputDocument = PDDocument.load(file);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return outputDocument;
    }
}

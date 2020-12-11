# HandyAndyJava8V1_1
 NetBeans project for HandyAndy application utilizing Java 8.
 
## Synopsis
This project simplifies the task of converting maintenance work orders from an external document to an internal one for the Handy Andy company.

## How to Run
This project was built in netbeans. If you have the source project you should be able to run it from a netbeans IDE. Otherwise if you navigate to the dist folder there is a jar file which can be executed. Another way is to run the jar file from the command line. If you navigate to the dist folder you can execute the command:
java -jar HandyAndyJava8V1_1.jar
and the application will run. Note that if you do not have access to the Handy Andy Database you will not be able to make it past the login screen without appropriate credentails.

## Code Example
The following code is the primary parser for the application. This algorithm shows the process of extracting the raw text from a PDDocument parsing it and generating an Editor object which is the return value.
```
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
                }
                else if (nextLine.toLowerCase().contains("rpr") || nextLine.toLowerCase().contains("rpl")){
                    currentRoom.addRoomItem(new RoomItem(currentRoom, nextLine));
                }
            }
            document.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        editor.generateDocument();
        editor.generateImage();
        return editor;
    }
 ```

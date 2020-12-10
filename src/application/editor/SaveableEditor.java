package application.editor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *The SaveableEditor class stores all the necessary information to generate an Editor object. It's purpose is to implement the Serializable
 * interface so that the state of an Editor object can be saved.
 * @author Christian Harris
 */
public class SaveableEditor implements Serializable{
    private final String jobNumber;
    private final String address;
    private final ArrayList<String> workOrderUsers;
    private final ArrayList<SaveableRoom> rooms;
    
    public SaveableEditor(String jobNumber, String address, ArrayList<String> workOrderUsers, ArrayList<SaveableRoom> rooms){
        this.jobNumber = jobNumber;
        this.address = address;
        this.workOrderUsers = workOrderUsers;
        this.rooms = rooms;
    }
    
    public String getJobNumber(){
        return this.jobNumber;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public ArrayList<String> getWorkOrderUsers(){
        return this.workOrderUsers;
    }
    public ArrayList<SaveableRoom> getRooms(){
        return this.rooms;
    }
}

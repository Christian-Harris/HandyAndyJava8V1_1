package user;

/**
 *
 * @author Christian Harris
 */
public enum UserType {
    USER (0, "User"),
    ADMIN (1, "Admin"),
    CREW (2, "Employee");
    
    private final int code;
    private final String name;
    
    UserType(int code, String name){
        this.code = code;
        this.name = name;
    }
    
    public int getCode(){
        return this.code;
    }
    
    public String getName(){
        return this.name;
    }
    
    public static UserType getUserType(String name) {
        switch(name){
            case "User":
                return UserType.USER;
            case "Admin":
                return UserType.ADMIN;
            case "Employee":
                return UserType.CREW;
            default:
                return null;
        }     
    }
}

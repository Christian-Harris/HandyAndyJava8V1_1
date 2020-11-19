package user;


import hash.PasswordHash;

/**
 *
 * @author Christian Harris
 */
public class User {
    private String username;
    private String password;
    private String email;
    private UserType userType;
    
    public User(String username, String password, UserType userType){
        this.username = username;
        this.password = PasswordHash.passwordHash(password);
        this.userType = userType;
    }
    
    public User(String username, String password, String email, UserType userType){
        this(username, password, userType);
        this.email = email;
    }
    
    public void setUsername(String username){} //Requires implementation.
    
    public String getUsername(){
        return this.username;
    }
    
    public void setPassword(String password){} //Requires implementation.
    
    public String getPassword(){
        return this.password;
    }
    
    public void setUserType(UserType userType){
        this.userType = userType;
    }
    
    public UserType getUserType(){
        return this.userType;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    
}

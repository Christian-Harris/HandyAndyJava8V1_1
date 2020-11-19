package hash;

/**
 *
 * @author Christian Harris
 */
public final class PasswordHash {
    public static String passwordHash(String password){
        return Integer.toString(password.hashCode()); // Needs a more robust implementation.
    }
}

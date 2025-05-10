package co.avbinvest.userservices.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User " + id + " not found");
    }
}

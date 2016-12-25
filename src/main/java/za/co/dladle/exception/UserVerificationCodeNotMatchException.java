package za.co.dladle.exception;

/**
 * Created by prady on 11/13/2016.
 */
public class UserVerificationCodeNotMatchException extends Exception {
    public UserVerificationCodeNotMatchException(String message) {
        super(message);
    }
}

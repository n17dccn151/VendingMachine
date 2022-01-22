package demo.exception;

/**
 * Created by TanVOD on Jan, 2022
 */
public class NotSufficientChangeException extends RuntimeException {
    private String message;

    public NotSufficientChangeException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }

}
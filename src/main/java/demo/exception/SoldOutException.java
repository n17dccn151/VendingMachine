package demo.exception;

/**
 * Created by TanVOD on Jan, 2022
 */
public class SoldOutException extends RuntimeException {
    private String message;

    public SoldOutException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }

}

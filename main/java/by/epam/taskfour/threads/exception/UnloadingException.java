package by.epam.taskfour.threads.exception;

public class UnloadingException extends Exception {
    public UnloadingException(){
        super();
    }

    public UnloadingException(String message){
        super(message);
    }
    public UnloadingException(Throwable cause){
        super(cause);
    }
    public UnloadingException(String message, Throwable cause){
        super (message, cause);
    }
}

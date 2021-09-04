package exceptions;

public abstract class EmpireException extends Exception {

    public EmpireException() {
    }

    public EmpireException(String message) {
        super(message);
    }
}

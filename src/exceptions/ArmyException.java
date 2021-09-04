package exceptions;

public abstract class ArmyException extends EmpireException {

    public ArmyException() {
    }

    public ArmyException(String message) {
        super(message);
    }
}

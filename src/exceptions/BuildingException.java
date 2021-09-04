package exceptions;

public abstract class BuildingException extends EmpireException {

    public BuildingException() {
    }

    public BuildingException(String message) {
        super(message);
    }
}

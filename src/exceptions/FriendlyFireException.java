package exceptions;

public class FriendlyFireException extends ArmyException {

    public FriendlyFireException() {
    }

    public FriendlyFireException(String message) {
        super(message);
    }
}

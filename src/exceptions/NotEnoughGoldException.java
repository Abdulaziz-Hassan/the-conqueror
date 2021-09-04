package exceptions;

public class NotEnoughGoldException extends BuildingException {

    public NotEnoughGoldException() {
    }

    public NotEnoughGoldException(String message) {
        super(message);
    }
}

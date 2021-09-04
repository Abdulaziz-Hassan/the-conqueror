package engine;

public class Distance {

    private final String from;
    private final String to;
    private final int distance;

    public Distance(String from, String to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }
}

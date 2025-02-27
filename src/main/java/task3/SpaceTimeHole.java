package task3;

public class SpaceTimeHole {
    private final String origin;
    private final String destination;

    public SpaceTimeHole(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public String transportMessage(Message message) {
        return message.getContent();
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}

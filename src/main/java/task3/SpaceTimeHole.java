package task3;

public class SpaceTimeHole {
    private final String origin;
    private final Galaxy destinationGalaxy;

    public SpaceTimeHole(String origin, Galaxy destinationGalaxy) {
        this.origin = origin;
        this.destinationGalaxy = destinationGalaxy;
    }

    public void transportMessage(Message message) {
        if (message == null || message.getContent() == null) {
            throw new IllegalArgumentException("Сообщение не может быть пустым");
        }
        destinationGalaxy.receiveMessage(message);
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destinationGalaxy.getName();
    }
}

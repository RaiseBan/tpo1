package task3;

public class Event {
    private final Person sender;
    private final SpaceTimeHole hole;
    private final Galaxy destination;

    public Event(Person sender, SpaceTimeHole hole, Galaxy destination) {
        this.sender = sender;
        this.hole = hole;
        this.destination = destination;
    }

    public void trigger() {
        if (sender == null || hole == null || destination == null) {
            throw new IllegalStateException("Все параметры события должны быть заданы");
        }

        Message message = new Message(sender, sender.getSpokenPhrase());
        String transportedMessage = hole.transportMessage(message);
        destination.broadcastMessage(transportedMessage);
        destination.updateWarStatus(); // Обновляем состояние галактики
    }
}
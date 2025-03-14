package task3;

import java.util.ArrayList;
import java.util.List;


public class Galaxy {
    private final String name;
    private final List<WarlikeCreature> inhabitants = new ArrayList<>();
    private Message lastReceivedMessage; // Храним последнее полученное сообщение

    public Galaxy(String name) {
        this.name = name;
    }

    public void addInhabitant(WarlikeCreature creature) {
        if (creature == null) {
            throw new IllegalArgumentException("Существо не может быть null");
        }
        inhabitants.add(creature);
    }

    public List<WarlikeCreature> getInhabitants() {
        return inhabitants;
    }

    public String getName() {
        return name;
    }

    public void receiveMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Сообщение не может быть null");
        }
        lastReceivedMessage = message; // Сохраняем сообщение
        for (WarlikeCreature creature : inhabitants) {
            creature.reactToMessage(message.getContent());
        }
    }

    public Message getLastReceivedMessage() {
        return lastReceivedMessage;
    }
    public WarStatus getCollectiveWarStatus() {
        if (inhabitants.isEmpty()) {
            return WarStatus.PEACE;
        }

        long onEdgeCount = inhabitants.stream()
                .filter(c -> c.getWarStatus() == WarStatus.ON_EDGE)
                .count();
        long atWarCount = inhabitants.stream()
                .filter(c -> c.getWarStatus() == WarStatus.AT_WAR)
                .count();

        if (atWarCount > 0) {
            return WarStatus.AT_WAR;
        } else if (onEdgeCount > inhabitants.size() / 2) {
            return WarStatus.ON_EDGE;
        } else {
            return WarStatus.PEACE;
        }
    }


}


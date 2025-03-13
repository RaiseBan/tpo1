package task3;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {
    private final String name;
    private final List<WarlikeCreature> inhabitants = new ArrayList<>();

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

    public void broadcastMessage(String messageContent) {
        if (messageContent == null) {
            throw new IllegalArgumentException("Сообщение не может быть null");
        }
        for (WarlikeCreature creature : inhabitants) {
            creature.reactToMessage(messageContent);
        }
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

    public void updateWarStatus() {
        WarStatus collectiveStatus = getCollectiveWarStatus();
        if (collectiveStatus == WarStatus.AT_WAR) {
            for (WarlikeCreature creature : inhabitants) {
                creature.setWarStatus(WarStatus.AT_WAR);
            }
        }
    }
}
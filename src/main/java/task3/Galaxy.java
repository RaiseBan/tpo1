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
        inhabitants.add(creature);
    }

    public List<WarlikeCreature> getInhabitants() {
        return inhabitants;
    }

    public String getName() {
        return name;
    }

    public void broadcastMessage(String messageContent) {
        for (WarlikeCreature creature : inhabitants) {
            creature.reactToMessage(messageContent);
        }
    }
}

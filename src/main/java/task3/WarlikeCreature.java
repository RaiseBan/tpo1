package task3;

public class WarlikeCreature {
    private final String species;
    private WarStatus warStatus;
    private final CreatureType creatureType;

    public WarlikeCreature(String species, WarStatus warStatus, CreatureType creatureType) {
        this.species = species;
        this.warStatus = warStatus;
        this.creatureType = creatureType;
    }

    public String getSpecies() {
        return species;
    }

    public WarStatus getWarStatus() {
        return warStatus;
    }

    public CreatureType getCreatureType() {
        return creatureType;
    }

    public void setWarStatus(WarStatus warStatus) {
        this.warStatus = warStatus;
    }

    /**
     * Реагирует на сообщение: если сообщение содержит "проблемы", статус меняется на ON_EDGE.
     */
    public void reactToMessage(String message) {
        if (message != null && message.toLowerCase().contains("problems")) {
            this.warStatus = WarStatus.ON_EDGE;
        }
    }
}

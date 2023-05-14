package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroid implements Moveable {

    private final UUID uuid;
    private final String name;
    private Coordinate position;
    private UUID currentDeckId;


    public MaintenanceDroid(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void moveTo(Coordinate position) {
        this.position = position;
    }

    public Boolean spawnOnDeck(UUID deckId, Coordinate position) {
        this.currentDeckId = deckId;
        this.position = position;
        return true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getCurrentDeckUuid() {
        return currentDeckId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public String getCoordinates() {
        return this.position.toString();
    }
}

package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class MaintenanceDroid implements Moveable {

    @Id
    private UUID uuid;
    private String name;
    private Point position;
    private UUID currentDeckId;

    protected MaintenanceDroid() { }

    public MaintenanceDroid(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void moveTo(Point position) {
        this.position = position;
    }

    public Boolean spawnOnDeck(UUID deckId, Point position) {
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

    public UUID getSpaceShipDeckId() {
        return currentDeckId;
    }

    public Point getPoint() {
        return position;
    }

    public String getCoordinates() {
        return this.position.toString();
    }
}

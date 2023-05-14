package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;


import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class SpaceShipDeck {

    @ElementCollection
    @Getter
    private final List<Barrier> barriers = new ArrayList<>();
    @Id
    private UUID uuid;
    private int gridWidth;
    private int gridHeight;

    protected SpaceShipDeck() {
    }

    public SpaceShipDeck(int width, int height) {
        this.gridWidth = width;
        this.gridHeight = height;
        this.uuid = UUID.randomUUID();
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addBarrier(Barrier barrier) {
        this.barriers.add(barrier);
    }
}

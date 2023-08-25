package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.UUID;

@Embeddable
public class SpaceShipDeck {

    private UUID uuid;
    private int gridWidth;
    private int gridHeight;
    private final ArrayList<Barrier> barriers = new ArrayList<>();

    protected SpaceShipDeck () {}

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

    public ArrayList<Barrier> getBarriers() {
        return barriers;
    }
}

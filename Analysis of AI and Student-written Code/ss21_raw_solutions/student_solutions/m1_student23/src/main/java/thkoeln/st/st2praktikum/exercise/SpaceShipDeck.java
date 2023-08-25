package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class SpaceShipDeck {

    private final UUID uuid;
    private final int gridWidth;
    private final int gridHeight;
    private final ArrayList<Barrier> barriers = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();

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

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }
}

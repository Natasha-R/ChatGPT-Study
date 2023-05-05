package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class FloorTile implements Identifiable {
    private final UUID tileID = UUID.randomUUID();
    private boolean hasRobot = false;
    private boolean hasHorizontalBarrier = false;
    private boolean hasVerticalBarrier = false;
    private boolean hasConnection = false;
    private UUID connection = null;
    private int[] connectionCoordinate = new int[2];

    public boolean isHasRobot() {
        return hasRobot;
    }

    public void setHasRobot() { hasRobot = !hasRobot; }

    public boolean isHasHorizontalBarrier() {
        return hasHorizontalBarrier;
    }

    public void setHasHorizontalBarrier() {
        hasHorizontalBarrier = !hasHorizontalBarrier;
    }

    public boolean isHasVerticalBarrier() { return hasVerticalBarrier; }

    public void setHasVerticalBarrier() {
        hasVerticalBarrier = !hasVerticalBarrier;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection() {
        hasConnection = !hasConnection;
    }

    public UUID getConnection() {
        return connection;
    }

    public void setConnection(UUID toRoomID) {
        connection = toRoomID;
    }

    public int[] getConnectionCoordinate() {
        return connectionCoordinate;
    }

    public void setConnectionCoordinate(int x, int y) {
        connectionCoordinate[0] = x;
        connectionCoordinate[1] = y;
    }

    @Override
    public UUID getUID() {
        return tileID;
    }

}

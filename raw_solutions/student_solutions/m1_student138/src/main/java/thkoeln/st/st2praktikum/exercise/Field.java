package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Field {
    private boolean southBarrier;
    public void setSouthBarrier(boolean southBarrier) { this.southBarrier = southBarrier; }
    public boolean isSouthBarrier() { return southBarrier; }

    private boolean westBarrier;
    public void setWestBarrier(boolean westBarrier) { this.westBarrier = westBarrier; }
    public boolean isWestBarrier() { return westBarrier; }

    private boolean occupiedByCleaner;
    public void setOccupiedByCleaner(boolean occupiedByCleaner) { this.occupiedByCleaner = occupiedByCleaner; }
    public boolean isOccupiedByCleaner() { return occupiedByCleaner; }

    private Connection connection;
    public void setConnection(Connection connection) {
        this.connection = connection;
        hasConnection = true;
    }
    public Connection getConnection() {
        return connection;
    }

    private boolean hasConnection = false;
    public boolean isHasConnection() {
        return hasConnection;
    }


    public boolean canMoveOut(String direction) {
        if ((direction.equals("so") && southBarrier) || (direction.equals("we") && westBarrier)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canMoveIn(String direction) {
        if ((direction.equals("no") && southBarrier) || (direction.equals("ea") && westBarrier) || occupiedByCleaner) {
            return false;
        } else {
            return true;
        }
    }

}





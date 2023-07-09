package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tile {
    @Getter
    private List<Connection> connections = new ArrayList<Connection>();
    @Getter
    private List<Wall> walls = new ArrayList<Wall>();
    @Getter
    private UUID robotID = null;

    public boolean placeRobotOnTile(UUID robotID) {
        if (this.robotID == null) {
            this.robotID = robotID;
            return true;
        }

        return false;
    }

    public void removeRobot() {
        robotID = null;
    }

    public boolean hasWallOfType(wallType type) {
        if (!hasWall())
            return false;

        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getType() == type)
                return true;
        }

        return false;
    }

    public boolean hasRobot() {
        return this.robotID != null;
    }

    public boolean hasWall() {
        return this.walls.size() > 0;
    }

    public void addWall(Wall newWall) {
        this.walls.add(newWall);
    }

    public boolean hasConnection() {
        return !this.connections.isEmpty();
    }

    public void addConnection(Connection newConnection) {
        this.connections.add(newConnection);
    }
}

package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Room extends Entity {
    @Getter
    private int width, height;
    private Tile[][] tiles = new Tile[1024][1024];

    public void addWall(String wallString) {
        Coordinate[] parsedWall = CustomHelpers.parseWallString(wallString);

        if (parsedWall[0].getX() == parsedWall[1].getX()) { // check if start and end x are the same. if so we have a vertical
            for (int start = parsedWall[0].getY(); start < parsedWall[1].getY(); start++) {
                tiles[parsedWall[0].getX()][start].addWall(new Wall(wallType.verticalWall));
            }
        } else {
            for (int start = parsedWall[0].getX(); start < parsedWall[1].getX(); start++) {
                tiles[start][parsedWall[0].getY()].addWall(new Wall(wallType.horizontalWall));
            }
        }
    }

    public Coordinate findConnectionLocation(UUID connectionID) {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (getTile(w, h).hasConnection()) {
                    for (Connection connection : getTile(w, h).getConnections()) {
                        if (connection.getId().equals(connectionID)) {
                            return new Coordinate(w, h);
                        }
                    }
                }
            }
        }

        throw new UnsupportedOperationException("tried to find non existing connection");
    }

    public int getMaxMoveAmount(int x, int y, moveType type, int desiredAmountToMove, UUID robotID) {

        switch (type) {
            case moveEast: {
                int moved = 0;
                for (int i = x; i < x + desiredAmountToMove; i++) {

                    if (i >= width)
                        return moved - 1;

                    if (getTile(i, y).hasWallOfType(wallType.verticalWall)) {
                        return moved - 1;
                    }
                    if (getTile(i, y).hasRobot()) {
                        if (!getTile(i, y).getRobotID().equals(robotID))
                            return moved;
                    }

                    moved++;
                }

                return desiredAmountToMove;
            }
            case moveWest: {
                int moved = 0;
                for (int i = x; i > x - desiredAmountToMove; i--) {

                    if (i <= 0)
                        return moved;

                    if (getTile(i, y).hasWallOfType(wallType.verticalWall)) {
                        return moved;
                    }
                    if (getTile(i, y).hasRobot()) {
                        if (!getTile(i, y).getRobotID().equals(robotID))
                            return moved;
                    }

                    moved++;
                }

                return desiredAmountToMove;
            }
            case moveNorth: {
                int moved = 0;
                for (int i = y; i < y + desiredAmountToMove; i++) {

                    if (i >= height)
                        return moved - 1;

                    if (getTile(x, i).hasWallOfType(wallType.horizontalWall)) {
                        return moved - 1;
                    }
                    if (getTile(x, i).hasRobot()) {
                        if (!getTile(x, i).getRobotID().equals(robotID))
                            return moved;
                    }

                    moved++;
                }

                return desiredAmountToMove;
            }
            case moveSouth: {
                int moved = 0;
                for (int i = y; i > y - desiredAmountToMove; i--) {
                    if (i <= 0)
                        return moved;

                    if (getTile(x, i).hasWallOfType(wallType.horizontalWall)) {
                        return moved;
                    }
                    if (getTile(x, i).hasRobot()) {
                        if (!getTile(x, i).getRobotID().equals(robotID))
                            return moved;
                    }

                    moved++;
                }

                return desiredAmountToMove;
            }
        }

        return 0;
    }

    public void addConnection(Connection connection, String position) {
        Coordinate destination = new Coordinate(position);
        getTile(destination.getX(), destination.getY()).addConnection(connection);
    }

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                this.tiles[w][h] = new Tile();
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (x >= this.width || y >= this.height)
            throw new UnsupportedOperationException("Tile not in room");

        return this.tiles[x][y];
    }
}

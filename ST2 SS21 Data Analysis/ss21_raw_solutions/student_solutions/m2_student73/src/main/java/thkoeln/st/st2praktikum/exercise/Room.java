package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.OutOfBoundsException;

import java.util.UUID;

public class Room implements Identifiable {
    private final UUID roomID = UUID.randomUUID();
    private final int height;
    private final int width;
    private FloorTile[][] actualRoom;


    public Room(int heightParam, int widthParam) {
        height = heightParam;
        width = widthParam;
        actualRoom = new FloorTile[widthParam][heightParam];
        fillRoom();
    }

    public void fillRoom() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                actualRoom[i][j] = new FloorTile();
            }
        }
    }
    public void addXBarrier(int xStart, int xEnd, int y) {
        for(int i = xStart; i <= xEnd; i++) {
            actualRoom[i][y].setHasHorizontalBarrier();
        }
    }

    public void addYBarrier(int yStart, int yEnd, int x) {
        for(int i = yStart; i <= yEnd; i++) {
            actualRoom[x][i].setHasVerticalBarrier();
        }
    }
    public void addBarrier(Barrier barrier) {
        int xAxisStart = barrier.getStart().getX();
        int xAxisEnd = barrier.getEnd().getX();
        int yAxisStart = barrier.getStart().getY();
        int yAxisEnd = barrier.getEnd().getY();

        if(xAxisStart == xAxisEnd) {
            addYBarrier(yAxisStart, yAxisEnd, xAxisStart);
        } else addXBarrier(xAxisStart, xAxisEnd,yAxisStart);
    }

    public UUID addConnection(Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate) {
        int srcCoordinateX = sourceCoordinate.getX();
        int srcCoordinateY = sourceCoordinate.getY();
        int destCoordinateX = destinationCoordinate.getX();
        int destCoordinateY = destinationCoordinate.getY();
        if(srcCoordinateX > width || srcCoordinateY > height) {
            throw new OutOfBoundsException("Connection must be placed inside Room");
        }
        this.getCoordinate(srcCoordinateX, srcCoordinateY).setHasConnection();
        this.getCoordinate(srcCoordinateX, srcCoordinateY).setConnection(destinationRoomId);
        this.getCoordinate(srcCoordinateX, srcCoordinateY).setConnectionCoordinate(destCoordinateX, destCoordinateY);
        return this.getCoordinate(srcCoordinateX, srcCoordinateY).getUID();
    }

    @Override
    public UUID getUID() {
        return roomID;
    }

    public FloorTile getCoordinate(int x, int y) {
        return actualRoom[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

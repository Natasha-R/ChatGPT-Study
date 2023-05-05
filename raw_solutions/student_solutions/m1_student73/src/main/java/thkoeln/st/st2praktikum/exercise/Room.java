package thkoeln.st.st2praktikum.exercise;

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
    public void addBarrier(String barrierString) {
        int xAxisStart = Integer.parseInt(barrierString.substring(barrierString.indexOf('(')+1,barrierString.indexOf(',')));
        int xAxisEnd = Integer.parseInt(barrierString.substring(barrierString.indexOf('(', barrierString.indexOf('-'))+1,barrierString.indexOf(',',barrierString.indexOf('-'))));
        int yAxisStart = Integer.parseInt(barrierString.substring(barrierString.indexOf(',')+1,barrierString.indexOf(')')));
        int yAxisEnd = Integer.parseInt(barrierString.substring(barrierString.indexOf(',',barrierString.indexOf('-'))+1,barrierString.indexOf(')',barrierString.indexOf('-'))));

        if(xAxisStart == xAxisEnd) {
            addYBarrier(yAxisStart, yAxisEnd, xAxisStart);
        } else addXBarrier(xAxisStart, xAxisEnd,yAxisStart);
    }

    public UUID addConnection(String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        int srcCoordinateX = Integer.parseInt(sourceCoordinate.substring(sourceCoordinate.indexOf('(')+1, sourceCoordinate.indexOf(',')));
        int srcCoordinateY = Integer.parseInt(sourceCoordinate.substring(sourceCoordinate.indexOf(',')+1, sourceCoordinate.indexOf(')')));
        int destCoordinateX = Integer.parseInt(destinationCoordinate.substring(destinationCoordinate.indexOf('(')+1, destinationCoordinate.indexOf(',')));
        int destCoordinateY = Integer.parseInt(destinationCoordinate.substring(destinationCoordinate.indexOf(',')+1, destinationCoordinate.indexOf(')')));
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

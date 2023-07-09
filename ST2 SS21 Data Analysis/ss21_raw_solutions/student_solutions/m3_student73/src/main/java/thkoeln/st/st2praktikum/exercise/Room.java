package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.OutOfBoundsException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends thkoeln.st.st1praktikum.core.AbstractEntity {
    private int height;
    private int width;
    @Transient
    private FloorTile[][] actualRoom = new FloorTile[10][10];


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

    public UUID addConnection(TransportSystem system, Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate) {
        int srcCoordinateX = sourceCoordinate.getX();
        int srcCoordinateY = sourceCoordinate.getY();
        if(srcCoordinateX > width || srcCoordinateY > height) {
            throw new OutOfBoundsException("Connection must be placed inside Room");
        }
        this.getCoordinate(srcCoordinateX, srcCoordinateY).setHasConnection();
        Connection connection = new Connection(destinationCoordinate, destinationRoomId);
        system.addConnection(connection);
        this.getCoordinate(srcCoordinateX, srcCoordinateY).setConnection(connection);
        return connection.getConnectionID();
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

package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.TransportSystem;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OutOfBoundsException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends thkoeln.st.st2praktikum.exercise.domainprimitives.AbstractEntity {
    private int height;
    private int width;

    @Transient
    HashMap<Vector2D, FloorTile> actualRoom = new HashMap<Vector2D, FloorTile>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private final List<FloorTile> floorTiles = new ArrayList<>();


    public Room(int heightParam, int widthParam) {
        height = heightParam;
        width = widthParam;
        fillRoom();
    }

    private FloorTile getFloorTile( Vector2D vector2D) {
        if(actualRoom.size() == 0) {
            for (FloorTile floorTile : floorTiles) {
                actualRoom.put(floorTile.getCoordinate(), floorTile);
            }
        }
        return actualRoom.get(vector2D);
    }

    public void fillRoom() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2D vector2D = new Vector2D(i, j);
                FloorTile newFloorTile = new FloorTile(vector2D);
                floorTiles.add(newFloorTile);
                actualRoom.put(vector2D, newFloorTile);
            }
        }
    }
    public void addXBarrier(int xStart, int xEnd, int y) {
        for(int i = xStart; i <= xEnd; i++) {
            this.getCoordinate(i ,y).setHasHorizontalBarrier();
        }
    }

    public void addYBarrier(int yStart, int yEnd, int x) {
        for(int i = yStart; i <= yEnd; i++) {
            this.getCoordinate(x,i).setHasVerticalBarrier();
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
        return getFloorTile(new Vector2D(x, y));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

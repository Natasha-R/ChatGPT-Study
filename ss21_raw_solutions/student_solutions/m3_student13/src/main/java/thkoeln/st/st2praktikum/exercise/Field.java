package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;
import thkoeln.st.st2praktikum.exercise.Repositories.RoomRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Field {
    private int xCoordinateSize;
    private int yCoordinateSize;

    @Id
    private UUID uuid;

    @Transient
    private Room[][] fieldWithRooms;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Room> rooms = new ArrayList<>();


    public Field(int xCoordinateSize, int yCoordinateSize) {
        this.xCoordinateSize = xCoordinateSize;
        this.yCoordinateSize = yCoordinateSize;
        fieldWithRooms = new Room[xCoordinateSize][yCoordinateSize];
        uuid = UUID.randomUUID();
        createField();
        //determineNeighbor();

        //jeden Raum aus fieldWithRooms in rooms speichern
//        for(int i = 0; i < xCoordinateSize; i++){
//            for(int j = 0; j < yCoordinateSize; j++){
//                rooms.add(fieldWithRooms[i][j]);
//            }
//        }
    }

    public UUID getId() {
        return uuid;
    }

    public Room getRoom(int xCoordinate, int yCoordinate) {
        mapping();
        return fieldWithRooms[xCoordinate][yCoordinate];
    }

    public Room[][] getFieldWithRooms() {

        mapping();
        return fieldWithRooms;
    }


    public void createField() {
        for (int i = 0; i < xCoordinateSize; i++) {
            for (int j = 0; j < yCoordinateSize; j++) {
                fieldWithRooms[i][j] = new Room(new Point(i, j), this);
                rooms.add(fieldWithRooms[i][j]);
            }
        }
    }

    public void determineNeighbor() {

        mapping();
        for (int i = 0; i < fieldWithRooms.length; i++) {
            for (int j = 0; j < fieldWithRooms[i].length; j++) {

                if (j + 1 < fieldWithRooms[i].length) {
                    fieldWithRooms[i][j].setNorth(fieldWithRooms[i][j + 1]);
                }
                if (i + 1 < fieldWithRooms.length) {
                    fieldWithRooms[i][j].setEast(fieldWithRooms[i + 1][j]);
                }
                if (j - 1 >= 0) {
                    fieldWithRooms[i][j].setSouth(fieldWithRooms[i][j - 1]);
                }

                if (i - 1 >= 0 && i - 1 < fieldWithRooms[i].length) {
                    fieldWithRooms[i][j].setWest(fieldWithRooms[i - 1][j]);
                }
            }
        }
    }

    public void mapping() {
        fieldWithRooms = new Room[xCoordinateSize][yCoordinateSize];

        for (Room room : rooms) {
            int xCoordinate = room.getPoint().getX();
            int yCoordinate = room.getPoint().getY();
            fieldWithRooms[xCoordinate][yCoordinate] = room;
        }
    }

    public void placeWall(Wall wall) {

        //1.forEach über rooms
        //2.für jeden Raum Koordinaten ermitteln
        //3.diesen Raum in FieldWithRooms an dem entsprechenden Index setzen
        mapping();
        if (wall.isVertically()) {
            for (int i = wall.getStart().getY(); i < wall.getEnd().getY(); i++) {
                fieldWithRooms[wall.getStart().getX() - 1][i].setEast(null);
                fieldWithRooms[wall.getStart().getX()][i].setWest(null);
            }
            //horizontally wall
        } else {
            for (int i = wall.getStart().getX(); i < wall.getEnd().getX(); i++) {
                fieldWithRooms[i][wall.getStart().getY()].setSouth(null);
                fieldWithRooms[i][wall.getStart().getY() - 1].setNorth(null);
            }
        }

    }

}
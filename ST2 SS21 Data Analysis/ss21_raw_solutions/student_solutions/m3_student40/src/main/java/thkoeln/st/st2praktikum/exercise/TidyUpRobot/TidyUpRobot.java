package thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Room;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class TidyUpRobot {

    @Getter
    @Id
    UUID id = UUID.randomUUID();

    private String name;

    @Getter
    @Embedded
    private Coordinate position = null;

    @Getter
    @ManyToOne
    private Room currentRoom = null;

    public TidyUpRobot(String name){
        this.name = name;
    }

    public boolean setRoom(Room newRoom){
        if( currentRoom == null ){
            this.currentRoom = newRoom;
            this.position = new Coordinate(0,0);
            return true;
        }

        return false;
    }

    public boolean switchRoom(Room newRoom, Coordinate newPosition){
        if( currentRoom != null ){
            this.currentRoom = newRoom;
            this.position = newPosition;
            return true;
        }

        return false; // if the currentRoom is null the robot is not able to use a connection
    }

    public void move(int amountX, int amountY){
        this.position = new Coordinate(position.getX() + amountX, position.getY() + amountY);
    }
}

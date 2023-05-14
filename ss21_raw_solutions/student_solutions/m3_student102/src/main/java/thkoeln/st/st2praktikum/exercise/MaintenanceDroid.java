package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MaintenanceDroid {
    @Id
    private UUID droidID=UUID.randomUUID();
    private  UUID spaceShipDeckId;
    private String name;
    @Embedded
    private Point point;

    public MaintenanceDroid(){}

    public MaintenanceDroid(String name) {
        this.name = name;
    }
    public UUID getSpaceShipDeckId(){ return spaceShipDeckId; }
    //public Point getPoint(){ return droidPosition; }
}

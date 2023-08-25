package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;


import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
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

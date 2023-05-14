package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class CurrentCoordinate {
    @Id
    @Getter
    private UUID currentCoordinateId = UUID.randomUUID();

    @Getter
    @Setter
    private UUID robotId;

    @Getter
    @Setter
    private Integer x;

    @Getter
    @Setter
    private Integer y;


    public CurrentCoordinate(){}

    public CurrentCoordinate(Integer x, Integer y, UUID uuidRobot){
        this.x = x;
        this.y = y;
        this.robotId= uuidRobot;
    }
}

package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class Connection {

    @Id
    private final UUID connectionId = UUID.randomUUID();

    private UUID transportCategoryId;

    private UUID sourceFieldId;

    @Embedded
    private Point sourcePoint;

    private UUID destinationFieldId;

    @Embedded
    private Point destinationPoint;


    public Connection(UUID transportCategoryId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        this.transportCategoryId = transportCategoryId;
        this.sourceFieldId = sourceFieldId;
        this.sourcePoint = sourcePoint;
        this.destinationFieldId = destinationFieldId;
        this.destinationPoint = destinationPoint;
    }

    protected Connection(){}
}

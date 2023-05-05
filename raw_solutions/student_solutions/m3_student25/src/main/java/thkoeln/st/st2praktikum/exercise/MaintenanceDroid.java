package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Entity
public class MaintenanceDroid {
    @Id
    private final UUID maintenanceDroidID = UUID.randomUUID();
    private String name;
    @Setter
    private UUID spaceShipDeckID;
    @Setter
    @OneToOne
    private Square square;

    public UUID getSpaceShipDeckId(){
        return this.spaceShipDeckID;
    }

    public Coordinate getCoordinate(){
        return this.square.getCoordinates();
    }

    protected MaintenanceDroid() {
    }

    public MaintenanceDroid(String name) {
        this.name = name;
    }
}

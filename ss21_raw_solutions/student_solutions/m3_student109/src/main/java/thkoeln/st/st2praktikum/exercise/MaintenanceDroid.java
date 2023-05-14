package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceDroid {

    private String maintenceDroidsName;

    @Id
    private UUID maintenanceDroidsId;


    private UUID spaceShipDeckId;

    @Embedded
    private Vector2D vector2D;

    public MaintenanceDroid(String name, UUID maintenanceDroidsId) {
        this.maintenceDroidsName = name;
        this.maintenanceDroidsId = maintenanceDroidsId;
    }


}

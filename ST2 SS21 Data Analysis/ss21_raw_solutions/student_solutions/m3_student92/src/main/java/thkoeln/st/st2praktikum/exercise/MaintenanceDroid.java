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
//@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDroid {

    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private UUID spaceShipDeckId;

    @Getter
    @Embedded
    private Vector2D vector2D;

    public void setVector2D(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    public MaintenanceDroid() {
        this.id = UUID.randomUUID();
    }
}

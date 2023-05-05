package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
//@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private UUID transportCategoryId;

    @Getter
    @Setter
    private UUID sourceSpaceShipDeckId;

    @Getter
    @Embedded
    private Vector2D sourceVector2D;

    public void setSourceVector2D(Vector2D vector2D) {
        this.sourceVector2D = vector2D;
    }

    @Getter
    @Setter
    private UUID destinationSpaceShipDeckId;

    @Getter
    @Embedded
    private Vector2D destinationVector2D;

    public void setDestinationVector2D(Vector2D vector2D) {
        this.destinationVector2D = vector2D;
    }


    public Connection() {
        this.id = UUID.randomUUID();
    }
}

package thkoeln.st.st2praktikum.exercise.device;

import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.environment.Space;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public interface Trackable {

    @ManyToOne
    Space getSpace();
    void setSpace(Space space);

    @Embedded
    Vector2D getVector2D();
    void setVector2D(Vector2D vector2D);

    void setId(UUID uuid);
    @Id
    UUID getId();

}

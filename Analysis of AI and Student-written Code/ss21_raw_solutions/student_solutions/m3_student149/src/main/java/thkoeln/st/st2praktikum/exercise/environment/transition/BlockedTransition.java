package thkoeln.st.st2praktikum.exercise.environment.transition;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class BlockedTransition extends Transition {

    public BlockedTransition(Vector2D fromVector2D, Vector2D toVector2D) {
//        this.fromVector2D = fromVector2D;
//        this.toVector2D = toVector2D;
        this.fromX = fromVector2D.getX();
        this.fromY = fromVector2D.getY();
        this.toX = toVector2D.getX();
        this.toY = toVector2D.getY();
    }

    @Override
    protected void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    @Id
    public UUID getId() {
        return uuid;
    }

}

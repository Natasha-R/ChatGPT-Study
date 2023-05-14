package thkoeln.st.st2praktikum.exercise.space.domain.transition;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;
import java.util.UUID;

@Inheritance
@Entity
@Table(name = "transition_table")
public abstract class Transition {

    protected UUID uuid = UUID.randomUUID();

//    protected Vector2D fromVector2D;
//
//    protected Vector2D toVector2D;

    protected int fromX;
    protected int fromY;
    protected int toX;
    protected int toY;

    @Transient
    public Vector2D getFromVector2D() {
        return new Vector2D(fromX, fromY);
    }

    @Transient
    public Vector2D getToVector2D() {
        return new Vector2D(toX, toY);
    }

//    protected void setFromVector2D(Vector2D toPosition) {
//        this.toVector2D = toPosition;
//    }
//
//    @Embedded
//    public Vector2D getFromVector2D() {
//        return fromVector2D;
//    }
//
//    protected void setToVector2D(Vector2D toPosition) {
//        this.toVector2D = toPosition;
//    }
//
//    @Embedded
//    public Vector2D getToVector2D() {
//        return toVector2D;
//    }

    public Vector2D getOtherPosition(Vector2D position) {
        if (!isInvolved(position)) {
            return null;
        }
        return getFromVector2D().equals(position) ? getToVector2D() : getFromVector2D();
    }

    public boolean isInvolved(Vector2D position) {
        return getFromVector2D().equals(position) || getToVector2D().equals(position);
    }

    @Override
    public String toString() {
        return "Transition{" + getFromVector2D() + " || " + getToVector2D() + '}';
    }

    protected void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Id
    public UUID getId() {
        return uuid;
    }

    protected int getFromX() {
        return fromX;
    }

    protected int getFromY() {
        return fromY;
    }

    protected int getToX() {
        return toX;
    }

    protected int getToY() {
        return toY;
    }

    protected void setFromX(int fromX) {
        this.fromX = fromX;
    }

    protected void setFromY(int fromY) {
        this.fromY = fromY;
    }

    protected void setToX(int toX) {
        this.toX = toX;
    }

    protected void setToY(int toY) {
        this.toY = toY;
    }
}

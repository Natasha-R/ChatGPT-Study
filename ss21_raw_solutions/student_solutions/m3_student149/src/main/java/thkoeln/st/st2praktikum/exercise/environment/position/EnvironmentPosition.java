//package thkoeln.st.st2praktikum.exercise.environment.position;
//
//import lombok.NoArgsConstructor;
//import thkoeln.st.st2praktikum.exercise.environment.Space;
//
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//import java.util.Objects;
//
//@Entity
//@NoArgsConstructor
//public class EnvironmentPosition extends Position {
//
//    private Space space;
//
//    EnvironmentPosition(Space space, Position position) {
//        super(position.x, position.y);
//        this.space = space;
//    }
//
//    protected void setSpace(Space space) {
//        this.space = space;
//    }
//
//    @ManyToOne
//    public Space getSpace() {
//        return space;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        boolean position = super.equals(o);
//        if (position) {
//            if (o instanceof EnvironmentPosition) {
//                EnvironmentPosition environmentPosition = (EnvironmentPosition) o;
//                return space.equals(environmentPosition.space);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), space);
//    }
//
//    @Override
//    public String toString() {
//        return space.toString() + ": " + super.toString();
//    }
//}

//package thkoeln.st.st2praktikum.exercise.environment.position;
//
//import lombok.NoArgsConstructor;
//import thkoeln.st.st2praktikum.exercise.exception.ParseException;
//import thkoeln.st.st2praktikum.exercise.exception.PositionOutOfSpaceException;
//import thkoeln.st.st2praktikum.exercise.parser.PositionParser;
//import thkoeln.st.st2praktikum.exercise.uuid.UUIDElement;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Inheritance;
//import javax.persistence.Table;
//import java.util.Objects;
//import java.util.UUID;
//
//@Inheritance
//@Entity
//@Table(name = "position_table")
//@NoArgsConstructor
//public class Position extends UUIDElement {
//
//    private static final PositionParser parser = new PositionParser();
//
//    protected int x;
//
//    protected int y;
//
//    protected Position(int x, int y) throws PositionOutOfSpaceException {
//        if (x < 0 || y < 0) {
//            throw new PositionOutOfSpaceException(x, y);
//        }
//        this.x = x;
//        this.y = y;
//    }
//
//    public static Position of(String string) throws ParseException, PositionOutOfSpaceException {
//        return parser.parse(string);
//    }
//
//    public static Position of(int x, int y) throws PositionOutOfSpaceException {
//        return new Position(x, y);
//    }
//
//    protected void setX(int x) {
//        this.x = x;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    protected void setY(int y) {
//        this.y = y;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public String toPositionString() {
//        return "(" + x + "," + y + ')';
//    }
//
//    @Override
//    public String toString() {
//        return toPositionString();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || !(o instanceof Position)) return false;
//        Position position = (Position) o;
//        return x == position.x && y == position.y;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(x, y);
//    }
//
//    protected void setId(UUID uuid) {
//        this.uuid = uuid;
//    }
//
//    @Id
//    protected UUID getId() {
//        return getUuid();
//    }
//}

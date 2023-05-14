package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
//@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Wall {

    private Vector2D start;
    private Vector2D end;


    public Wall(Vector2D pos1, Vector2D pos2) {
        if (!pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY())) {
            throw new IllegalArgumentException("only vertical or horizontal walls allowed");
        }

        if (pos2.getX()+pos2.getY() < pos1.getX()+pos1.getY()) {
            this.start = pos2;
            this.end = pos1;
        }
        else {
            this.start = pos1;
            this.end = pos2;
        }
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        if (!wallString.substring(1, 2).equals(wallString.substring(7, 8)) && !wallString.substring(3, 4).equals(wallString.substring(9, 10))) {
            throw new IllegalArgumentException("only vertical or horizontal walls allowed");
        }
        else if (!wallString.substring(5, 6).equals("-")) {
            throw new IllegalArgumentException("not the right format");
        }
        else {
            this.start = new Vector2D(Integer.parseInt(wallString.substring(1, 2)), Integer.parseInt(wallString.substring(3, 4)));
            this.end = new Vector2D(Integer.parseInt(wallString.substring(7, 8)), Integer.parseInt(wallString.substring(9, 10)));
        }
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}

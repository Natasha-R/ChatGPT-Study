package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class Wall {

    @OneToOne
    private final Vector2D start;

    @OneToOne
    private final Vector2D end;

    public Wall(Vector2D pos1, Vector2D pos2) {
        checkIfWallsAreDiagonal(pos1, pos2);

        if ((pos1.getX() + pos1.getY()) <= (pos2.getX() + pos2.getY())) {
            this.start = pos1;
            this.end = pos2;
        } else {
            this.start = pos2;
            this.end = pos1;
        }
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        if (!wallString.matches("^\\([0-9]+,[0-9]+\\)-\\([0-9]+,[0-9]+\\)$"))
            throw new IllegalArgumentException("WallString doesn't match correct format.");

        String[] coordinates = wallString.split("-");
        Vector2D tempStart = new Vector2D(coordinates[0]);
        Vector2D tempEnd = new Vector2D(coordinates[1]);

        checkIfWallsAreDiagonal(tempStart, tempEnd);

        if ((tempStart.getX() + tempStart.getY()) <= (tempEnd.getX() + tempEnd.getY())) {
            this.start = tempStart;
            this.end = tempEnd;
        } else {
            this.start = tempEnd;
            this.end = tempStart;
        }
    }

    private void checkIfWallsAreDiagonal(Vector2D pos1, Vector2D pos2) {
        if (pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new IllegalArgumentException("Walls can't be diagonal.");
    }

    public Vector2D getStart() {
        return start;
    }
    public Vector2D getEnd() {
        return end;
    }
}

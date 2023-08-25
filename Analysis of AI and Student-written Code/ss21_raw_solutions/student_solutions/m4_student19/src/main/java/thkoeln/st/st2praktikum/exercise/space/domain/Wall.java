package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Embeddable
@Getter
public class Wall {

    @OneToOne
    private Vector2D start;

    @OneToOne
    private Vector2D end;

    public Wall() {}

    public Wall(Vector2D start, Vector2D end) {
        if (start.getX() != end.getX() && start.getY() != end.getY())
            throw new IllegalArgumentException("Walls can't be diagonal.");

        if ((start.getX() + start.getY()) <= (end.getX() + end.getY())) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }
    }
    
    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        if (!wallString.matches("^\\([0-9]+,[0-9]+\\)-\\([0-9]+,[0-9]+\\)$"))
            throw new IllegalArgumentException("WallString doesn't match correct format.");

        Wall newWall = new Wall();

        String[] coordinates = wallString.split("-");
        Vector2D tempStart = Vector2D.fromString(coordinates[0]);
        Vector2D tempEnd = Vector2D.fromString(coordinates[1]);

        if (tempStart.getX() != tempEnd.getX() && tempStart.getY() != tempEnd.getY())
            throw new IllegalArgumentException("Walls can't be diagonal.");

        if ((tempStart.getX() + tempStart.getY()) <= (tempEnd.getX() + tempEnd.getY())) {
            newWall.start = tempStart;
            newWall.end = tempEnd;
        } else {
            newWall.start = tempEnd;
            newWall.end = tempStart;
        }

        return newWall;
    }
}

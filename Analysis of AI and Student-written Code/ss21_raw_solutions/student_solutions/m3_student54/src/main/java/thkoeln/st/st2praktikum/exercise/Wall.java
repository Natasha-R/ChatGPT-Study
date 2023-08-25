package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Wall {

    private Vector2D start;
    private Vector2D end;

    public Wall(Vector2D pos1, Vector2D pos2)
    {
        Validator validator = new Validator();
        validator.orderWallVectors(pos1, pos2);

        if(!validator.isHorizontalOrVertical(validator.getWallStart(), validator.getWallEnd()))
        {
            throw new IllegalArgumentException("Diagonal walls are not allowed!");
        }
        this.start = validator.getWallStart();
        this.end = validator.getWallEnd();
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString)
    {
        Validator validator = new Validator();

        if(!validator.isWallString(wallString))
        {
            throw new IllegalArgumentException("This wallstring is not valid!");
        }
        this.start = validator.getWallStart();
        this.end = validator.getWallEnd();
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}

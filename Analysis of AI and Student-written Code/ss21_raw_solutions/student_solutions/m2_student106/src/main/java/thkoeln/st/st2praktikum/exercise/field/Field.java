package thkoeln.st.st2praktikum.exercise.field;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Field {
    @Id
    private UUID uuid = UUID.randomUUID();

    private int width;
    private int height;
    private final List<Obstacle> obstacles = new ArrayList<>();

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Point correctPointToBeWithinBoarders(Point wishedPosition) {
        if (wishedPosition.getX() >= width) wishedPosition.setX(width - 1);
        else if (wishedPosition.getY() >= height) wishedPosition.setY(height - 1);
        else wishedPosition.changeNegativeValueTo0();
        return wishedPosition;
    }

    public void ceckPointToBeWithinBoarders(Point wishedPosition) throws RuntimeException {
        if (wishedPosition.getX() >= width || wishedPosition.getY() >= height) {
            throw new RuntimeException("Point is outside of the fields boarders");
        }
        wishedPosition.checkXandYareNotNegative();
    }

}

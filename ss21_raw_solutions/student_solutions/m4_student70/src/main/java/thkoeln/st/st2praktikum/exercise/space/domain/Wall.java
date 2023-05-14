package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import javax.persistence.Embeddable;


@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Wall {

    private Point start;
    private Point end;


    public Wall(Point start, Point end) {
        if (start.getX() != end.getX() && start.getY() != end.getY())
            throw new UnsupportedOperationException();

        if ((start.getX() == end.getX() && start.getY() > end.getY())
                || (start.getY() == end.getY() && start.getX() > end.getX())) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }

    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        String[] pointsData = wallString.split("-");
        String[] startData = pointsData[0].replace("(", "").replace(")", "").split(",");
        String[] endData = pointsData[1].replace("(", "").replace(")", "").split(",");
        return new Wall(Point.fromString(pointsData[0]), Point.fromString(pointsData[1]));

    }

    public boolean isCross(Point position, TaskType taskType) {
        switch (taskType) {
            case NORTH:
                return position.getY() + 1 == start.getY() && position.getX() >= start.getX() && position.getX() < end.getX();
            case SOUTH:
                return position.getY() == start.getY() && position.getX() >= start.getX() && position.getX() < end.getX();
            case EAST:
                return position.getX() + 1 == start.getX() && position.getY() >= start.getY() && position.getY() < end.getY();
            case WEST:
                return position.getX() == start.getX() && position.getY() >= start.getY() && position.getY() < end.getY();
        }


        return false;
    }


}

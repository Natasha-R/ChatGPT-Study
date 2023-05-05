package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Blockable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.ArrayList;

@Getter
@Embeddable
public class Obstacle implements Blockable {
    @Embedded
    private Point start;
    @Embedded
    private Point end;
    private Orientation orientation;

    protected Obstacle() {
    }

    public Obstacle(Point start, Point end) throws RuntimeException {
        this.start = start;
        this.end = end;
        checkIsNotDiagonal();
        correctPointOrder();
        setOrientation();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) throws RuntimeException {
        if (obstacleString.length() == 11) {
            //Seperates into ["(2,3)","(10,3)"] Array
            String[] limits = obstacleString.split("-");
            start = new Point(limits[0]);
            end = new Point(limits[1]);
        } else throw new RuntimeException("Obstical has to many parts");
        checkIsNotDiagonal();
        correctPointOrder();
        setOrientation();
    }

    /**
     * @return always returns unreachable points from positiv direction (moving north or east)
     * for moving south or west put -1 on "y-Coordinate" or "x-Coordinate"
     */
    @Override
    public ArrayList<Point> getBlockedPoints() {
        ArrayList<Point> list = new ArrayList<>();

        if (start.getX().equals(end.getX())) {
            for (int i = start.getY(); i <= end.getY(); i++) {
                list.add(new Point(start.getX(), i));
            }
        } else {
            for (int i = start.getX(); i <= end.getX(); i++) {
                list.add(new Point(i, start.getY()));
            }
        }
        return list;
    }

    public ArrayList<Point> getBlockedPointsAccordingToDirection(TaskType taskType) {
        if (orientation.equals(Orientation.HORIZONTAL) && (taskType.equals(TaskType.NORTH) || taskType.equals(TaskType.SOUTH))) {
            switch (taskType) {
                case NORTH:
                    return getBlockedPoints();
                case SOUTH:
                    ArrayList<Point> list = getBlockedPoints();
                    for (Point blockedPoint : list) {
                        blockedPoint.setY(blockedPoint.getY() - 1);
                    }
                    return list;
            }
        }
        if (orientation.equals(Orientation.VERTICAL) && (taskType.equals(TaskType.EAST) || taskType.equals(TaskType.WEST))) {
            switch (taskType) {
                case EAST:
                    return getBlockedPoints();
                case WEST:
                    ArrayList<Point> list = getBlockedPoints();
                    for (Point blockedPoint : list) {
                        blockedPoint.setY(blockedPoint.getX() - 1);
                    }
                    return list;
            }
        }
        return null;
    }

    private void checkIsNotDiagonal() throws RuntimeException {
        if (!(start.getX().equals(end.getX()) || start.getY().equals(end.getY()))) {
            throw new RuntimeException("Obstacle is diagonal");
        }
    }

    private void correctPointOrder() {
        if (start.getX() > end.getX() || start.getY() > end.getY()) {
            Point temp = new Point(start.getX(), start.getY());
            start = end;
            end = temp;
        }
    }

    private void setOrientation() {
        if (start.getX().equals(end.getX())) this.orientation = Orientation.VERTICAL;
        else this.orientation = Orientation.HORIZONTAL;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        if (obstacleString.length() == 11) {
            //Seperates into ["(2,3)","(10,3)"] Array
            String[] limits = obstacleString.split("-");
            Point tempStart = new Point(limits[0]);
            Point tempEnd = new Point(limits[1]);
            return new Obstacle(tempStart, tempEnd);
        } else throw new RuntimeException("Obstical has to many parts");
    }

}

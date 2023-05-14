package thkoeln.st.st2praktikum.exercise;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable @EqualsAndHashCode
public class Wall {
    @Getter private Point start;
    @Getter private Point end;

    protected Wall() { }

    private void check(){
        if (start.getX() != end.getX() && start.getY() != end.getY())
            throw new InvalidCommandException("Diagonals not sut supported");
        if (start.getX() > end.getX())
            switchPoints();
        if (start.getY() > end.getY())
            switchPoints();
    }

    private void switchPoints(){
        var t = start;
        start = end;
        end = t;
    }

    public Wall(Point start, Point end) {
        this.start = start;
        this.end = end;
        check();
    }

    public Wall(int startX, int startY, int endX, int endY){
        start = new Point(startX, startY);
        end = new Point(endX, endY);
        check();
    }


    public Wall(String s){
        var arr = s.split("-");
        if (arr.length != 2) throw new InvalidCommandException("Invalid arguments size, expected 2 got " + arr.length);
        start = new Point(arr[0]);
        end = new Point(arr[1]);
        check();
    }

    public Boolean blocks(Point point, TaskType dir){
        if (dir == TaskType.NORTH && start.getY() == point.getY() + 1 && point.getX() >= start.getX() && point.getX() < end.getX())
            return true;
        if (dir == TaskType.EAST && start.getX() == point.getX() + 1 && point.getY() >= start.getY() && point.getY() < end.getY())
            return true;
        if (dir == TaskType.SOUTH && start.getY() == point.getY() && point.getX() >= start.getX() && point.getX() < end.getX())
            return true;
        if (dir == TaskType.WEST && start.getX() == point.getX() && point.getY() >= start.getY() && point.getY() < end.getY())
            return true;
        return false;
    }
}

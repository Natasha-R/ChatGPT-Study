package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Wall {
    private Point start, end;


    public Wall(String wallString) {
        String[] pointsData = wallString.split("-");
        String[] startData = pointsData[0].replace("(", "").replace(")", "").split(",");
        String[] endData = pointsData[1].replace("(", "").replace(")", "").split(",");
        this.start = new Point(pointsData[0]);
        this.end = new Point(pointsData[1]);

    }

    public boolean isCross(Point position, String direction) {
        switch (direction) {
            case "no": return position.getY() + 1 == start.getY() && position.getX() >= start.getX()&& position.getX() < end.getX();
            case "so": return position.getY()  == start.getY() && position.getX() >= start.getX()&& position.getX() < end.getX();
            case "ea": return position.getX() +1 == start.getX() && position.getY() >= start.getY()&& position.getY() < end.getY();
            case "we": return position.getX()  == start.getX() && position.getY() >= start.getY()&& position.getY() < end.getY();
        }


        return false;
    }


}

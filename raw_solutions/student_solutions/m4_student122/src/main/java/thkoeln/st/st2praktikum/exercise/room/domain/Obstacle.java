package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;


@Embeddable
@Setter
@Getter
public class Obstacle {

    @Embedded
    @Column(insertable = false,updatable = false)
    private Point start;
    @Embedded
    @Column(insertable = false,updatable = false)
    private Point end;


    public Obstacle(Point pos1, Point pos2) {

        if(!pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY())){
            throw new RuntimeException();
        }


        if(pos2.getX() < pos1.getX() || pos2.getY() < pos1.getY()){
            this.start = pos2;
            this.end = pos1;
        }
        else {
            this.start = pos1;
            this.end = pos2;
        }
    }
    public Obstacle(String obstacleString) {
        String[] obstacleEndPoints = obstacleString.split("-");
        List<Point> pointList = new ArrayList<>();
        for(String pointString : obstacleEndPoints){
            Point point = new Point(pointString);
            pointList.add(point);
        }

        this.start = pointList.get(0);
        this.end = pointList.get(1);

    }

    public Obstacle() {

    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        String[] obstacleEndPoints = obstacleString.split("-");
        List<Point> pointList = new ArrayList<>();
        for(String pointString : obstacleEndPoints){
            Point point = new Point(pointString);
            pointList.add(point);
        }
        return new Obstacle(pointList.get(0),pointList.get(1));
    }
    
}

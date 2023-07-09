package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.GeometricPositionDesignations;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;


@Getter
@Embeddable
public class Barrier {

    @Id
    private String barrierId;

    @Embedded
    private Point start;

    @Embedded
    private Point end;

    private GeometricPositionDesignations geometricPositionDesignations = null;


    public Barrier(Point pos1, Point pos2) {
        this.barrierId = pos1.toString() + "-" + pos2.toString();
        this.checkPointOrder(pos1,pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) throws IllegalArgumentException {

        this.barrierId = barrierString;

        String[] barrierItem = barrierString.split("-");

        final Point tempStart = new Point(barrierItem[0]);
        final Point tempEnd = new Point(barrierItem[1]);
        this.checkPointOrder(tempStart,tempEnd);
    }

    public static Barrier fromString(String barrierString) throws IllegalArgumentException {
        return new Barrier(barrierString);
    }

    protected Barrier(){}

    private void checkPointOrder(Point start, Point end){
        if(start.getX().equals(end.getX())){
            this.geometricPositionDesignations = GeometricPositionDesignations.VERTICAL;
            if(this.checkBarrierPointsInOrder(start.getY(), end.getY())){
                this.start = start;
                this.end = end;
            }else{
                this.end = start;
                this.start = end;
            }
        }
        if(start.getY().equals(end.getY())){
            this.geometricPositionDesignations = GeometricPositionDesignations.HORIZONTAL;
            if(this.checkBarrierPointsInOrder(start.getX(), end.getX())){
                this.start = start;
                this.end = end;
            }else{
                this.end = start;
                this.start = end;
            }
        }
        if(this.geometricPositionDesignations == null){
            throw new IllegalStateException("GeometricPositionDesignation is null");
        }
    }

    public Boolean checkBarrierPointsInOrder(Integer start, Integer end) {
        return start < end;
    }
}

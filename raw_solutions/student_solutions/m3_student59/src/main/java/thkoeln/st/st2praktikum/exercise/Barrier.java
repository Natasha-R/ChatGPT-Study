package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Entity
public class Barrier {

    @Id
    private final UUID barrierId = UUID.randomUUID();

    @Embedded
    private Point start;

    @Embedded
    private Point end;

    private GeometricPositionDesignations geometricPositionDesignations = null;


    public Barrier(Point pos1, Point pos2) {
        this.checkPointOrder(pos1,pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) throws IllegalArgumentException {

        String[] barrierItem = barrierString.split("-");

        final Point tempStart = new Point(barrierItem[0]);
        final Point tempEnd = new Point(barrierItem[1]);
        this.checkPointOrder(tempStart,tempEnd);
    }

    protected Barrier(){}

    private void checkPointOrder(Point start, Point end){
        if(start.getX().equals(end.getX())){
            this.geometricPositionDesignations = GeometricPositionDesignations.VERTICAL;
            if(BarrierService.checkBarrierPointsInOrder(start.getY(), end.getY())){
                this.start = start;
                this.end = end;
            }else{
                this.end = start;
                this.start = end;
            }
        }
        if(start.getY().equals(end.getY())){
            this.geometricPositionDesignations = GeometricPositionDesignations.HORIZONTAL;
            if(BarrierService.checkBarrierPointsInOrder(start.getX(), end.getX())){
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
}

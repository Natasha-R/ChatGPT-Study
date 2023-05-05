package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.world2.services.BarrierService;
import thkoeln.st.st2praktikum.exercise.world2.types.GeometricPositionDesignations;

public class Barrier {

    @Getter
    private Point start;
    @Getter
    private Point end;
    @Getter
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

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public GeometricPositionDesignations getGeometricPositionDesignations(){ return this.geometricPositionDesignations; };
}

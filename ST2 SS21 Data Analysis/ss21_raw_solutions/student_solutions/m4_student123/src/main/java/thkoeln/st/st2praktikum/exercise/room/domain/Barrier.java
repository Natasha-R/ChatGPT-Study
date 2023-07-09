package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@Setter(AccessLevel.PROTECTED)
public class Barrier {

    @Embedded
    private Vector2D start;
    @Embedded
    private Vector2D end;


    protected Barrier() {}
    public Barrier(Vector2D pos1, Vector2D pos2) {
        checkIfBarriersAreDiagonal(pos1,pos2);
        if(pos1.getX()+pos1.getY()<=pos2.getX()+pos2.getY()){
            this.start = pos1;
            this.end = pos2;
        }else {
            this.start = pos2;
            this.end = pos1;
        }
    }
    
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {

        Barrier newBarrier = new Barrier();
        if(barrierString.matches("^\\([0-9]+,[0-9]+\\)-\\([0-9]+,[0-9]+\\)$")) {
            String[] barrierParam = barrierString.split("-");
            newBarrier.checkIfBarriersAreDiagonal(Vector2D.fromString(barrierParam[0]),Vector2D.fromString(barrierParam[1]));
            Vector2D pos1 = Vector2D.fromString(barrierParam[0]);
            Vector2D pos2 = Vector2D.fromString(barrierParam[1]);
            if(pos1.getX()+pos1.getY()<=pos2.getX()+pos2.getY()){
                newBarrier.start = pos1;
                newBarrier.end = pos2;
            }else {
                newBarrier.start = pos2;
                newBarrier.end = pos1;
            }
            return newBarrier;
        }
        else throw new IllegalArgumentException("barrierString did not match Format. String: " + barrierString);
    }
    private void checkIfBarriersAreDiagonal(Vector2D start, Vector2D end) {
        if (start.getX() != end.getX() && start.getY() != end.getY()) throw new IllegalArgumentException("Vector2Ds cant be diagonal to each other!");
    }
    
}

package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Barrier {

    private Coordinate start;
    private Coordinate end;


    public Barrier(Coordinate pos1, Coordinate pos2) {
        if(pos1.getX().equals(pos2.getX()) || pos1.getY().equals(pos2.getY())) {
            if (pos1.getX()+pos1.getY() > pos2.getX()+pos2.getY()) {
                this.start = pos2;
                this.end = pos1;
            }else {
                this.start = pos1;
                this.end = pos2;
            }
        }else throw new RuntimeException();
    }
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
    private static boolean isTheStringCorrect(String barrier){

        if (barrier.length() >= 11){
            if (barrier.replaceAll("[0-9]","").equals("(,)-(,)") && barrier.replaceAll("[^0-9]","").length()>=4) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {

        Integer xStart = Integer.parseInt(String.valueOf(barrierString.charAt(1)));
        Integer yStart = Integer.parseInt(String.valueOf(barrierString.charAt(3)));
        Integer xEnd = Integer.parseInt(String.valueOf(barrierString.charAt(7)));
        Integer yEnd = Integer.parseInt(String.valueOf(barrierString.charAt(9)));

        if (isTheStringCorrect(barrierString)){
            if (xStart.equals(xEnd) || yStart.equals(yEnd)) {
                if((xStart+yStart<xEnd+yEnd)) {
                    return new Barrier(new Coordinate(xStart,yStart),new Coordinate(xEnd,yEnd));
                }else {
                    return new Barrier(new Coordinate(xEnd,yEnd),new Coordinate(xStart,yStart));
                }
            }else throw new RuntimeException();

        }else  throw new RuntimeException();    }
    
}

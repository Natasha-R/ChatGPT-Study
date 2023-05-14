package thkoeln.st.st2praktikum.exercise.field.domain;

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


    public Barrier(Coordinate start, Coordinate end) {
        if (start.getX().equals(end.getX())){
            if (start.getY() < end.getY()){
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }
        } else if (start.getY().equals(end.getY())) {
            if (start.getX() < end.getX()){
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }
        } else throw new RuntimeException("Barrier must be either Horizontal or Vertical");
    }

    public boolean isVertical() {
        return start.getX().equals(end.getX());
    }

    public boolean isHorizontal() {
        return start.getY().equals(end.getY());
    }
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        if(!barrierString.matches("\\p{Punct}\\p{Digit}\\p{Punct}\\p{Digit}\\p{Punct}\\p{Punct}\\p{Punct}\\p{Digit}\\p{Punct}\\p{Digit}\\p{Punct}")){
            throw new RuntimeException("Inavalid barrierString");
        }


        Integer[] barrierStart = new Integer[2];
        Integer[] barrierEnd = new Integer[2];
        try {
            String cleanedBarrierString =
                    barrierString.replaceAll("\\(", "").replaceAll("\\)", "");

            String[] coordsString = cleanedBarrierString.split("-");

            String[] barrierStartString = coordsString[0].split(",");
            barrierStart[0] = Integer.parseInt(barrierStartString[0]);
            barrierStart[1] = Integer.parseInt(barrierStartString[1]);

            String[] barrierEndString = coordsString[1].split(",");
            barrierEnd[0] = Integer.parseInt(barrierEndString[0]);
            barrierEnd[1] = Integer.parseInt(barrierEndString[1]);
        } catch (Exception e) {
            throw new RuntimeException("Inavalid barrierString");
        }


        Coordinate firstCoordinate = new Coordinate(barrierStart[0],barrierStart[1]);
        Coordinate secondCoordinate = new Coordinate(barrierEnd[0], barrierEnd[1]);

        if (firstCoordinate.getX().equals(secondCoordinate.getX())){
            if (firstCoordinate.getY() < secondCoordinate.getY()){
                return new Barrier(firstCoordinate, secondCoordinate);
            } else {
                return new Barrier(secondCoordinate, firstCoordinate);
            }
        } else if (firstCoordinate.getY().equals(secondCoordinate.getY())) {
            if (firstCoordinate.getX() < secondCoordinate.getX()){
                return new Barrier(firstCoordinate, secondCoordinate);
            } else {
                return new Barrier(secondCoordinate, firstCoordinate);
            }
        } else throw new RuntimeException("Barrier must be either Horizontal or Vertical");
    }
    
}

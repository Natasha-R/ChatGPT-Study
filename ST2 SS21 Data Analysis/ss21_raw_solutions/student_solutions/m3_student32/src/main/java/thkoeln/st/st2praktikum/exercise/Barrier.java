package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Barrier {

    private Coordinate start;
    private Coordinate end;


    public Barrier(Coordinate pos1, Coordinate pos2) {

        if (pos1.getX().equals(pos2.getX())){
            if (pos1.getY() < pos2.getY()){
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        } else if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() < pos2.getX()){
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        } else throw new RuntimeException("Barrier must be either Horizontal or Vertical");
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {

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
                this.start = firstCoordinate;
                this.end = secondCoordinate;
            } else {
                this.start = secondCoordinate;
                this.end = firstCoordinate;
            }
        } else if (firstCoordinate.getY().equals(secondCoordinate.getY())) {
            if (firstCoordinate.getX() < secondCoordinate.getX()){
                this.start = firstCoordinate;
                this.end = secondCoordinate;
            } else {
                this.start = secondCoordinate;
                this.end = firstCoordinate;
            }
        } else throw new RuntimeException("Barrier must be either Horizontal or Vertical");
    }

    public boolean isVertical() {
        return start.getX().equals(end.getX());
    }

    public boolean isHorizontal() {
        return start.getY().equals(end.getY());
    }
}

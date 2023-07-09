package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable
@Getter
@EqualsAndHashCode
public class Barrier {

    @Setter
    @Getter
    @Embedded
    private Coordinate start;

    @Setter
    @Getter
    @Embedded
    private Coordinate end;

    public Barrier(Coordinate pos1, Coordinate pos2) {
        if (pos1.getX() < pos2.getX() & pos1.getY().equals(pos2.getY())
                || pos1.getX().equals(pos2.getX())
                & pos1.getY() < (pos2.getY())) {
            this.start = pos1;
            this.end = pos2;
        } else if (pos1.getX() > pos2.getX() & pos1.getY().equals(pos2.getY())
                || pos1.getX().equals(pos2.getX())
                & pos1.getY() > (pos2.getY())) {
            this.start = pos2;
            this.end = pos1;
        } else throw new IllegalArgumentException("Can't add a diagonal Barrier");
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        if (barrierString.matches("[(]+[0-9]+[,]+[0-9]+[)]+[-]+[(]+[0-9]+[,]+[0-9]+[)]")) {
            String barrierUnStrung = barrierString.replace("(", "");
            barrierUnStrung = barrierUnStrung.replace(")", "");
            barrierUnStrung = barrierUnStrung.replace("-", ",");
            String[] barrierArray = barrierUnStrung.split(",");
            int[] coordinateArray = new int[barrierArray.length];
            for (int i = 0; i < barrierArray.length; i++) {
                coordinateArray[i] = Integer.parseInt(barrierArray[i].trim());
            }

            Coordinate start = new Coordinate(coordinateArray[0], coordinateArray[1]);
            Coordinate end = new Coordinate(coordinateArray[2], coordinateArray[3]);
            return new Barrier(start, end);
        } else throw new IllegalArgumentException("Not a valid Barrier");
    }

    public Barrier(String barrierString) {
        if (barrierString.matches("[(]+[0-9]+[,]+[0-9]+[)]+[-]+[(]+[0-9]+[,]+[0-9]+[)]")) {
            String barrierUnStrung = barrierString.replace("(", "");
            barrierUnStrung = barrierUnStrung.replace(")", "");
            barrierUnStrung = barrierUnStrung.replace("-", ",");
            String[] barrierArray = barrierUnStrung.split(",");
            int[] coordinateArray = new int[barrierArray.length];
            for (int i = 0; i < barrierArray.length; i++) {
                coordinateArray[i] = Integer.parseInt(barrierArray[i].trim());
            }
            this.start = new Coordinate(coordinateArray[0], coordinateArray[1]);
            this.end = new Coordinate(coordinateArray[2], coordinateArray[3]);
        } else throw new IllegalArgumentException("Not a valid Barrier");
    }

    protected Barrier() {
    }
}

package thkoeln.st.st2praktikum.exercise.room.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.InvalidStringError;
import thkoeln.st.st2praktikum.exercise.domainprimitives.NoDiagonalBarriersAllowed;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Barrier extends AbstractEntity {



    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;

    private UUID id;
    @Id
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Barrier(Coordinate pos1, Coordinate pos2) {

        if(!pos1.getY().equals(pos2.getY())&&!pos1.getX().equals(pos2.getX())){
            throw new NoDiagonalBarriersAllowed("Es sind keine Diagonalen Barrieren erlaubt");
        }
        if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() > pos2.getX()) {
                this.start = pos2;
                this.end = pos1;
            }else {
                this.start = pos1;
                this.end = pos2;
            }
        } else {
            if (pos1.getX().equals(pos2.getX())) {
                if (pos1.getY() > pos2.getY()) {
                    this.start = pos2;
                    this.end = pos1;
                }else {
                    this.start = pos1;
                    this.end = pos2;
                }
            }

        }
    }


    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
       Coordinate start = null;
        Coordinate end = null;


        var firstChar = Character.getType(barrierString.charAt(1));
        var secondChar = Character.getType(barrierString.charAt(3));
        var thirdChar = Character.getType(barrierString.charAt(7));
        var fourthChar = Character.getType(barrierString.charAt(9));
        Integer compareInt = 1;
        if (barrierString.charAt(0) != '(' ||
                Integer.class.isInstance(firstChar) != Integer.class.isInstance(compareInt) ||
                barrierString.charAt(2) != ',' ||
                Integer.class.isInstance(secondChar) != Integer.class.isInstance(compareInt) ||
                barrierString.charAt(4) != ')'||
                barrierString.charAt(5) != '-'
                ||      barrierString.charAt(6)!= '('
                ||      Integer.class.isInstance(thirdChar) != Integer.class.isInstance(compareInt)
                ||      barrierString.charAt(8) != ','
                ||      Integer.class.isInstance(fourthChar) != Integer.class.isInstance(compareInt)
                ||      barrierString.charAt(10) != ')'
        ) {
            throw new InvalidStringError("Dies ist kein zugelassener String");
        } else {
            barrierString = barrierString.replaceAll("[()]", "");
            String[] barrierCoordinateString = barrierString.split("-");
            for (int i = 0; i < 1; i++) {
                start = Coordinate.fromString("(" + barrierCoordinateString[0] + ")");
                end = Coordinate.fromString("(" + barrierCoordinateString[1] + ")");
            }
        }
        Barrier barrier =new  Barrier(start,end);
        barrier.setId(UUID.randomUUID());
        return barrier;
    }


    public Coordinate getStart() {

        return start;
    }

    public Coordinate getEnd() {

        return end;
    }




}

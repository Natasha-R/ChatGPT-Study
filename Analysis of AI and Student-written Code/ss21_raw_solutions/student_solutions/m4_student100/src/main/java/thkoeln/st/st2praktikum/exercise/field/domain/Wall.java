package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.InvalidStringError;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.NoDiagonalBarriersException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wall extends AbstractEntity {


  
    @Embedded
    private  Coordinate start;
    @Embedded
    private  Coordinate end;

    private UUID wallid;
    @Id
    public UUID getWallid() {
        return wallid;
    }

    public void setWallid(UUID id) {
        this.wallid = id;
    }




    public Wall(Coordinate pos1, Coordinate pos2) {

        if (!pos1.getY().equals(pos2.getY()) && !pos1.getX().equals(pos2.getX())) {
            throw new NoDiagonalBarriersException("Es sind keine diagonalen Wandkoordinaten" +
                    "erlaubt ");
        }




        if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() > pos2.getX()) {
                this.start = pos2;
                this.end = pos1;
            } else {
                this.start = pos1;
                this.end = pos2;
            }
        } else {
            if (pos1.getX().equals(pos2.getX())) {
                if (pos1.getY() > pos2.getY()) {
                    this.start = pos2;
                    this.end = pos1;

                } else {
                    this.start = pos1;
                    this.end = pos2;

                }
            }



        }
    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static  Wall fromString(String wallString) {
        Coordinate start = null;
        Coordinate end = null;

        int firstNumber = Character.getNumericValue(wallString.charAt(1));
        int secondNumber = Character.getNumericValue(wallString.charAt(3));
        int thirdNumber = Character.getNumericValue(wallString.charAt(7));
        int fourthNumber = Character.getNumericValue(wallString.charAt(9));
        Integer.class.isInstance(firstNumber);
        int comparenumber = 0;

        if(wallString.charAt(0) != '('
                || Integer.class.isInstance(firstNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(2) != ','
                || Integer.class.isInstance(secondNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(4) != ')'
                || wallString.charAt(5) != '-'
                ||      wallString.charAt(6) != '('
                || Integer.class.isInstance(thirdNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(8) != ','
                || Integer.class.isInstance(fourthNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(10) != ')')
        {
            throw new InvalidStringError("Dies ist kein erlaubter String");
        } else {

            wallString = wallString.replaceAll("[()]", "");
            String[] wallcoordinatestring = wallString.split("-");
            for(int i=0 ; i<1; i++) {
                start =  Coordinate.fromString("(" + wallcoordinatestring[0] + ")");
                end   =  Coordinate.fromString("(" + wallcoordinatestring[1] + ")");
            }
        }
        Wall wall = new Wall(start, end);
        wall.setWallid(UUID.randomUUID());
        return wall;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }


}

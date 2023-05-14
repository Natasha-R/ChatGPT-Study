package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.WrongFormatException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embedded;
import java.util.ArrayList;


@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Wall extends AbstractEntity {

    @Getter @Embedded
    private Coordinate start;
    @Getter @Embedded
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {
        checkFormat(pos1, pos2);
    }
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        Coordinate startFromString;
        Coordinate endFromString;

        if(!wallString.contains("-")){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }
        String segments[] = wallString.split("-");

        if(segments.length != 2){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }

        try{
            startFromString = Coordinate.fromString(segments[0]);
            endFromString = Coordinate.fromString(segments[1]);

        }catch( Exception e){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }


        if(startFromString.getX() > endFromString.getX() || startFromString.getY() > endFromString.getY()){
            Coordinate temp = endFromString;
            endFromString = startFromString;
            startFromString = temp;
        }

        if(startFromString.getX() != endFromString.getX() && startFromString.getY() != endFromString.getY()){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }

        return new Wall(startFromString,endFromString);



    }


    private void checkFormat(Coordinate start, Coordinate end){
        if(start.getX() > end.getX() || start.getY() > end.getY()){
            Coordinate temp = end;
            end = start;
            start = temp;
        }

        if(start.getX() != end.getX() && start.getY() != end.getY()){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }


        this.start = start;
        this.end = end;

    }

}

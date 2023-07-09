package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import thkoeln.st.st2praktikum.exercise.Blocking;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@EqualsAndHashCode
public class Wall implements Blocking {

    private Vector2D start;
    private Vector2D end;


    public Wall(Vector2D start, Vector2D end) {
        if(start.getX() != end.getX() && start.getY() != end.getY())
            throw new UnsupportedOperationException("a wall must be either vertical or horizontal");


        Vector2D[] coordinates = sortCoordinates(start, end);
        this.start = coordinates[0];
        this.end = coordinates[1];
        
    }
    
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        try {
            if(!wallString.startsWith("(")&&wallString.endsWith(")"))
                throw new UnsupportedOperationException("wrong format");
            if(!(StringUtils.countOccurrencesOf(wallString, "(") == 2 && StringUtils.countOccurrencesOf(wallString, "(") == 2 ))
                throw new UnsupportedOperationException("wrong format");
            if(!(StringUtils.countOccurrencesOf(wallString, ",") == 2 &&  StringUtils.countOccurrencesOf(wallString, "-") == 1 ))
                throw new UnsupportedOperationException("wrong format");

            String[] splitWallString = wallString.split("[-(),]");

            Vector2D pos1 = new Vector2D(Integer.parseInt(splitWallString[1]),Integer.parseInt(splitWallString[2]));
            Vector2D pos2 = new Vector2D(Integer.parseInt(splitWallString[5]),Integer.parseInt(splitWallString[6]));


            Vector2D[] coordinates = sortCoordinates(pos1, pos2);
            return new Wall(coordinates[0], coordinates[1]);
        }catch (Exception e){
            throw new UnsupportedOperationException("wrong format");
        }
    }

    @Override
    public Boolean isBlockingCoordinate(Vector2D coordinates){
        if(coordinates.getX().equals(start.getX()) && ( start.getY() <= coordinates.getY() && coordinates.getY() < end.getY()) )
            return true;
        if(coordinates.getY().equals(start.getY()) && ( start.getX() <= coordinates.getX() && coordinates.getX() < end.getX()) )
            return true;
        return false;
    }

    //set [0] to be closest to (0,0)
    private static Vector2D[] sortCoordinates(Vector2D pos1, Vector2D pos2){
        if(pos1.getX() >= pos2.getX() && pos1.getY() >= pos2.getY()) {
            return new Vector2D[]{pos2,pos1};
        }else {
            return new Vector2D[]{pos1,pos2};
        }
    }
    
}

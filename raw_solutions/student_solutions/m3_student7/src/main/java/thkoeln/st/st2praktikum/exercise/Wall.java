package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@EqualsAndHashCode
public class Wall implements Blocking {

    private Vector2D start;
    private Vector2D end;


    public Wall(Vector2D pos1, Vector2D pos2) throws WallException {
        if(pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new WallException("a wall must be either vertical or horizontal");


        Vector2D[] coordinates = sortCoordinates(pos1, pos2);
        this.start = coordinates[0];
        this.end = coordinates[1];
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) throws WallException {
        try {
            if(!wallString.startsWith("(")&&wallString.endsWith(")"))
                throw new WallException("wrong format");
            if(!(StringUtils.countOccurrencesOf(wallString, "(") == 2 && StringUtils.countOccurrencesOf(wallString, "(") == 2 ))
                throw new WallException("wrong format");
            if(!(StringUtils.countOccurrencesOf(wallString, ",") == 2 &&  StringUtils.countOccurrencesOf(wallString, "-") == 1 ))
                throw new WallException("wrong format");

            String[] splitWallString = wallString.split("[-(),]");

            Vector2D pos1 = new Vector2D(Integer.parseInt(splitWallString[1]),Integer.parseInt(splitWallString[2]));
            Vector2D pos2 = new Vector2D(Integer.parseInt(splitWallString[5]),Integer.parseInt(splitWallString[6]));


            Vector2D[] coordinates = sortCoordinates(pos1, pos2);
            this.start = coordinates[0];
            this.end = coordinates[1];
        }catch (Exception e){
            throw new WallException("wrong format");
        }
    }

    //set [0] to be closest to (0,0)
    private Vector2D[] sortCoordinates(Vector2D pos1, Vector2D pos2){
        if(pos1.getX() >= pos2.getX() && pos1.getY() >= pos2.getY()) {
            return new Vector2D[]{pos2,pos1};
        }else {
            return new Vector2D[]{pos1,pos2};
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

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}

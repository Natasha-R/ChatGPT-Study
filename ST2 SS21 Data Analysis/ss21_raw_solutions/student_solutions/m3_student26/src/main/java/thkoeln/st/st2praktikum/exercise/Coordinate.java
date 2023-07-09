package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
@Getter
public class Coordinate {

    @Getter
    private Integer x;
    @Getter
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        checkFormat(x,y);
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {

        //Coordinate
        //a coordinate needs a constructor to be created from a string, e.G. "(2,1)". Invalid strings need to be detected.
        //a coordinate has to be positive
        //invalid strings need to be detected

        int xFromString;
        int yFromString;


        if(!coordinateString.contains(",")){
            throw new WrongFormatException("Wrong Format for Coordinate, try (x,y) ");
        }

        String segments[] = coordinateString.split(",");

        char[] temp = coordinateString.toCharArray();

        if(temp[0] != '(' || temp[temp.length-1] != ')'){
            throw new WrongFormatException("Wrong Format for Coordinate, try (x,y) ");
        }


        if(segments.length > 2 || segments.length < 2){
            throw new WrongFormatException("Wrong Format for Coordinate, try (x,y) ");
        }

        try{
            xFromString = Integer.parseInt(segments[0].replace("("," ").trim());
            yFromString = Integer.parseInt(segments[1].replace(")"," ").trim());

        }catch( Exception e){
            throw new WrongFormatException("Wrong Format for Coordinate, try (x,y) ");
        }



        checkFormat(xFromString,yFromString);


    }

    private void checkFormat(int x, int y){
        if(x < 0 || y < 0){
            throw new WrongFormatException("Negative Coordinates are not allowed");
        }

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}

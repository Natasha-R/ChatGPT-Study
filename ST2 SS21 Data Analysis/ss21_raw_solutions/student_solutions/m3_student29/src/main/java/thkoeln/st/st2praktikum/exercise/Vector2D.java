package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Embeddable
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString){
        if (this.isValidVector2DString(vector2DString)){
            var vector = this.fromStringToVector2D(vector2DString);
            var xVector = vector.x;
            var yVector = vector.y;
            if(this.isPositiveStringVector2D(vector2DString)){
                this.x = xVector;
                this.y = yVector;
            } else {
                throw new UnsupportedOperationException("Coordinate muss only have positive value!");
            }
        } else {
            throw new UnsupportedOperationException("Your coordinate format is invalid!\n Please make sure your coordinate has this form: (x,y)! ");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
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

    public boolean isValidVector2DString(String stringCoordinate){
        Pattern pattern = Pattern.compile("\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(stringCoordinate);
        return matcher.matches();
    }

    public Vector2D fromStringToVector2D(String vector2DString){
        var vectorStringSize = vector2DString.length();
        var subString = vector2DString.substring(1, vectorStringSize - 1);
        var vector = subString.split(",");
        var x = vector[0];
        var y = vector[1];

        var xVectorToInteger = Integer.parseInt(x);
        var yVectorToInteger = Integer.parseInt(y);

        return new Vector2D(xVectorToInteger, yVectorToInteger);
    }

    public boolean isPositiveStringVector2D(String vector2DString){
        Vector2D vector = fromStringToVector2D(vector2DString);
        return vector.x >= 0 && vector.y >= 0;
    }

    public boolean isPositiveVector2D(){
        return x >= 0 && y >= 0;
    }



}

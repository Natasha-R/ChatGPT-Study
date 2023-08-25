package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) throws Vector2DException {
        if(x >= 0 && y >= 0) {
            this.x = x;
            this.y = y;
        }else
            throw new Vector2DException("only positive coordinates allowed");
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D (String vector2DString) throws Vector2DException {
        try {
            if(!(vector2DString.startsWith("(")&&vector2DString.endsWith(")")&& StringUtils.countOccurrencesOf(vector2DString, ",") == 1))
                throw new Vector2DException("wrong format");

            String[] splitCoordinates = vector2DString.replace("(","").replace(")","").split(",");

            Integer x = Integer.parseInt(splitCoordinates[0]);
            Integer y = Integer.parseInt(splitCoordinates[1]);

            if(x >= 0 && y >= 0) {
                this.x = x;
                this.y = y;
            }else
                throw new Vector2DException("only positive coordinates allowed");

        }catch (Exception e){
            throw new Vector2DException("wrong format");
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

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}

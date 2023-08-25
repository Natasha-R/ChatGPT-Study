package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Setter
@NoArgsConstructor
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;

        if(this.x < 0 || this.y < 0) {
            throw new RuntimeException();
        }
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        final String regex = "\\(([0-9]*),([0-9]*)\\)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(vector2DString);

        if (matcher.find()) {
            this.x = Integer.parseInt(matcher.group(1));
            this.y = Integer.parseInt(matcher.group(2));
        } else {
            throw new RuntimeException();
        }

        if(this.x < 0 || this.y < 0) {
            throw new RuntimeException();
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

    public Boolean isGreaterThan(Vector2D that) {
        return this.getX().equals(that.getX()) && this.getY() > that.getY()
                || this.getY().equals(that.getY()) && this.getX() > that.getX();
    }
}

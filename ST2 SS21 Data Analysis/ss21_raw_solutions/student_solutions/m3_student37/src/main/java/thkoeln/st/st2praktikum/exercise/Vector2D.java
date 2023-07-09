package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class Vector2D {

    @Setter
    @Column(insertable = false,updatable = false)
    private Integer x;
    @Setter
    @Column(insertable = false,updatable = false)
    private Integer y;


    public Vector2D(Integer px, Integer py) {
        if (px<0||py<0) {
            throw new RuntimeException("Darf nicht negativ sein");
        }
        x = px;
        y = py;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) throws RuntimeException {
        if (vector2DString.length()>5||vector2DString.contains("-")) {
            throw new RuntimeException("Falscher String");
        }
        if (vector2DString.contains("(")) {
            String[] string = vector2DString.split(",");
            int x = Integer.parseInt(string[0].substring(1));
            if (x < 0) {
                throw new RuntimeException("Negative Zahlen sind nicht erlaubt");
            } else {
                this.x = x;
            }
            int y = Integer.parseInt(string[1].substring(0, 1));
            if (y < 0) {
                throw new RuntimeException("Negative Zahlen sind nicht erlaubt");
            } else {
                this.y = y;
            }
        } else {
            String[] string = vector2DString.split(",");
            int x = Integer.parseInt(string[0]);
            int y = Integer.parseInt(string[1]);
            this.x=x;
            this.y=y;
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

    public void updateY(int steps) {
        y+=steps;
    }
    public void updateX(int steps) {
        x+=steps;
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

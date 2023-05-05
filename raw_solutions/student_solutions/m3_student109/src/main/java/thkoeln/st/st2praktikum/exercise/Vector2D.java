package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if (x >= 0 && y>= 0) {
            this.x = x;
            this.y = y;
        }
        else {
            throw new RuntimeException("Werte müssen positiv sein");
        }
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if (vector2DString.matches("\\p{Punct}\\p{Digit}\\p{Punct}\\p{Digit}\\p{Punct}")) {
            String vectorString[] = vector2DString.split(",");
            vectorString[0] = vectorString[0].replace("(", " ").trim();
            vectorString[1] = vectorString[1].replace(")", " ").trim();
            if (Integer.parseInt(vectorString[0]) >= 0 && Integer.parseInt(vectorString[1]) >= 0) {
                x = Integer.parseInt(vectorString[0]);
                y = Integer.parseInt(vectorString[1]);
            } else {
                throw new RuntimeException("Werte müssen positiv sein");
            }
        }
        else {
            throw new RuntimeException("Falsches Format");
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

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

}

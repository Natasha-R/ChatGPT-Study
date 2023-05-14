package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public static Vector2D fromString(String vector2DString) {
        if (vector2DString.matches("\\p{Punct}\\p{Digit}\\p{Punct}\\p{Digit}\\p{Punct}")) {
            String vectorString[] = vector2DString.split(",");
            vectorString[0] = vectorString[0].replace("(", " ").trim();
            vectorString[1] = vectorString[1].replace(")", " ").trim();
            if (Integer.parseInt(vectorString[0]) >= 0 && Integer.parseInt(vectorString[1]) >= 0) {
                return new Vector2D(Integer.parseInt(vectorString[0]), Integer.parseInt(vectorString[1]));
            } else {
                throw new RuntimeException("Werte müssen positiv sein");
            }
        }
        else {
            throw new RuntimeException("Falsches Format");
        }
    }
    
}

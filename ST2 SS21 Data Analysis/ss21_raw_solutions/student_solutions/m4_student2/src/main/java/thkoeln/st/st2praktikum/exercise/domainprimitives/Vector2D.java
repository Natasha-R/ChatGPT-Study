package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Setter(AccessLevel.PROTECTED)
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if(x >= 0 && y >= 0) {
            this.x = x;
            this.y = y;
        }
        else
            throw new RuntimeException();
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
        Integer x = null;
        Integer y = null;
        if(checkVector(vector2DString)){
            x = Integer.parseInt(vector2DString.replace("(", "").replaceAll(",.*", ""));
            y = Integer.parseInt(vector2DString.replaceAll(".*,", "").replace(")", ""));

            return new Vector2D(x,y);
        }
        else
            throw new RuntimeException();
    }

    private static Boolean checkVector(String vector2DString){
        Integer x = Integer.parseInt(vector2DString.replace("(","").replaceAll(",.*",""));
        Integer y = Integer.parseInt(vector2DString.replaceAll(".*,","").replace(")",""));

        long countComma = vector2DString.chars().filter(ch -> ch == ',').count();

        if(vector2DString.charAt(0) != '(' || vector2DString.charAt(vector2DString.length()-1) != ')') return false;
        if(countComma != 1) return false;
        if(!Character.isDigit(vector2DString.charAt(1)) || !Character.isDigit(vector2DString.charAt(vector2DString.length()-2))) return false;
        if(x != null && y != null){
            if(x < 0 || y < 0) return false;
        }

        return true;
    }
    
}

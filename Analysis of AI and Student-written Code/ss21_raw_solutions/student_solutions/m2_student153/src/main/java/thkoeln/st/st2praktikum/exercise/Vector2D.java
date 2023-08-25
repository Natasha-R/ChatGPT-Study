package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.UUID;
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if(x<0){
            throw new RuntimeException("negative Zahl");
        }
        if(y<0){
            throw new RuntimeException("negative Zahl");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {


        String[] output = vector2DString.split(",");


        if (output.length !=2) {


            throw new RuntimeException("lol3");
        }
        if (Character.getNumericValue(vector2DString.charAt(1))==-1 ||Character.getNumericValue(vector2DString.charAt(3))==-1){
            throw new RuntimeException("lol2");
        }


        this.x = Character.getNumericValue(vector2DString.charAt(1));
        this.y = Character.getNumericValue(vector2DString.charAt(3));


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

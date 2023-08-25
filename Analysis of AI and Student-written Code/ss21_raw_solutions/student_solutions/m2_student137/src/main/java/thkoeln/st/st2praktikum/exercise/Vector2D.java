package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;

    public Vector2D(Integer x, Integer y) {

        if(!(x < 0) && !(y < 0)) {
            this.x = x;
            this.y = y;
        } else {
            throw  new NumberFormatException();
        }


    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {

        // split start and end values at the comma
        String[] Split = vector2DString.split(",");

        // throw exception of more than 2 values have been given for a vector. Not possible!
        if(Split.length> 2) {
            throw  new NumberFormatException();
        }

        ArrayList<String> totalSplit = new ArrayList<>();

        // store split values in new arraylist
        totalSplit.add(Split[0]); // (NUMBER
        totalSplit.add(Split[1]); // NUMBER)

        // try to parse the given vector value to a string. as this has to be an Integer it will throw an error if not given the right value
        try {
            this.x = Integer.parseInt(totalSplit.get(0).substring(1));
        } catch(Exception e) {
            throw  new NumberFormatException();
        }

        String[] secondValueString = totalSplit.get(1).split("\\)");

        // same error test as above for the second value
        try {
            this.y = Integer.parseInt(secondValueString[0]);
        } catch(Exception e) {
            throw  new NumberFormatException();
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

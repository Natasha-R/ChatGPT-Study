package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if (x >= 0 && y >= 0)
        {
            this.x = x;
            this.y = y;
        }
        else
            throw new IllegalArgumentException("A vector2D has to be positive");
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if (validString(vector2DString))
        {
            String splitCoordinates[] = vector2DString.replace("(", "").replace(")", "").split(",");

            this.x = Integer.parseInt(splitCoordinates[0]);
            this.y = Integer.parseInt(splitCoordinates[1]);
        }
        else
        {
            throw new IllegalArgumentException("The Input is not a valid coordinate it needs to be (Integer,Integer) but was " + vector2DString);
        }
    }

    public static Vector2D fromString(String vector2DString)
    {
        if (validString(vector2DString))
        {
            String splitCoordinates[] = vector2DString.replace("(", "").replace(")", "").split(",");

            int splitCoordinateX = Integer.parseInt(splitCoordinates[0]);
            int splitCoordinateY = Integer.parseInt(splitCoordinates[1]);
            return new Vector2D(splitCoordinateX, splitCoordinateY);
        }
        else
        {
            throw new IllegalArgumentException("The Input is not a valid coordinate it needs to be (Integer,Integer) but was " + vector2DString);
        }
    }

    public static boolean validString(String vector2DString)
    {
        if (vector2DString.startsWith("(") && vector2DString.endsWith(")") && vector2DString.contains(","))
        {
            String splitCoordinates[] = vector2DString.replace("(", "").replace(")", "").split(",");
            try
            {
                int splitCoordinateX = Integer.parseInt(splitCoordinates[0]);
                int splitCoordinateY = Integer.parseInt(splitCoordinates[1]);

                if (splitCoordinates.length == 2 && splitCoordinateX >= 0 && splitCoordinateY >= 0)
                    return true;
            }
            catch (Exception ex)
            {

            }
        }
        return false;
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

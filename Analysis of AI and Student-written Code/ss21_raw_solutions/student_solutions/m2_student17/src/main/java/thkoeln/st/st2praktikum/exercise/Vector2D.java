package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.BitPaw.InvalidVectorException;

import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;

    public Vector2D(Integer x, Integer y)
    {
        setX(x);
        setY(y);
    }

    public Vector2D(Vector2D vector)
    {
        setX(vector.x);
        setY(vector.y);
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString)
    {
        boolean validLength = vector2DString.length() >= 5;

        if(!validLength)
        {
            throw new IllegalArgumentException("Invalid length");
        }

        boolean hasBrackets = vector2DString.charAt(0) == '(' && vector2DString.charAt(vector2DString.length() -1) == ')';

        if(!hasBrackets)
        {
            throw new IllegalArgumentException("Invalid brackets");
        }

        int commaIndex = vector2DString.indexOf(',');
        String xText = vector2DString.substring(1, commaIndex);
        String yText = vector2DString.substring(commaIndex+1, vector2DString.length()-1);

        setX(Integer.parseInt(xText));
        setY(Integer.parseInt(yText));
    }

    public void add(final int x, final int y)
    {
        setX(getX() + x);
        setY(getY() + y);
    }

    public void add(final Vector2D position)
    {
        add(position.getX(), position.getY());
    }

    public static boolean isBNearer(Vector2D position, int x, int y, OrderType direction)
    {
        int xDiff;
        int yDiff;

        switch (direction)
        {
            case NORTH:
                xDiff = Math.abs(x - position.getY());
                yDiff = Math.abs(y - position.getY());
                return xDiff > yDiff;


            case SOUTH:
                xDiff = Math.abs(x - position.getY());
                yDiff = Math.abs(y - position.getY());
                return xDiff > yDiff;

            case WEST:
                xDiff = Math.abs(x - position.getX());
                yDiff = Math.abs(y - position.getX());
                return xDiff > yDiff;

            default:
            case EAST:
                xDiff = Math.abs(x - position.getX());
                yDiff = Math.abs(y - position.getX());
                return xDiff > yDiff;
        }
    }

    public void set(final int x,final  int y)
    {
        setX(x);
        setY(y);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    private void setX(int value)
    {
        if(value < 0)
        {
            throw new InvalidVectorException("X cant be negative");
        }

        x = value;
    }

    public Integer getX()
    {
        return x;
    }

    private void setY(final int value)
    {
        if(value < 0)
        {
            throw new InvalidVectorException("Y cant be negative");
        }

        y = value;
    }

    public Integer getY()
    {
        return y;
    }
}

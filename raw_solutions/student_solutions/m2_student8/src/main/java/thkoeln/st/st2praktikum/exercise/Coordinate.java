package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate implements Positionable
{

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) throws RuntimeException
    {
        if (x.intValue()<0 || y.intValue()<0)
            throw new RuntimeException("Invalid Coordinate");
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) throws RuntimeException
    {
        if (!coordinateString.matches("\\(\\d+,\\d+\\)"))
        {
            throw new RuntimeException("Invalid coordinate input pattern");
        }
        String[] splitCoordinate = extractCoordinate(coordinateString);
        this.x = Integer.valueOf(splitCoordinate[0]);
        this.y = Integer.valueOf(splitCoordinate[1]);
    }

    private String[] extractCoordinate(String coordinateString) throws RuntimeException
    {
        coordinateString = coordinateString.replace("(","").replace(")","");
        String[] splitCoordinate = coordinateString.split(",");
        if (Integer.parseInt(splitCoordinate[0])<0)
            throw new RuntimeException("Invalid Coordinate");
        if (Integer.parseInt(splitCoordinate[1])<0)
            throw new RuntimeException("Invalid Coordinate");
        return splitCoordinate;
    }




    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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

    public Integer getX()
    {
        return x;
    }

    public Integer getY()
    {
        return y;
    }

    @Override
    public int getCoordinateX() {
        return x.intValue();
    }

    @Override
    public int getCoordinateY() {
        return y.intValue();
    }

    @Override
    public void setCoordinateX(int position)
    {
        this.x = Integer.valueOf(position);
    }

    @Override
    public void setCoordinateY(int position)
    {
        this.y = Integer.valueOf(position);
    }
}

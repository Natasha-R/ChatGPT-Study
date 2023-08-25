package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.Objects;

public class Position
{
    public int X;
    public int Y;

    public Position(int x, int y)
    {
        Set(x, y);
    }

    public Position(String string)
    {
        boolean validLength = string.length() >= 5;

        if(!validLength)
        {
            throw new IllegalArgumentException("Invalid length");
        }

        boolean hasBrackets = string.charAt(0) == '(' && string.charAt(string.length() -1) == ')';

        if(!hasBrackets)
        {
            throw new IllegalArgumentException("Invalid brackets");
        }

        int commaIndex = string.indexOf(',');
        String xText = string.substring(1, commaIndex);
        String yText = string.substring(commaIndex+1, string.length()-1);

        X = Integer.parseInt(xText);
        Y = Integer.parseInt(yText);
    }

    public void Set(int x, int y)
    {
        X = x;
        Y = y;
    }

    public void Add(Position position)
    {
        X += position.X;
        Y += position.Y;
    }

    public static boolean IsBNearer(Position position, int x, int y, Direction direction)
    {
        int xDiff;
        int yDiff;

        switch (direction)
        {
            case Up:
                xDiff = Math.abs(x - position.Y);
                yDiff = Math.abs(y - position.Y);
                return xDiff > yDiff;


            case Down:
                xDiff = Math.abs(x - position.Y);
                yDiff = Math.abs(y - position.Y);
                return xDiff > yDiff;

            case Left:
                xDiff = Math.abs(x - position.X);
                yDiff = Math.abs(y - position.X);
                return xDiff > yDiff;

            default:
            case Right:
                xDiff = Math.abs(x - position.X);
                yDiff = Math.abs(y - position.X);
                return xDiff > yDiff;
        }
    }

    @Override
    public String toString()
    {
        return "(" + X + "," + Y + ')';    // (2,4)
    }

    public boolean equals(final Position position)
    {
        return X == position.X && Y == position.Y;
    }
}

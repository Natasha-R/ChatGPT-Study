package thkoeln.st.st2praktikum.exercise.BitPaw;

import com.fasterxml.jackson.databind.node.POJONode;
import org.springframework.data.domain.Sort;

public class Point
{
    public int X;
    public int Y;

    public Point()
    {
        Set(0, 0);
    }

    public Point(int x, int y)
    {
        Set(x, y);
    }

    public void Set(int x, int y)
    {
        X = x;
        Y = y;
    }

    public void Add(Point point)
    {
        X += point.X;
        Y += point.Y;
    }

    public static boolean IsBNearer(Point reference, int x, int y, MoveDirection direction)
    {
        int xDiff;
        int yDiff;

        switch (direction)
        {
            case Up:
                xDiff = Math.abs(x - reference.Y);
                yDiff = Math.abs(y - reference.Y);
                return xDiff > yDiff;


            case Down:
                xDiff = Math.abs(x - reference.Y);
                yDiff = Math.abs(y - reference.Y);
                return xDiff > yDiff;

            case Left:
                xDiff = Math.abs(x - reference.X);
                yDiff = Math.abs(y - reference.X);
                return xDiff > yDiff;

            default:
            case Right:
                xDiff = Math.abs(x - reference.X);
                yDiff = Math.abs(y - reference.X);
                return xDiff > yDiff;
        }


    }

    public boolean IsBNearer(Point a, Point b)
    {
        int xDiffA = Math.abs(a.X - X);
        int yDiffA = Math.abs(a.Y - Y);
        int xDiffB = Math.abs(b.X - X);
        int yDiffB = Math.abs(b.Y - Y);

        int deltaX = Math.abs(xDiffA - xDiffB );
        int deltaY = Math.abs(yDiffA - yDiffB );

        if(deltaX > deltaY)
        {
            // Look at X
            return xDiffA < xDiffB;
        }
        else
        {
            // Loook at Y
            return yDiffA < yDiffB;
        }
    }

    @Override
    public String toString()
    {
        return "(" + X + "," + Y + ')';
    }
}

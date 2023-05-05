package thkoeln.st.st2praktikum.exercise.BitPaw;

public class Wall
{
    public Position A;
    public Position B;

    public WallOrientation Orientation;

    public Wall(Position a, Position b)
    {
        Set(a, b);
    }

    public Wall(String string)
    {
        final int splitttoken = string.indexOf('-');
        final String aText = string.substring(0,splitttoken);
        final String bText = string.substring(splitttoken+1);

        Set(new Position(aText), new Position(bText));
    }

    public void Set(Position a, Position b)
    {
        A = a;
        B = b;

        Orientation = (a.X == b.X) ? WallOrientation.Vertical : WallOrientation.Horizontal;
    }

    public int GetWallOrientationValue()
    {
        switch (Orientation)
        {
            case Horizontal:
                return A.Y;

            default:
            case Vertical:
                return A.X;
        }
    }

    public boolean Collide(final Position position, final WallOrientation axis)
    {
        final int x = position.X;
        final int y = position.Y;

        switch (axis)
        {
            case Vertical:
            {
                final int xMax = Math.max(A.X, B.X);
                final int xMin = Math.min(A.X, B.X);

                return  x < xMax && x >= xMin;
            }

            default:
            case Horizontal:
            {
                final int yMax = Math.max(A.Y, B.Y);
                final int yMin = Math.min(A.Y, B.Y);

                return y < yMax && y >= yMin;
            }
        }
    }

    @Override
    public String toString()
    {
        return A.toString() + "-" + B.toString();
    }
}

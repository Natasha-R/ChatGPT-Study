package thkoeln.st.st2praktikum.exercise.BitPaw;

public class Barrier
{
    private Point _from;
    private Point _to;

    public Point From() {return _from;}
    public Point To() {return _to;}

    public Axis Orientation;

    public Barrier(int xA, int yA, int xB, int yB)
    {
        Set(xA, yA, xB, yB);
    }

    public Barrier(Point from, Point to)
    {
        Set(from, to);
    }

    public void Set(int xA, int yA, int xB, int yB)
    {
        Set(new Point(xA, yA), new Point(xB, yB));
    }

    public void Set(Point from, Point to)
    {
        boolean isXSame = from.X == to.X;
        boolean isYSame = from.Y == to.Y;
        boolean isLiniar = (isXSame || isYSame) && !(isXSame && isYSame);

        if(!isLiniar)
        {
            throw new IllegalArgumentException();
        }

        if(isXSame)
        {
            Orientation = Axis.Vertical;
        }

        if(isYSame)
        {
            Orientation = Axis.Horizontal;
        }

        _from = from;
        _to = to;
    }

    public int GetWallOriantationValue()
    {
        switch (Orientation)
        {

            case Horizontal:
                return From().Y;

            default:
            case Vertical:
                return From().X;
        }
    }

    public boolean Collide(final Point point)
    {
        return Collide(point, Axis.Horizontal) && Collide(point, Axis.Vertical);
    }

    @Override
    public String toString()
    {
        return  _from + "-" + _to + "(" + Orientation + ")";
    }

    public boolean Collide(final Point point, final Axis axis)
    {
        final int x = point.X;
        final int y = point.Y;

        switch (axis)
        {
            case Vertical:
            {
                final int xMax = Math.max(_from.X, _to.X);
                final int xMin = Math.min(_from.X, _to.X);

                return  x < xMax && x >= xMin;
            }

            default:
            case Horizontal:
            {
                final int yMax = Math.max(_from.Y, _to.Y);
                final int yMin = Math.min(_from.Y, _to.Y);

                return y < yMax && y >= yMin;
            }
        }
    }
}
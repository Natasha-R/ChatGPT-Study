package thkoeln.st.st2praktikum.exercise;

public class Position
{
    private int X, Y;

    public int getX()
    {
        return X;
    }

    public void setX(int x)
    {
        this.X = x;
    }

    public int getY()
    {
        return Y;
    }

    public void setY(int y)
    {
        this.Y = y;
    }

    public Position(int x, int y)
    {
        this.X = x;
        this.Y = y;
    }
}

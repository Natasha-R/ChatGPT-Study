package thkoeln.st.st2praktikum.exercise;

public class Wall
{
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public Wall(int a, int b, int c, int d)
    {
        startX=a;
        startY=b;
        endX=c;
        endY=d;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}

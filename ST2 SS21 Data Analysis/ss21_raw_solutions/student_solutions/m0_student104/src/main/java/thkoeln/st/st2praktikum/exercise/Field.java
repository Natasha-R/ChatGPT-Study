package thkoeln.st.st2praktikum.exercise;

public class Field
{
    private boolean obstacle;
    private boolean[] walls;
    private int x;
    private int y;

    public Field(int a, int b)
    {
        x=a;
        y=b;
        obstacle = false;

        walls=new boolean[4];

        walls[0]=false;
        walls[1]=false;
        walls[2]=false;
        walls[3]=false;

    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public boolean getWall(String dir) {
        switch (dir)
        {
            case "no":
                return walls[0];
            case "ea":
                return walls[1];
            case "so":
                return walls[2];
            case "we":
                return walls[3];
            default:
                return true;
        }
    }

    public void setWalls(boolean state, int dir) {
        walls[dir]=state;
        if (state=true)
        {
            obstacle=true;
        }
        //if state false check for other walls
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

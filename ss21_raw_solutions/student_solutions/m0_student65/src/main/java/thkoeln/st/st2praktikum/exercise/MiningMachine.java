package thkoeln.st.st2praktikum.exercise;

import java.util.Vector;

public class MiningMachine implements Moveable{
    private int xPos;
    private int yPos;

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    public MiningMachine()
    {
        xPos=0;
        yPos=2;
    }
    public void moveNorth(Field field, int numberOfSteps)
    {
        Vector<Obstacle> obstacles = field.getObstacles();
        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.startP.y == obstacle.endP.y && obstacle.startP.x <= xPos && obstacle.endP.x > xPos && obstacle.startP.y == yPos + 1)
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                yPos++;
            }
        }
    }
    public void moveEast(Field field, int numberOfSteps)
    {
        Vector<Obstacle> obstacles = field.getObstacles();
        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.startP.x == obstacle.endP.x && obstacle.startP.y <= yPos && obstacle.endP.y > yPos && obstacle.startP.x - 1 == xPos)
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                xPos++;
            }
        }
    }
    public void moveSouth(Field field, int numberOfSteps)
    {
        Vector<Obstacle> obstacles = field.getObstacles();
        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.startP.y == obstacle.endP.y && obstacle.startP.x <= xPos && obstacle.endP.x > xPos && obstacle.startP.y == yPos)
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                yPos--;
            }
        }
    }
    public void moveWest(Field field, int numberOfSteps)
    {
        Vector<Obstacle> obstacles = field.getObstacles();
        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.startP.x == obstacle.endP.x && obstacle.startP.y <= yPos && obstacle.endP.y > yPos && obstacle.startP.x == xPos)
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                xPos--;
            }
        }
    }
}

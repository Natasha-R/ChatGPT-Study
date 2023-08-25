package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Field {
    private final UUID uUID;
    private final ArrayList<Obstacle> obstacles;
    private final Vector2D dynamics;

    public Field(int pHeight,int pWidth){
        dynamics = new Vector2D(pWidth,pHeight);
        uUID=UUID.randomUUID();
        obstacles=new ArrayList<>();
    }

    public Field(Vector2D pWidthAndHight){
        dynamics = pWidthAndHight;
        uUID=UUID.randomUUID();
        obstacles=new ArrayList<>();
    }

    public Vector2D getDynamics(){
        return dynamics;
    }

    public void addObstacles(Obstacle pObstacle){
        if(pObstacle!=null){
            obstacles.add(pObstacle);
        }
        else{
            throw new RuntimeException();
        }
    }

    public UUID getUUID(){
        return uUID;
    }

    private Vector2D getRightAddedIntForBlocked(CommandType pCommand, Vector2D pVector){
        if (pCommand.equals(CommandType.NORTH)){
            return new Vector2D(pVector.getX()+1, pVector.getY()+1);
        }
        else if (pCommand.equals(CommandType.SOUTH)){
            return new Vector2D(pVector.getX()+1, pVector.getY());
        }
        else if (pCommand.equals(CommandType.WEST)){
            return new Vector2D(pVector.getX(), pVector.getY()+1);
        }
        else if (pCommand.equals(CommandType.EAST)){
            return new Vector2D(pVector.getX()+1, pVector.getY()+1);
        }
        throw new RuntimeException();
    }

    private Vector2D getRightAddedNumberEasy(CommandType pCommand, Vector2D pVector){
        if (pCommand.equals(CommandType.NORTH)){
            return new Vector2D(pVector.getX(), pVector.getY()+1);
        }
        else if (pCommand.equals(CommandType.SOUTH) || pCommand.equals(CommandType.WEST)){
            return new Vector2D(pVector.getX(), pVector.getY());
        }
        else if (pCommand.equals(CommandType.EAST)){
            return new Vector2D(pVector.getX()+1, pVector.getY());
        }
        throw new RuntimeException();
    }

    public boolean isBlocked(Vector2D pVector, CommandType pCommand, Vector2D pVectorForCheckObstacle){
            for(int i=0;i<obstacles.size();i++){
                if(getIfTheRightObstacleIsThere(pCommand, i)) {
                    for (int j = 0; j < getNumberForLoopOfObstacles(i);j++)
                        if (getIfSoutOrNorth(pCommand,i)){
                            if(obstacles.get(i).getStart().getY().equals(getRightAddedNumberEasy(pCommand,pVector).getY())&&
                            getRightAddedIntForBlocked(pCommand,pVector).getX().equals(obstacles.get(i).getStart().getX()+j+1) &&
                            getRightAddedNumberEasy(pCommand, pVector).getX().equals(obstacles.get(i).getStart().getX()+j)){
                                return true;
                            }
                        }else{
                            if(obstacles.get(i).getStart().getX().equals(getRightAddedNumberEasy(pCommand,pVector).getX())&&
                                    getRightAddedIntForBlocked(pCommand,pVector).getY().equals(obstacles.get(i).getStart().getY()+j+1) &&
                                    getRightAddedNumberEasy(pCommand, pVector).getY().equals(obstacles.get(i).getStart().getY()+j)){
                                return true;
                            }
                        }
                }
            }
        return false;
    }

    private int getNumberForLoopOfObstacles(Integer pPlaceOfObstacle){
        return (obstacles.get(pPlaceOfObstacle).getEnd().getX()-obstacles.get(pPlaceOfObstacle).getStart().getX())+
                (obstacles.get(pPlaceOfObstacle).getEnd().getY()-obstacles.get(pPlaceOfObstacle).getStart().getY());
    }

    private boolean getIfTheRightObstacleIsThere(CommandType pCommandType, Integer pI){
        if (pCommandType.equals(CommandType.SOUTH) || pCommandType.equals(CommandType.NORTH)) {
            return obstacles.get(pI).getStart().getY().equals(obstacles.get(pI).getEnd().getY());
        }
        return obstacles.get(pI).getStart().getX().equals(obstacles.get(pI).getEnd().getX());
    }

    private boolean getIfSoutOrNorth(CommandType pCommandType, Integer pI){
            return obstacles.get(pI).getStart().getY().equals(obstacles.get(pI).getEnd().getY());
    }
}


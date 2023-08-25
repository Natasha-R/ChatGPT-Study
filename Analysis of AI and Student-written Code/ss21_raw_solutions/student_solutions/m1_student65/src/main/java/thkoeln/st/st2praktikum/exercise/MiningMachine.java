package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

public class MiningMachine implements Transportable,Initializable, Moveable{
    private Vector2d position = new Vector2d(0,0);
    private String name;
    private UUID isLocatedOnField;


    public int getxPos() {
        return position.getX();
    }

    public void setxPos(int xPos) {
        this.position.setX(xPos);
    }

    public int getyPos() {
        return position.getY();
    }
    public void moveToDir(Vector2d delta) {
        position.setX(position.getX() + delta.getX());
        position.setY(position.getY() + delta.getY());
    }
    public void setyPos(int yPos) {
        this.position.setY(yPos);
    }
    public void setPos(Vector2d pos) {
        this.position = pos;
    }
    public Vector2d getPos() {
        return position;
    }
    public String getStringPos() {
        return new String("("+position.getX()+","+position.getY()+")");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getIsLocatedOnField() {
        return isLocatedOnField;
    }

    public void setLocatedOnField(UUID isLocatedOnField) {
        this.isLocatedOnField = isLocatedOnField;
    }

    public MiningMachine(String name)
    {
        this.name = name;
    }

    @Override
    public boolean transport(HashMap<UUID,Connection> connections, String transportCommand) {
        int indexOfUUID = transportCommand.lastIndexOf(',');
        UUID fieldUUID = UUID.fromString(transportCommand.substring(indexOfUUID+1,indexOfUUID+37));
        for(Map.Entry<UUID, Connection> entry : connections.entrySet()) {
            Connection connection = entry.getValue();
            if(connection.getDestinationFieldId().equals(fieldUUID))
            {
                if (isLocatedOnField != null && isLocatedOnField.equals(connection.getSourceFieldId()) && connection.getSourceCoordinate().equals(getStringPos())) {
                    setLocatedOnField(connection.getDestinationFieldId());
                    setPos(String2Vector2d(connection.getDestinationCoordinate()));
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean initialize(HashMap<UUID,MiningMachine> miningMachines, String initialzeCommand) {
        int indexOfUUID = initialzeCommand.lastIndexOf(',');
        UUID fieldID = UUID.fromString(initialzeCommand.substring(indexOfUUID+1,indexOfUUID+37));
        if(cellIsEmpty(fieldID,new Vector2d(0,0),miningMachines))
        {
            //miningMachines.get(miningMachineId).setLocatedOnField(fieldID);
            setLocatedOnField(fieldID);
            return true;
        }
        return false;
    }
    @Override
    public boolean move(Field field, String moveCommand) {
        int indexOfNumber = moveCommand.lastIndexOf(',');
        int numberOfSteps = Integer.valueOf(String.valueOf(moveCommand.charAt(indexOfNumber + 1)));
        if (numberOfSteps > 0) {
            if (moveCommand.contains("no")) {
                moveNorth(numberOfSteps, field);
            }
            if (moveCommand.contains("ea")) {
                moveEast(numberOfSteps, field);
            }
            if (moveCommand.contains("so")) {
                moveSouth(numberOfSteps, field);
            }
            if (moveCommand.contains("we")) {
                moveWest(numberOfSteps, field);
            }
        }
        return true;
    }
    @Override
    public void moveNorth(int numberOfSteps, Field field)
    {
        Vector<Obstacle> obstacles = field.getObstacles();

        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            Vector2d mmPos = getPos();
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.getStartP().getY() == obstacle.getEndP().getY() && obstacle.getStartP().getX() <= mmPos.getX() && obstacle.getEndP().getX() > mmPos.getX() && obstacle.getStartP().getY() == mmPos.getY() + 1)
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                moveToDir(new Vector2d(0,1));
            }
        }
    }
    @Override
    public void moveEast(int numberOfSteps, Field field)
    {
        Vector<Obstacle> obstacles = field.getObstacles();

        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            Vector2d mmPos = getPos();
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.getStartP().getX() == obstacle.getEndP().getX() && obstacle.getStartP().getY() <= mmPos.getY() && obstacle.getEndP().getY() > mmPos.getY() && obstacle.getStartP().getX() - 1 == mmPos.getX())
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
               moveToDir(new Vector2d(1,0));
            }
        }
    }
    @Override
    public void moveSouth(int numberOfSteps, Field field)
    {
        Vector<Obstacle> obstacles = field.getObstacles();

        for(int i=0;i<numberOfSteps;i++)
        {
            boolean moveBlocked = false;
            Vector2d mmPos = getPos();
            for(int obstIndex=0;obstIndex<obstacles.size();obstIndex++)
            {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if(obstacle.getStartP().getY() == obstacle.getEndP().getY() && obstacle.getStartP().getX() <= mmPos.getX() && obstacle.getEndP().getX() > mmPos.getX() && obstacle.getStartP().getY() == mmPos.getY())
                {
                    moveBlocked = true;
                }
            }
            if(!moveBlocked)
            {
                moveToDir(new Vector2d(0,-1));
            }
        }
    }
    @Override
    public void moveWest(int numberOfSteps, Field field) {

        Vector<Obstacle> obstacles = field.getObstacles();

        for (int i = 0; i < numberOfSteps; i++) {
            boolean moveBlocked = false;
            Vector2d mmPos = getPos();
            for (int obstIndex = 0; obstIndex < obstacles.size(); obstIndex++) {
                Obstacle obstacle = obstacles.elementAt(obstIndex);
                if (obstacle.getStartP().getX() == obstacle.getEndP().getX() && obstacle.getStartP().getY() <= mmPos.getY() && obstacle.getEndP().getY() > mmPos.getY() && obstacle.getStartP().getX() == mmPos.getX()) {
                    moveBlocked = true;
                }
            }
            if (!moveBlocked) {
                moveToDir(new Vector2d(-1, 0));
            }
        }
    }
    private Vector2d String2Vector2d(String string)
    {
        Vector2d vector = new Vector2d(0,0);
        int indexOfNumber = string.indexOf("(");
        vector.setX(getNumberAt(string, indexOfNumber + 1));
        indexOfNumber = string.indexOf(",");
        vector.setY(getNumberAt(string, indexOfNumber + 1));
        return vector;
    }
    private int getNumberAt(String String, int index)
    {
        char curChar = String.charAt(index);
        int size = 0;
        while(curChar != ',' && curChar != ')')
        {
            size++;
            curChar = String.charAt(index+size);
        }
        return Integer.valueOf(String.substring(index,index+size));
    }
    private boolean cellIsEmpty(UUID fieldID, Vector2d pos, HashMap<UUID,MiningMachine> miningMachines)
    {
        for(Map.Entry<UUID, MiningMachine> entry : miningMachines.entrySet()) {
            MiningMachine value = entry.getValue();
            if(value.getIsLocatedOnField() != null && value.getIsLocatedOnField().equals(fieldID) && value.getxPos() == pos.getX() && value.getyPos() == pos.getY())
            {
                return false;
            }
        }
        return true;
    }
}

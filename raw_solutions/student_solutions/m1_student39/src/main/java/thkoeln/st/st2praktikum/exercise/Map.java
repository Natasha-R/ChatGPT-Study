package thkoeln.st.st2praktikum.exercise;


import java.util.ArrayList;
import java.util.UUID;

abstract class Map{
    protected UUID identifier;
    protected ArrayList<Blocking> listOfBlockingObjects = new ArrayList<>();
    protected ArrayList<Connection> listOfConnections = new ArrayList<>();

    protected Integer height;
    protected Integer width;

    public abstract Connection getConnection(Vector2D sourcePosition);

    public abstract Integer checkNorthDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);
    public abstract Integer checkWestDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);
    public abstract Integer checkSouthDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);
    public abstract Integer checkEastDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);

    public abstract boolean isNotBlocked(Integer xPosition, Integer yPosition);

    public abstract void removeBlocking(Blocking blockingObject);
    public abstract boolean addBlockingObject(Blocking blockingObject, Vector2D position);

    public boolean addConnection(Connection connection, Map destinationMap){
        if(height > connection.getSourcePosition().getY()  && width > connection.getSourcePosition().getX()) {
            if (destinationMap.getHeight() > connection.getDestinationPosition().getY() && destinationMap.getWidth() > connection.getDestinationPosition().getX()) {
                listOfConnections.add(connection);
                return true;
            }
        }
        return false;
    }

    public Integer getHeight(){
        return height;
    }
    public Integer getWidth(){
        return width;
    }
    public UUID getUUID(){
        return identifier;
    }

}

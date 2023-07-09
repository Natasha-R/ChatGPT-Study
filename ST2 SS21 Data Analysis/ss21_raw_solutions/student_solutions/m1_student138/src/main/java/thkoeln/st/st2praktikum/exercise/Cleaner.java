package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Cleaner implements RobotActions {

    private String name;
    public String getName() {
        return name;
    }

    private UUID uuid;
    public UUID getUuid() {
        return uuid;
    }

    private Space currentSpace;
    public Space getCurrentSpace() { return currentSpace; }

    private Integer x;
    public Integer getX() {
        return x;
    }

    private Integer y;
    public Integer getY() {
        return y;
    }


    public Cleaner(String _name) {
        name = _name;
        uuid = UUID.randomUUID();
    }


    @Override
    public boolean move(String direction, Integer moveFields) {
        if (currentSpace == null)
        {
            throw new NullPointerException("The cleaner is currently not on a space");
        }

        for(int i = 0; i < moveFields; i++)
        {
            currentSpace.getField(x, y).setOccupiedByCleaner(false);
            if (direction.equals("no") && currentSpace.fieldExists(x,y+1)  && currentSpace.getField(x, y).canMoveOut("no") && currentSpace.getField(x, y+1).canMoveIn("no"))
            {
                y += 1;
            }
            if (direction.equals("ea") && currentSpace.fieldExists(x+1, y) && currentSpace.getField(x, y).canMoveOut("ea") && currentSpace.getField(x+1, y).canMoveIn("ea"))
            {
                x += 1;
            }
            if (direction.equals("so") && currentSpace.fieldExists(x,y-1) && currentSpace.getField(x, y).canMoveOut("so") && currentSpace.getField(x, y-1).canMoveIn("so"))
            {
                y -= 1;
            }
            if (direction.equals("we") && currentSpace.fieldExists(x-1, y) && currentSpace.getField(x, y).canMoveOut("we") && currentSpace.getField(x-1, y).canMoveIn("we"))
            {
                x -= 1;
            }
            currentSpace.getField(x, y).setOccupiedByCleaner(true);
        }

        return true;
    }

    @Override
    public boolean transport(Space destinationSpace, HashMap<UUID, Connection> connectionHashMap) {
        if (currentSpace.getField(x, y).isOccupiedByCleaner())
        {
            Connection connection = currentSpace.getField(x, y).getConnection();
            if (connection != null && connection.getSourceSpace() == currentSpace && connection.getDestinationSpace() == destinationSpace
                    && !destinationSpace.getField(connection.getDestinationCoordinateX(), connection.getDestinationCoordinateY()).isOccupiedByCleaner())
            {
                currentSpace.getField(x, y).setOccupiedByCleaner(false);
                destinationSpace.getField(connection.getDestinationCoordinateX(), connection.getDestinationCoordinateY()).setOccupiedByCleaner(true);
                this.currentSpace = destinationSpace;
                x = connection.getDestinationCoordinateX();
                y = connection.getDestinationCoordinateY();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean enterSpace(Space destinationSpace) {
        if (currentSpace == null && !destinationSpace.getField(0, 0).isOccupiedByCleaner())
        {
            destinationSpace.getField(0, 0).setOccupiedByCleaner(true);
            x = 0;
            y = 0;
            currentSpace = destinationSpace;
            return true;
        }
        else
        {
            return false;
        }
    }
}

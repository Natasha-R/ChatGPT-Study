package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Robot implements IRobotActions
{
    private UUID robotId;
    private UUID currentRoom;
    private String name;
    private Position position;

    public Robot(String name)
    {
        this.robotId = UUID.randomUUID();
        this.name = name;
    }

    public UUID getRobotId()
    {
        return robotId;
    }

    public void setRobotId(UUID robotId)
    {
        this.robotId = robotId;
    }

    public UUID getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(UUID currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Position getPosition()
    {
        return this.position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    @Override
    public String getCurrentPositionAsString()
    {
        return "(" + position.getX() + "," + position.getY() + ")";
    }

    @Override
    public boolean executeCommand(String commandString, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections)
    {
        String[] commandParameters = extractCommandParameters(commandString);

        String action = commandParameters[0];
        String parameter = commandParameters[1];

        switch (action)
        {
            case "no":
            case "we":
            case "ea":
            case "so":
            {
                return move(commandString, rooms);
            }
            case "tr":
            {
                return transport(rooms, UUID.fromString(parameter));
            }
            case "en":
            {
                return spawnAtPosition(new Position(0, 0), UUID.fromString(parameter), rooms);
            }
        }

        return false;
    }

    @Override
    public boolean move(String moveCommand, HashMap<UUID, Room> rooms)
    {
        String[] commandParams = extractCommandParameters(moveCommand);

        String direction = commandParams[0];
        int amount = Integer.parseInt(commandParams[1]);

        for (int i = 0; i < amount; i++)
        {
            switch (direction)
            {
                case "no":
                {
                    moveUp(rooms);
                    break;
                }
                case "so":
                {
                    moveDown(rooms);
                    break;
                }
                case "ea":
                {
                    moveRight(rooms);
                    break;
                }
                case "we":
                {
                    moveLeft(rooms);
                    break;
                }
                default: {}
            }
        }
        return true;
    }

    @Override
    public boolean spawnAtPosition(Position position, UUID roomToSpawnIn, HashMap<UUID, Room> rooms)
    {
        if (!rooms.get(roomToSpawnIn).getRoomGrid()[position.getX()][position.getY()].fieldOccupied)
        {
            setCurrentRoom(roomToSpawnIn);
            setPosition(position);
            occupyFieldAtPosition(position, rooms);
            return true;
        }
        return false;
    }

    @Override
    public boolean collidesAtPosition(Position position, String moveDirection, HashMap<UUID, Room> rooms)
    {
        if (rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()] instanceof Barrier)
        {
            switch(moveDirection)
            {
                case "no":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasNorthernBarrier();
                }
                case "so":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasSouthernBarrier();
                }
                case "we":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasWesternBarrier();
                }
                case "ea":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasEasternBarrier();
                }
                default: {}
            }
        }

        return false;
    }

    @Override
    public boolean transport(HashMap<UUID, Room> rooms, UUID targetRoom)
    {
        if (rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()] instanceof Connection)
        {
            UUID destination = ((Connection) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).destinationId;
            String destinationCoordsString = ((Connection) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).destinationCoordinate;
            String[] destinationCoords = extractCoordinates(destinationCoordsString);
            rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied = false;
            return spawnAtPosition(new Position(Integer.parseInt(destinationCoords[0]), Integer.parseInt(destinationCoords[1])), destination, rooms);
        }
        return false;
    }

    @Override
    public boolean collidesWithRobotAtPosition(Position position, HashMap<UUID, Room> rooms)
    {
        return rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied;
    }

    private boolean moveUp(HashMap<UUID, Room> rooms)
    {
        if (position != null && position.getY() + 1 <= rooms.get(currentRoom).getHeight() - 1)
        {
            if (!collidesAtPosition(new Position(position.getX(), position.getY() + 1), "so", rooms)
                    && !collidesWithRobotAtPosition(new Position(position.getX(), position.getY() + 1), rooms))
            {
                Position lastPosition = new Position(position.getX(), position.getY());
                position.setY(position.getY() + 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private boolean moveDown(HashMap<UUID, Room> rooms)
    {
        if (position != null && !(position.getY() - 1 < 0))
        {
            if (!collidesAtPosition(new Position(position.getX(), position.getY() - 1), "so", rooms)
                    && !collidesWithRobotAtPosition(new Position(position.getX(), position.getY() - 1), rooms))
            {
                Position lastPosition = new Position(position.getX(), position.getY());
                position.setY(position.getY() - 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private boolean moveRight(HashMap<UUID, Room> rooms)
    {
        if (position != null && position.getX() + 1 <= rooms.get(currentRoom).getWidth() - 1)
        {
            if (!collidesAtPosition(new Position(position.getX() + 1, position.getY()), "we", rooms)
                    && !collidesWithRobotAtPosition(new Position(position.getX() + 1, position.getY()), rooms))
            {
                Position lastPosition = new Position(position.getX(), position.getY());
                position.setX(position.getX() + 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private boolean moveLeft(HashMap<UUID, Room> rooms)
    {
        if (position != null && !(position.getX() - 1 < 0))
        {
            if (!collidesAtPosition(new Position(position.getX() - 1, position.getY()), "we", rooms)
                    && !collidesWithRobotAtPosition(new Position(position.getX() - 1, position.getY()), rooms))
            {
                Position lastPosition = new Position(position.getX(), position.getY());
                position.setX(position.getX() - 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    public String[] extractCommandParameters(String commandString)
    {
        String[] result = new String[2];

        String command = commandString.split(",")[0].replace("[", "");
        String amount = commandString.split(",")[1].replace("]", "");

        result[0] = command;
        result[1] = amount;

        return result;
    }

    public String[] extractCoordinates(String coordinateString)
    {
        String[] result = new String[2];

        result[0] = coordinateString.split(",")[0].replace("(", "");
        result[1] = coordinateString.split(",")[1].replace(")", "");

        return result;
    }

    private void occupyFieldAtPosition(Position position, HashMap<UUID, Room> rooms)
    {
        rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied = true;
    }

    private void releaseFieldAtPosition(Position position, HashMap<UUID, Room> rooms)
    {
        rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied = false;
    }
}

package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Robot implements IRobot{

    private String name;
    private UUID currentRoom;
    private Position currentPosition;

    public Robot(String name)
    {
        this.name = name;
        this.currentPosition = new Position(-1, -1);
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

    public Position getPosition()
    {
        return currentPosition;
    }

    private String[] extractCommandAndParameterFromCommandString(String commandString)
    {
        String[] commandAndParameter = new String[2];
        commandAndParameter [0] = commandString.substring(1, 3);
        commandAndParameter [1] = commandString.substring(4, commandString.length() - 1);

        return commandAndParameter;
    }

    @Override
    public boolean executeCommand(String commandString, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections)
    {
        String[] commandAndParameter = extractCommandAndParameterFromCommandString(commandString);

        if((commandAndParameter[0].equals("no") || commandAndParameter[0].equals("ea") || commandAndParameter[0].equals("so") || commandAndParameter[0].equals("we")) && currentRoom != null)
        {
            return move(rooms.get(currentRoom), commandAndParameter[0], commandAndParameter[1]);
        }
        else if((commandAndParameter[0].equals("tr")) && currentRoom != null)
        {
            return transport(commandAndParameter[1], rooms, connections);
        }
        else if(commandAndParameter[0].equals("en"))
        {
            return enlist(rooms.get(UUID.fromString(commandAndParameter[1])), commandAndParameter[1]);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    private boolean moveNorth(Room room)
    {
        int currentRoomHeight;
        currentRoomHeight = room.getHeight();
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentPosition.getY() + 1 <= currentRoomHeight - 1
                && !currentRoomGrid[currentPosition.getX()][currentPosition.getY() + 1].getIsSouthWall()
                && !currentRoomGrid[currentPosition.getX()][currentPosition.getY() + 1].getIsOccupied())
        {
                currentPosition.setY(currentPosition.getY() + 1);
                room.setGridCellToOccupied(currentPosition.getX(), currentPosition.getY());
                room.setGridCellToUnoccupied(currentPosition.getX(), currentPosition.getY() - 1);
                return true;
        }
        return false;
    }

    private boolean moveEast(Room room)
    {
        int currentRoomWidth;
        currentRoomWidth = room.getWidth();
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentPosition.getX() + 1 <= currentRoomWidth - 1
                && !currentRoomGrid[currentPosition.getX() + 1][currentPosition.getY()].getIsWestWall()
                && !currentRoomGrid[currentPosition.getX() + 1][currentPosition.getY()].getIsOccupied())
        {
                currentPosition.setX(currentPosition.getX() + 1);
                room.setGridCellToOccupied(currentPosition.getX(), currentPosition.getY());
                room.setGridCellToUnoccupied(currentPosition.getX() - 1, currentPosition.getY());
                return true;
        }
        return false;
    }

    private boolean moveSouth(Room room)
    {
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentPosition.getY() - 1 >= 0
                && !currentRoomGrid[currentPosition.getX()][currentPosition.getY()].getIsSouthWall()
                && !currentRoomGrid[currentPosition.getX()][currentPosition.getY() - 1].getIsOccupied())
        {
                currentPosition.setY(currentPosition.getY() - 1);
                room.setGridCellToOccupied(currentPosition.getX(), currentPosition.getY());
                room.setGridCellToUnoccupied(currentPosition.getX(), currentPosition.getY() + 1);
                return true;
        }
        return false;
    }

    private boolean moveWest(Room room)
    {
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentPosition.getX() - 1 >= 0
                && !currentRoomGrid[currentPosition.getX()][currentPosition.getY()].getIsWestWall()
                && !currentRoomGrid[currentPosition.getX() - 1][currentPosition.getY()].getIsOccupied())
        {
                currentPosition.setX(currentPosition.getX() - 1);
                room.setGridCellToOccupied(currentPosition.getX(), currentPosition.getY());
                room.setGridCellToUnoccupied(currentPosition.getX() + 1, currentPosition.getY());
                return true;
        }
        return false;
    }

    @Override
    public boolean move(Room room, String command, String parameter)
    {
        int steps = Integer.parseInt(parameter);
        boolean result = false;

        for(int i = 0; i < steps; i++)
        {
            switch ((command))
            {
                case "no":
                {
                    result = moveNorth(room);
                }
                break;
                case "ea":
                {
                    result = moveEast(room);
                }
                break;
                case "so":
                {
                    result = moveSouth(room);
                }
                break;
                case "we":
                {
                    result = moveWest(room);
                }
                break;
            }
            if(!result)
            {
                break;
            }
        }
        return true;
    }

    @Override
    public boolean transport(String parameter, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections)
    {
        UUID destinationRoomId = UUID.fromString(parameter);
        int currentPositionX = currentPosition.getX();
        int currentPositionY = currentPosition.getY();

        if(rooms.get(currentRoom).getGrid()[currentPosition.getX()][currentPosition.getY()].getIsConnection())
        {
            UUID connectionId = rooms.get(currentRoom).getGrid()[currentPosition.getX()][currentPosition.getY()].getConnectionID();
            Connection connection = connections.get(connectionId);

            if(connection.getDestinationRoomId().equals(destinationRoomId)
                    && !rooms.get(destinationRoomId).getGrid()[connection.getDestinationCoordinate().getX()][connection.getDestinationCoordinate().getY()].getIsOccupied())
            {
                this.setCurrentRoom(destinationRoomId);
                this.currentPosition.setX(connection.getDestinationCoordinate().getX());
                this.currentPosition.setY(connection.getDestinationCoordinate().getY());
                rooms.get(destinationRoomId).setGridCellToOccupied(connection.getDestinationCoordinate().getX(), connection.getDestinationCoordinate().getY());
                rooms.get(connection.getSourceRoomId()).setGridCellToUnoccupied(currentPositionX, currentPositionY);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean enlist(Room room, String destinationRoomId)
    {
        if(!room.getGrid()[0][0].getIsOccupied())
        {
            this.setCurrentRoom(UUID.fromString(destinationRoomId));
            this.currentPosition.setX(0);
            this.currentPosition.setY(0);
            room.getGrid()[0][0].setIsOccupied(true);
            return true;
        }
        else
        {
            return false;
        }
    }
}

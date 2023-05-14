package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Entity
public class TidyUpRobot extends AbstractRobot implements Transportable
{
    private String name;


    public TidyUpRobot(String name)
    {
        super();
        this.name = name;
    }

    public UUID getRoomId()
    {
        return jobRoom.getRoomID();
    }

    @Override
    public UUID identify()
    {
        return robotID;
    }


    public boolean moveNorth(Integer steps)
    {
        int commandSteps = steps.intValue();
        Wall wall = isWallBetweenNorth(commandSteps);

        if (wall!=null)
        {
            this.coordinate.setCoordinateY(wall.getBeginPositionY()-1);

        }else if (checkBoundaryNorth(commandSteps))
        {
            this.coordinate.setCoordinateY(jobRoom.getHeight());

        }else
        {
            this.coordinate.setCoordinateY(coordinate.getCoordinateY()+commandSteps);
        }
        return true;
    }


    public boolean moveEast(Integer steps)
    {
        int commandSteps = steps.intValue();
        Wall wall = isWallBetweenEast(commandSteps);

        if (wall!=null)
        {
            this.coordinate.setCoordinateX(wall.getBeginPositionX()-1);
        }else if (checkBoundaryEast(commandSteps))
        {
            this.coordinate.setCoordinateX(jobRoom.getWidth());

        }else
        {
            this.coordinate.setCoordinateX(coordinate.getCoordinateX()+commandSteps);
        }
        return true;
    }


    public boolean moveSouth(Integer steps)
    {
        int commandSteps = steps.intValue();
        Wall wall = isWallBetweenSouth(commandSteps);

        if (wall!=null)
        {
            this.coordinate.setCoordinateY(wall.getBeginPositionY()+1);
        }else if (checkBoundarySouth(commandSteps))
        {
            this.coordinate.setCoordinateY(0);
        }else
        {
            this.coordinate.setCoordinateY(coordinate.getCoordinateY()-commandSteps);
        }
        return true;
    }


    public boolean moveWest(Integer steps)
    {
        int commandSteps = steps.intValue();
        Wall wall = isWallBetweenWest(commandSteps);

        if (wall!=null)
        {
            if (wall.getBeginPositionX()==0)
                this.coordinate.setCoordinateX(0);
            else
                this.coordinate.setCoordinateX(wall.getBeginPositionX()+1);
        }else if (checkBoundaryWest(commandSteps))
        {
            this.coordinate.setCoordinateX(0);
        }else
        {
            this.coordinate.setCoordinateX(coordinate.getCoordinateX()-commandSteps);
        }
        return true;
    }

    @Override
    public boolean moveInDirection(TaskType key, Integer steps, Connectable connection, Roomable room)
    {
        switch (key)
        {
            case ENTER:
                return placeMovable(room);
            case TRANSPORT:
                return transportToRoom(room,(Connection) connection);
            case NORTH:
                return moveNorth(steps);
            case EAST:
                return moveEast(steps);
            case SOUTH:
                return moveSouth(steps);
            case WEST:
                return moveWest(steps);
        }
        return false;
    }


    private boolean placeMovable(Roomable room)
    {
        if (room instanceof Room)
        {
            Room workingRoom= (Room) room;

            if (isRoomEmpty(workingRoom))
            {
                this.jobRoom = workingRoom;
                this.coordinate = new Coordinate("(0,0)");
                return true;
            }

            if (!isEntryBlocked(workingRoom))
            {
                this.jobRoom = workingRoom;

                this.coordinate = new Coordinate("(0,0)");
                return true;
            }
        }
        return false;
    }

    @Override
    public Coordinate showActualPosition()
    {
        return coordinate;
    }

    @Override
    public Positionable getActualPosition()
    {
        return (Positionable) coordinate;
    }

    @Override
    public UUID showTransportableRoomID() {
        return jobRoom.identify();
    }


    private boolean transportToRoom(Roomable destinationRoom, Connection usedConnection)
    {
        if (destinationRoom instanceof Room)
        {
            Room room = (Room) destinationRoom;

            if (isTransportable(usedConnection))
            {
                this.coordinate = (Coordinate) usedConnection.showDestinationCoordinate();
                this.jobRoom = room;
            }
        }
        return false;
    }

    @Override
    public Roomable showRoom() {
        return jobRoom;
    }

    private boolean isRoomEmpty(Room room)
    {
        return room.getCleaningRobots().isEmpty();
    }

    private boolean isEntryBlocked(Room workingRoom)
    {
        for (Transportable cleaningRobots: workingRoom.getCleaningRobots())
        {
            if (cleaningRobots.getActualPosition() != null)
            {
                if (cleaningRobots.getActualPosition().getCoordinateX() == 0 && cleaningRobots.getActualPosition().getCoordinateY() == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTransportable(Connection usedConnection)
    {
        Positionable connectionPoint = usedConnection.showStartConnectionPoint();

        return coordinate.getX() == connectionPoint.getCoordinateX() &&
                coordinate.getY() == connectionPoint.getCoordinateY();

    }


    private boolean checkBoundaryNorth(int steps)
    {
        if (coordinate.getCoordinateY()+steps >= jobRoom.getHeight())
        {
            return true;
        }

        return false;
    }


    private boolean checkBoundaryEast(int steps)
    {
        if (coordinate.getCoordinateX()+steps >= jobRoom.getHeight())
        {
            return true;
        }

        return false;
    }

    private boolean checkBoundarySouth(int steps)
    {
        if (coordinate.getCoordinateY()-steps <= 0)
        {
            return true;
        }

        return false;
    }

    private boolean checkBoundaryWest(int steps)
    {
        if (coordinate.getCoordinateX()-steps <= 0)
        {
            return true;
        }

        return false;
    }


    private Wall isWallBetweenNorth(int steps)
    {
        for (FieldBlockable wall : jobRoom.getWalls())
        {
            Wall placedWall = (Wall) wall;

            if (placedWall.getBeginPositionX()<= coordinate.getCoordinateX() && placedWall.getBeginPositionY() >= coordinate.getCoordinateY())
            {
                if (placedWall.getEndPositionX() > coordinate.getCoordinateX())
                {
                    if (placedWall.getEndPositionY() < coordinate.getCoordinateY()+steps)
                        return placedWall;
                }
            }
        }
        return null;
    }

    private  Wall isWallBetweenEast(int steps)
    {
        for (FieldBlockable wall : jobRoom.getWalls())
        {
            Wall placedWall = (Wall) wall;

            if (placedWall.getBeginPositionY()<= coordinate.getCoordinateY() && placedWall.getBeginPositionX() <= coordinate.getCoordinateX())
            {
                if (placedWall.getEndPositionY() >= coordinate.getCoordinateY())
                {
                    if (placedWall.getBeginPositionX() < coordinate.getCoordinateX()+steps)
                        return placedWall;
                }
            }
        }
        return null;
    }

    private  Wall isWallBetweenWest(int steps)
    {
        for (FieldBlockable wall : jobRoom.getWalls())
        {
            Wall placedWall = (Wall) wall;

            if (placedWall.getBeginPositionX()<= coordinate.getCoordinateX() && placedWall.getBeginPositionY() <= coordinate.getCoordinateY())
            {
                if (placedWall.getEndPositionX() >= coordinate.getCoordinateX())
                {
                    if (placedWall.getBeginPositionX() > coordinate.getCoordinateX()-steps)
                        return placedWall;
                }
            }
        }
        return null;
    }

    private  Wall isWallBetweenSouth(int steps)
    {
        for (FieldBlockable wall : jobRoom.getWalls())
        {
            Wall placedWall = (Wall) wall;

            if (placedWall.getBeginPositionX()<= coordinate.getCoordinateX() && placedWall.getBeginPositionY() <= coordinate.getCoordinateY())
            {
                if (placedWall.getEndPositionX() >= coordinate.getCoordinateX())
                {
                    if (placedWall.getBeginPositionY() > coordinate.getCoordinateY()-steps)
                        return placedWall;
                }
            }
        }
        return null;
    }

}

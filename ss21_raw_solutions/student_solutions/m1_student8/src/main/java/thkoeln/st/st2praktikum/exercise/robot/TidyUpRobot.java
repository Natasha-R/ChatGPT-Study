package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.position.Position;
import thkoeln.st.st2praktikum.exercise.position.Positionable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Roomable;
import thkoeln.st.st2praktikum.exercise.wall.FieldBlockable;
import thkoeln.st.st2praktikum.exercise.wall.Wall;
import java.util.UUID;

public class TidyUpRobot extends AbstractRobot implements Transportable
{
    private String name;


    public TidyUpRobot(String name)
    {
        super();
        this.name = name;
    }

    @Override
    public UUID identify()
    {
        return robotID;
    }

    @Override
    public void moveNorth(String steps)
    {
        int commandSteps = Integer.parseInt(steps);
        Wall wall = isWallBetweenNorth(commandSteps);

        if (wall!=null)
        {
            this.position.setCoordinateY(wall.getBeginPositionY()-1);

        }else if (checkBoundaryNorth(commandSteps))
        {
            this.position.setCoordinateY(jobRoom.getHeight());

        }else
        {
            this.position.setCoordinateY(position.getCoordinateY()+commandSteps);
        }
    }

    @Override
    public void moveEast(String steps)
    {
        int commandSteps = Integer.parseInt(steps);
        Wall wall = isWallBetweenEast(commandSteps);

        if (wall!=null)
        {
            this.position.setCoordinateX(wall.getBeginPositionX()-1);
        }else if (checkBoundaryEast(commandSteps))
        {
            this.position.setCoordinateX(jobRoom.getWidth());

        }else
        {
            this.position.setCoordinateX(position.getCoordinateX()+commandSteps);
        }

    }

    @Override
    public void moveSouth(String steps)
    {
        int commandSteps = Integer.parseInt(steps);
        Wall wall = isWallBetweenSouth(commandSteps);

        if (wall!=null)
        {
            this.position.setCoordinateY(wall.getBeginPositionY()+1);
        }else if (checkBoundarySouth(commandSteps))
        {
            this.position.setCoordinateY(0);
        }else
        {
            this.position.setCoordinateY(position.getCoordinateY()-commandSteps);
        }

    }

    @Override
    public void moveWest(String steps)
    {
        int commandSteps = Integer.parseInt(steps);
        Wall wall = isWallBetweenWest(commandSteps);

        if (wall!=null)
        {
            this.position.setCoordinateX(wall.getBeginPositionX()+1);
        }else if (checkBoundaryWest(commandSteps))
        {
            this.position.setCoordinateX(0);
        }else
        {
            this.position.setCoordinateX(position.getCoordinateX()-commandSteps);
        }
    }

    @Override
    public boolean placeMovable(Roomable room)
    {
        if (room instanceof Room)
        {
            Room workingRoom= (Room) room;

            if (isRoomEmpty(workingRoom))
            {
                this.jobRoom = workingRoom;

                this.position = new Position("(0,0)");
                return true;
            }

            if (!isEntryBlocked(workingRoom))
            {
                this.jobRoom = workingRoom;

                this.position = new Position("(0,0)");
                return true;
            }
        }
        return false;
    }

    @Override
    public String showActualPosition()
    {
        return "("+position.getCoordinateX()+","+position.getCoordinateY()+")";
    }

    @Override
    public Positionable getActualPosition()
    {
        return position;
    }

    @Override
    public UUID showTransportableRoomID() {
        return jobRoom.identify();
    }

    @Override
    public boolean transportToRoom(Roomable destinationRoom, Connectable usedConnection)
    {
        if (destinationRoom instanceof Room)
        {
            Room room = (Room) destinationRoom;

            if (isTransportable(usedConnection))
            {
                this.position = (Position) usedConnection.showDestinationCoordinate();
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
        for (Transportable cleaningRobots: workingRoom.getCleaningRobots().values())
        {
            if (cleaningRobots.getActualPosition().getCoordinateX() == 0 && cleaningRobots.getActualPosition().getCoordinateY()==0)
            {
                return true;
            }
        }
        return false;
    }

    private boolean isTransportable(Connectable usedConnection)
    {
        Positionable connectionPoint = usedConnection.showStartConnectionPoint();

        return position.getCoordinateX() == connectionPoint.getCoordinateX() &&
               position.getCoordinateY() == connectionPoint.getCoordinateY();

    }


    private boolean checkBoundaryNorth(int steps)
    {
        if (position.getCoordinateY()+steps >= jobRoom.getHeight())
        {
            return true;
        }

        return false;
    }


    private boolean checkBoundaryEast(int steps)
    {
        if (position.getCoordinateX()+steps >= jobRoom.getHeight())
        {
            return true;
        }

        return false;
    }

    private boolean checkBoundarySouth(int steps)
    {
        if (position.getCoordinateY()-steps <= 0)
        {
            return true;
        }

        return false;
    }

    private boolean checkBoundaryWest(int steps)
    {
        if (position.getCoordinateX()-steps <= 0)
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

                if (placedWall.getBeginPositionX()<= position.getCoordinateX() && placedWall.getBeginPositionY() >= position.getCoordinateY())
                {
                    if (placedWall.getEndPositionX() > position.getCoordinateX())
                    {
                        if (placedWall.getEndPositionY() < position.getCoordinateY()+steps)
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

            if (placedWall.getBeginPositionY()<= position.getCoordinateY() && placedWall.getBeginPositionX() <= position.getCoordinateX())
            {
                if (placedWall.getEndPositionY() >= position.getCoordinateY())
                {
                    if (placedWall.getBeginPositionX() < position.getCoordinateX()+steps)
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

            if (placedWall.getBeginPositionX()<= position.getCoordinateX() && placedWall.getBeginPositionY() <= position.getCoordinateY())
            {
                if (placedWall.getEndPositionX() >= position.getCoordinateX())
                {
                    if (placedWall.getBeginPositionX() > position.getCoordinateX()-steps)
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

            if (placedWall.getBeginPositionX()<= position.getCoordinateX() && placedWall.getBeginPositionY() <= position.getCoordinateY())
            {
                if (placedWall.getEndPositionX() >= position.getCoordinateX())
                {
                    if (placedWall.getBeginPositionY() > position.getCoordinateY()-steps)
                        return placedWall;
                }
            }
        }
        return null;
    }



}

package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.*;
import java.util.List;

public class Room
{
    private final List<Wall> _wallList = new ArrayList();
    private final Map<UUID, Portal> _portalList = new HashMap<>();
    private final Map<UUID, Robot> _robotList = new HashMap<>();

    public final UUID ID;
    public final int Width;
    public final int Height;

    public Room(final int width, final int height)
    {
        ID = UUID.randomUUID();
        Width = width;
        Height = height;
    }

    public void AddWall(final int xA, final int yA, final int xB, final int yB)
    {
        final Position a = new Position(xA, yA);
        final Position b = new Position(xB, yB);

        AddWall(a, b);
    }

    public void AddWall(final Position a, final Position b)
    {
        final Wall wall = new Wall(a, b);

        AddWall(wall);
    }

    public void AddWall(final Wall wall)
    {
        _wallList.add(wall);
    }

    public void AddPortal(int xFrom, int yFrom, int xTo, int yTo, final UUID pointToRoomID)
    {
        final Position from = new Position(xFrom, yFrom);
        final Position to = new Position(xTo, yTo);

        AddPortal(from, to, pointToRoomID);
    }

    public void AddPortal(final Position location, final Position pointToPosition, final UUID pointToRoomID)
    {
        final Portal portal = new Portal(location, pointToPosition, pointToRoomID);

        AddPortal(portal);
    }

    public void AddPortal(final Portal portal)
    {
        _portalList.put(portal.ToRoomID, portal);
    }

    public void AddRobot(final Robot robot)
    {
        _robotList.put(robot.ID, robot);
    }

    public void RemoveRobot(final UUID robot)
    {
        _robotList.remove(robot);
    }

    public Robot GetRobot(final UUID robotID)
    {
        return _robotList.get(robotID);
    }

    public boolean ContainsRobot(final UUID robotID)
    {
        return _robotList.containsKey(robotID);
    }

    public boolean IsRobotAtPosition(Position position)
    {
        for (Robot robot:  _robotList.values())
        {
            boolean isSame = robot.CurrentPosition.equals(position);

            if(isSame)
            {
                return true;
            }
        }

        return false;
    }

    public boolean IsInRoom(final Position position)
    {
        final int x = position.X;
        final int y = position.Y;

        return x >= 0 && x <= Width && y >= 0 && y <= Height;
    }

    /**
     * Try to get a portal at this specific position.
     * @param position Current position.
     * @return If there is a portal at this position it will be returned, else null will be returned.
     */
    public Portal GetPortalFromPosition(Position position)
    {
        for (final Portal portal : _portalList.values())
        {
            boolean sameLocation = portal.Location.equals(position);

            if(sameLocation)
            {
                return portal;
            }
        }

        return null;
    }

    public Position GetNextPossiblePosition(MoveCommand moveCommand)
    {
        final Robot robot = _robotList.get(moveCommand.RobotUUID);
        int nearestWall;
        int maximalSteps = Integer.MAX_VALUE;
        Position moveVectir = new Position(0,0);

        // Get worldBorder
        switch (moveCommand.MoveDirection)
        {
            case Up:
                nearestWall = Height;
                break;

            case Down:
            case Left:
                nearestWall = 0;
                break;

            default:
            case Right:
                nearestWall = Width;
                break;
        }

        for (final Wall wall : _wallList) // Go thru every barrier
        {
            if (moveCommand.Orientation != wall.Orientation)  // Get only the barriers with opposite axis
            {
                boolean canCollide = wall.Collide(robot.CurrentPosition, moveCommand.Orientation);

                if (canCollide)
                {
                    // We can collide with the wall

                    int barrierValue = wall.GetWallOrientationValue();

                    // Check for range
                    if(Position.IsBNearer(robot.CurrentPosition,nearestWall, barrierValue, moveCommand.MoveDirection))
                    {
                        nearestWall = barrierValue;
                    }
                }
            }
        }

        switch (moveCommand.MoveDirection)
        {
            case Up:
                moveVectir.Set(0, Math.min(nearestWall-1, moveCommand.MoveSteps));
                break;

            case Down:
                if(robot.CurrentPosition.X - moveCommand.MoveSteps < nearestWall)
                {
                    int nextPos = Math.max(nearestWall, -moveCommand.MoveSteps);                   ;

                    nextPos = Math.abs( robot.CurrentPosition.Y - nextPos);

                    moveVectir.Set(0, -nextPos);
                }
                else
                {
                    moveVectir.Set(0, -Math.min(nearestWall+1, moveCommand.MoveSteps));
                }

                break;

            case Left:

                if(robot.CurrentPosition.X - moveCommand.MoveSteps < nearestWall)
                {
                    int nextPos = Math.max(nearestWall, -moveCommand.MoveSteps);                   ;

                    nextPos = Math.abs( robot.CurrentPosition.X - nextPos);

                    moveVectir.Set(-nextPos, 0);
                }
                else
                {
                    moveVectir.Set(-Math.min(nearestWall+1, moveCommand.MoveSteps), 0);
                }

                break;

            case Right:
                moveVectir.Set(Math.min(nearestWall-1, moveCommand.MoveSteps), 0);
                break;
        }

        return moveVectir;
    }
}
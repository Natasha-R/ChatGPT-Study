package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.InvalidPositionException;
import thkoeln.st.st2praktikum.exercise.utility.WallOrientation;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Room
{
    @Embedded
    private final List<Barrier> wallList = new ArrayList();

    @Embedded
    private final Map<UUID, TidyUpRobot> robotList = new HashMap<>();

    @Id
    private UUID uuid;

    private int width;
    private int height;

    public Room(final int width, final int height)
    {
        setUUID(UUID.randomUUID());
        setWidth(width);
        setHeight(height);
    }

    public void addWall(final int xA, final int yA, final int xB, final int yB)
    {
        final Vector2D a = new Vector2D(xA, yA);
        final Vector2D b = new Vector2D(xB, yB);

        addWall(a, b);
    }

    public void addWall(final Vector2D a, final Vector2D b)
    {
        final Barrier wall = new Barrier(a, b);

        addWall(wall);
    }

    public void addWall(final Barrier wall)
    {
        boolean canBeAdded = isInRoom(wall.getStart()) && isInRoom(wall.getEnd());

        if(!canBeAdded)
        {
            throw new InvalidPositionException("Barrier has positions outside a room.");
        }

        wallList.add(wall);
    }

     /*
    public void addPortal(int xFrom, int yFrom, int xTo, int yTo, final UUID pointToRoomID)
    {
        final Vector2D from = new Vector2D(xFrom, yFrom);
        final Vector2D to = new Vector2D(xTo, yTo);

        addPortal(from, to, pointToRoomID);
    }

    public void addPortal(final Vector2D location, final Vector2D pointToPosition, final UUID pointToRoomID)
    {
        final Portal portal = new Portal(location, pointToPosition, pointToRoomID);

        addPortal(portal);
    }

    public void addPortal(final Portal portal)
    {
        boolean canBeAdded = isInRoom(portal.getLocation());

        if(!canBeAdded)
        {
            throw new InvalidPositionException("Portal has positions outside a room.");
        }

        portalList.put(portal.getToRoomID(), portal);
    }
        /**
     * Try to get a portal at this specific position.
     * @param position Current position.
     * @return If there is a portal at this position it will be returned, else null will be returned.
     * /
     public Portal getPortalFromPosition(Vector2D position)
     {
         for (final Portal portal : portalList.values())
         {
             boolean sameLocation = portal.getLocation().equals(position);

             if(sameLocation)
             {
                 return portal;
             }
         }

         return null;
     }


    */

    public void addRobot(final TidyUpRobot robot)
    {
        robotList.put(robot.getUUID(), robot);
        robot.setRoomID(getUUID());
    }

    public void removeRobot(final UUID robot)
    {
        robotList.remove(robot);
    }

    public TidyUpRobot getRobot(final UUID robotID)
    {
        return robotList.get(robotID);
    }

    public boolean containsRobot(final UUID robotID)
    {
        return robotList.containsKey(robotID);
    }

    public boolean isRobotAtPosition(Vector2D position)
    {
        for (TidyUpRobot robot:  robotList.values())
        {
            boolean isSame = robot.getCurrentPosition().equals(position);

            if(isSame)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isInRoom(final Vector2D position)
    {
        final int x = position.getX();
        final int y = position.getY();

        return x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight();
    }

    public Vector2D getNextPossiblePosition(TidyUpRobot robot, Order moveCommand)
    {
        final int numberOfSteps = moveCommand.getNumberOfSteps();
        int nearestWall;
        int maximalSteps = Integer.MAX_VALUE;

        int correctedXPosition = 0;
        int correctedYPosition = 0;
        final Vector2D currentPosition = new Vector2D(robot.getCurrentPosition());

        // Get worldBorder
        switch (moveCommand.getType())
        {
            case NORTH:
                nearestWall = getHeight();
                break;

            case SOUTH:
            case WEST:
                nearestWall = 0;
                break;

            default:
            case EAST:
                nearestWall = getWidth();
                break;
        }

        for (final Barrier wall : wallList) // Go thru every barrier
        {
            WallOrientation moveOrientation;

            switch (moveCommand.getType())
            {
                case SOUTH:
                case NORTH:
                    moveOrientation = WallOrientation.VERTICAL;
                    break;

                default:
                case WEST:
                case EAST:
                    moveOrientation = WallOrientation.HORIZONTAL;
                    break;
            }

            if (moveOrientation != wall.getOrientation())  // Get only the barriers with opposite axis
            {
                boolean canCollide = wall.collide(robot.getCurrentPosition(), moveOrientation);

                if (canCollide)
                {
                    // We can collide with the wall

                    int barrierValue = wall.getWallOrientationValue();

                    // Check for range
                    if(Vector2D.isBNearer(robot.getCurrentPosition(),nearestWall, barrierValue, moveCommand.getType()))
                    {
                        nearestWall = barrierValue;
                    }
                }
            }
        }

        switch (moveCommand.getType())
        {
            case NORTH:
                correctedXPosition = 0;
                correctedYPosition = Math.min(nearestWall-1,moveCommand.getNumberOfSteps());
                break;

            case SOUTH:
                if(robot.getCurrentPosition().getX() - numberOfSteps < nearestWall)
                {
                    int nextPos = Math.max(nearestWall, -numberOfSteps);

                    nextPos = Math.abs( robot.getCurrentPosition().getY() - nextPos);

                    correctedXPosition = 0;
                    correctedYPosition = -nextPos;
                }
                else
                {
                    correctedXPosition = 0;
                    correctedYPosition = -Math.min(nearestWall+1, numberOfSteps);
                }

                break;

            case WEST:

                if(robot.getCurrentPosition().getX() -numberOfSteps < nearestWall)
                {
                    int nextPos = Math.max(nearestWall, -numberOfSteps);                   ;

                    nextPos = Math.abs( robot.getCurrentPosition().getX() - nextPos);

                    correctedXPosition = -nextPos;
                    correctedYPosition = 0;
                }
                else
                {
                    correctedXPosition = -Math.min(nearestWall+1, numberOfSteps);
                    correctedYPosition = 0;
                }

                break;

            case EAST:
                correctedXPosition = Math.min(nearestWall-1, numberOfSteps);
                correctedYPosition = 0;
                break;
        }

        currentPosition.add(correctedXPosition, correctedYPosition);

        return currentPosition;
    }

    public UUID getUUID()
    {
        return uuid;
    }

    private void setUUID(UUID uuid)
    {
        this.uuid = uuid;
    }

    public int getWidth()
    {
        return width;
    }

    private void setWidth(int value)
    {
        width = value;
    }

    public int getHeight()
    {
        return height;
    }

    private void setHeight(int value)
    {
        height = value;
    }
}
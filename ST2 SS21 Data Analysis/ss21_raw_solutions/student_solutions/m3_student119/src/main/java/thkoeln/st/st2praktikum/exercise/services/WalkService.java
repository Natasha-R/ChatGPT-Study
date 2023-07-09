package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.entities.BarrierType;
import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.space.ISpaceService;

public class WalkService implements IWalkService {
    public boolean walk(CommandType direction, int steps, IWalkable walkable, ISpaceService space) {
        for (int i = 0; i < steps; i++) {
            // if the border coordinate is lower than the barrier limit, we can move
            final boolean limitReached = barrierReached(direction, space, walkable);
            if(limitReached)
                return false;

            // get the next position without moving the device
            final Point preview = walkable.previewWalk(direction, 1);
            final boolean isTileFree = space.isFree(preview);
            if(!isTileFree)
                return false;

            walkable.walk(direction, 1);
        }

        return true;
    }

    private boolean barrierReached(CommandType direction, ISpaceService space, IWalkable position) {
        final int border = position.getBorder(direction);
        return space.getBarriers().stream().anyMatch(barrier -> {
            switch (direction) {
                // we are interested in barriers where the y coordinate is greater than or equal
                // the current tile position, and the x coordinate is in rage of barriers x coordinates
                case NORTH:
                case SOUTH:
                    return barrier.getBarrierType() == BarrierType.horizontal &&
                            position.leftTop().getX() >= barrier.getStart().getX() && position.rightTop().getX() <= barrier.getEnd().getX() &&
                            border == barrier.getStart().getY();
                case EAST:
                case WEST:
                    final boolean barrierTypeMatch = barrier.getBarrierType() == BarrierType.vertical;
                    final boolean barrierInRange = position.leftBottom().getY() >= barrier.getStart().getY() && position.leftTop().getY() <= barrier.getEnd().getY();
                    return barrierTypeMatch &&
                            barrierInRange &&
                            border == barrier.getStart().getX();
                default:
                    throw new UnsupportedOperationException();
            }
        });
    }
}


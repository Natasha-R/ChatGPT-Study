package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.space.ISpace;

class WalkService implements IWalkService {
    public boolean walk(CommandType direction, int steps, IWalkable walkable, ISpace space) {
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

    private boolean barrierReached(CommandType direction, ISpace space, IWalkable position) {
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

interface IWalkService {
    boolean walk(CommandType direction, int steps, IWalkable walkable, ISpace space);
}

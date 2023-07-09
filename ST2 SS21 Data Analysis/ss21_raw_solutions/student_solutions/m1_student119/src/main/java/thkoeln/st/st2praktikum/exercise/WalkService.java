package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.barrier.BarrierType;
import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.space.ISpace;

class WalkService implements IWalkService {
    public boolean walk(Direction direction, int steps, IWalkable walkable, ISpace space) {
        for (int i = 0; i < steps; i++) {
            // if the border coordinate is lower than the barrier limit, we can move
            final boolean limitReached = barrierReached(direction, space, walkable);
            if(limitReached)
                return false;

            walkable.walk(direction, 1);
        }

        return true;
    }

    private boolean barrierReached(Direction direction, ISpace space, IWalkable position) {
        final int border = position.getBorder(direction);
        return space.getBarriers().stream().anyMatch(barrier -> {
            switch (direction) {
                // we are interested in barriers where the y coordinate is greater than or equal
                // the current tile position, and the x coordinate is in rage of barriers x coordinates
                case north:
                case south:
                    return barrier.getType() == BarrierType.horizontal &&
                            position.leftTop().x >= barrier.getStart().x && position.rightTop().x <= barrier.getEnd().x &&
                            border == barrier.getStart().y;
                case east:
                case west:
                    final boolean barrierTypeMatch = barrier.getType() == BarrierType.vertical;
                    final boolean barrierInRange = position.leftBottom().y >= barrier.getStart().y && position.leftTop().y <= barrier.getEnd().y;
                    return barrierTypeMatch &&
                            barrierInRange &&
                            border == barrier.getStart().x;
                default:
                    throw new UnsupportedOperationException();
            }
        });
    }
}

interface IWalkService {
    boolean walk(Direction direction, int steps, IWalkable walkable, ISpace space);
}

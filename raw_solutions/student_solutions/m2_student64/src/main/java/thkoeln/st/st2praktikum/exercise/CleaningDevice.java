package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Exception.NegativePointCoordinateException;

import java.util.UUID;
@Getter
public class CleaningDevice implements IMovable {
    private final UUID uuid;

    @Setter
    private Point position;
    private final String name;

    @Setter
    private Space space;

    public CleaningDevice(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.position = new Point(0, 0);
    }

    @Override
    public boolean executeTask(Task task) {
        if(task.getTaskType().equals(TaskType.TRANSPORT))
        {
            return this.space.useConnection(this);
        }

        return this.moveIn(task.getTaskType(), task.getNumberOfSteps());
    }

    public boolean moveIn(TaskType movePosition, int numberOfSteps)
    {
        Point positionAfterSingleMove;
        for(int currentStep = 0; currentStep < numberOfSteps; ++currentStep)
        {
            try {
                positionAfterSingleMove = this.getNextPositionWhenMovedByOneIn(movePosition);
            }
            catch (NegativePointCoordinateException e) {
                return false;
            }

            if(this.space.isVectorColliding(this.position, positionAfterSingleMove))
            {
                return false;
            }

            this.position = positionAfterSingleMove;
        }

        return true;
    }

    protected Point getNextPositionWhenMovedByOneIn(TaskType movePosition)
    {
        Point currentPosition = new Point(this.position.getX(), this.position.getY());

        switch (movePosition)
        {
            case NORTH:
                currentPosition.addOneToY();
                break;
            case SOUTH:
                currentPosition.removeOneFromY();
                break;
            case EAST:
                currentPosition.addOneToX();
                break;
            case WEST:
                currentPosition.removeOneFromX();
                break;
            default:
                throw new RuntimeException();
        }

        return currentPosition;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    public void setSpace(Space space)
    {
        System.out.println(String.format("%s", space.getUUID()));

        this.space = space;
    }
}

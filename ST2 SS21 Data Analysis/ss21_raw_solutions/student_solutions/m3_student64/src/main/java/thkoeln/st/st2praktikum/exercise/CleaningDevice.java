package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.Exception.NegativePointCoordinateException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CleaningDevice extends AbstractEntity implements IMovable {
    private Point position;
    private String name;

    @ManyToOne
    private Space space;

    public CleaningDevice(String name) {
        this.name = name;
        this.position = new Point(0, 0);
    }

    public UUID getSpaceId()
    {
        return this.space.getId();
    }

    public Point getPoint()
    {
        return this.position;
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

    public void setSpace(Space space)
    {
        System.out.printf("%s%n", space.getId());

        this.space = space;
    }
}

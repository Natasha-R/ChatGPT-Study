package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import thkoeln.st.st2praktikum.Core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.exception.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CleaningDevice extends AbstractEntity {
    private Point position;
    private String name;

    @ManyToOne
    private Space space;

    @ElementCollection(targetClass = Task.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Task> Tasks = new ArrayList<>();


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

    public boolean executeTask(Task task) {

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

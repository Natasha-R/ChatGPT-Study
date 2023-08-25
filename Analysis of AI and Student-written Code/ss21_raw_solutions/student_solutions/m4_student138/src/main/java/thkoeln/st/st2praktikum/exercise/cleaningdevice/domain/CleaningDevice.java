package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CleaningDevice implements RobotActions {

    private String name;
    @Id
    private UUID uuid;
    private UUID currentSpace;
    //@Embedded
    private Vector2D position;

    @ElementCollection
    private List<Command> commandsList;

    public List<Command> getCommandsList() {
        return commandsList;
    }

    public void addCommand(Command command)
    {
        commandsList.add(command);
    }

    public void clearCommandHistory()
    {
        commandsList.clear();
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public UUID getSpaceId() {
        return currentSpace;
    }

    public UUID getUuid() {
        return uuid;
    }

    public CleaningDevice(String _name) {
        name = _name;
        uuid = UUID.randomUUID();
        commandsList = new ArrayList<Command>();
    }

    public CleaningDevice(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
        commandsList = new ArrayList<Command>();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean move(Space currentSpace, CommandType direction, int moveFields) {
        if (currentSpace == null)
        {
            throw new NullPointerException("The cleaner is currentlposition.getY() not on a space");
        }

        for(int i = 0; i < moveFields; i++)
        {
            if (direction == CommandType.NORTH && position.getY()+1 < currentSpace.getHeight() && currentSpace.getField(position.getX(), position.getY()).canMoveOut("no") && currentSpace.getField(position.getX(), position.getY()+1).canMoveIn("no"))
            {
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(false);
                position = new Vector2D(position.getX(), position.getY() + 1);
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(true);
            }
            if (direction == CommandType.EAST && position.getX()+1 < currentSpace.getWidth() && currentSpace.getField(position.getX(), position.getY()).canMoveOut("ea") && currentSpace.getField(position.getX()+1, position.getY()).canMoveIn("ea"))
            {
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(false);
                position = new Vector2D(position.getX() + 1, position.getY());
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(true);
            }
            if (direction == CommandType.SOUTH && position.getY()-1 >= 0 && currentSpace.getField(position.getX(), position.getY()).canMoveOut("so") && currentSpace.getField(position.getX(), position.getY()-1).canMoveIn("so"))
            {
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(false);
                position = new Vector2D(position.getX(), position.getY() -1);
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(true);
            }
            if (direction == CommandType.WEST && position.getX()-1 >= 0 && currentSpace.getField(position.getX(), position.getY()).canMoveOut("we") && currentSpace.getField(position.getX()-1, position.getY()).canMoveIn("we"))
            {
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(false);
                position = new Vector2D(position.getX() - 1, position.getY());
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(true);
            }
        }

        return true;
    }

    public Vector2D getVector2D() {
        return position;
    }

    @Override
    public boolean transport(Space currentSpace, Space destinationSpace) {
        if (currentSpace.getField(position.getX(), position.getY()).isHasCleaner())
        {
            Connection connection = currentSpace.getField(position.getX(), position.getY()).getConnection();
            /* if (connection == null)
                throw new NullPointerException("The current field of the player doesnt have a connection"); */

            if (connection != null && connection.getSourceSpaceId() == currentSpace.getUuid() && connection.getDestinationSpaceId() == destinationSpace.getUuid()
                    && !destinationSpace.getField(connection.getDestinationVector().getX(), connection.getDestinationVector().getY()).isHasCleaner())
            {
                currentSpace.getField(position.getX(), position.getY()).setHasCleaner(false);
                destinationSpace.getField(connection.getDestinationVector().getX(), connection.getDestinationVector().getY()).setHasCleaner(true);
                this.currentSpace = destinationSpace.getUuid();

                position = connection.getDestinationVector();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean enterSpace(Space destinationSpace) {
        if (currentSpace == null && !destinationSpace.getField(0, 0).isHasCleaner())
        {
            destinationSpace.getField(0, 0).setHasCleaner(true);
            currentSpace = destinationSpace.getUuid();
            position = new Vector2D(0,0);
            return true;
        }
        else
        {
            return false;
        }
    }
}

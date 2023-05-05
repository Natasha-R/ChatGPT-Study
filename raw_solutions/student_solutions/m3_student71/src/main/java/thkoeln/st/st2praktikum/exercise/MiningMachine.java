package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.*;
//import org.hibernate.annotations.Entity;

import javax.management.InvalidAttributeValueException;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;


@Entity
@NoArgsConstructor
public class MiningMachine extends UUIDBase implements Serializable {

    @Getter
    @Setter
    private String name;
    @Getter @Setter
    private Coordinate coordinate;

    @Getter @Setter
    @ManyToOne(targetEntity = Field.class)
    private Field field;

    public MiningMachine(String name){
        super();
        this.name = name;

    }

    public void executeCommand(Command command) throws InvalidAttributeValueException, TeleportMiningMachineException, SpawnMiningMachineException {

        switch (command.getCommandType()){
            case NORTH:
                this.moveNorth(command.getNumberOfSteps());
                break;

            case WEST:
                this.moveWest(command.getNumberOfSteps());
                break;

            case SOUTH:

                this.moveSouth(command.getNumberOfSteps());
                break;

            case EAST:
                this.moveEast(command.getNumberOfSteps());
                break;

            case TRANSPORT:
                this.teleport(this.field.getTile(coordinate).getConnection());
                break;

            case ENTER:
                break;
        }
    }

    public void changePosition(Field field, Coordinate coordinates){
        this.field = field;
        coordinate = coordinates;
    }

    public UUID getFieldId(){
        return field.getUuid();
    }

    private void moveNorth(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = field.getTile(coordinate);

            if(!currentTile.isNorthWalkable())
                return;

            var moveTile = field.getTile(coordinate.getX(), coordinate.getY() +1);

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            coordinate.setY(coordinate.getY()+1);
        }
    }

    private void moveEast(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = field.getTile(coordinate);

            if(!currentTile.isEastWalkable())
                return;

            var moveTile = field.getTile(coordinate.getX() +1, coordinate.getY());

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            coordinate.setX(coordinate.getX()+1);
        }
    }

    private void moveSouth(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = field.getTile(coordinate);

            if(!currentTile.isSouthWalkable())
                return;

            var moveTile = field.getTile(coordinate.getX(), coordinate.getY() -1);

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            coordinate.setY(coordinate.getY()-1);
        }
    }

    private void moveWest(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = field.getTile(coordinate);

            if(!currentTile.isWestWalkable())
                return;

            var moveTile = field.getTile(coordinate.getX() -1, coordinate.getY());

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            coordinate.setX(coordinate.getX()-1);
        }
    }

    private void teleport(Connection connection) throws TeleportMiningMachineException,NullPointerException {
        if(connection == null)
            throw new TeleportMiningMachineException("Connection couldn't be found");

        if(field.getUuid() != connection.getSourceField().getUuid())
            throw new TeleportMiningMachineException("MiningMachine isn't on sourceField");

        if(!coordinate.equals(connection.getSourceCoordinates()))
            throw new TeleportMiningMachineException("MiningMachine isn't on sourceCoordinate");

        var destinationTile = connection.getDestinationField().getTile(connection.getDestinationCoordinates());

        if(destinationTile.isOccupied())
            throw new TeleportMiningMachineException("Tile is already occupied!");

        var currentTile = field.getTile(coordinate);
        currentTile.setContent(null);
        destinationTile.setContent(this);

        changePosition(connection.getDestinationField(), connection.getDestinationCoordinates());

    }
}

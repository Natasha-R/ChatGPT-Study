package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.management.InvalidAttributeValueException;
import java.util.UUID;

public class MiningMachine extends UUIDBase implements IContent {

    @Getter
    private String name;
    @Getter
    private Coordinate position;

    private Field currentField;

    public MiningMachine(String name){
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
                this.teleport(this.getCurrentTile().getConnection());
                break;

            case ENTER:
                var field = World.getInstance().getFieldList().get(command.getGridId());
                field.spawnMiningMachine(this,new Coordinate(0,0));
                break;
        }
    }

    public void changePosition(Field field, Coordinate coordinates){
        currentField = field;
        position = coordinates;
    }

    public UUID getFieldID(){ return currentField.getUuid();}

    private Tile getCurrentTile(){
        return currentField.getTile(position);
    }

    private void moveNorth(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = currentField.getTile(position);

            if(!currentTile.isNorthWalkable())
                return;

            var moveTile = currentField.getTile(position.getX(), position.getY() +1);

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            position.setY(position.getY()+1);
        }
    }

    private void moveEast(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = currentField.getTile(position);

            if(!currentTile.isEastWalkable())
                return;

            var moveTile = currentField.getTile(position.getX() +1, position.getY());

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            position.setX(position.getX()+1);
        }
    }

    private void moveSouth(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = currentField.getTile(position);

            if(!currentTile.isSouthWalkable())
                return;

            var moveTile = currentField.getTile(position.getX(), position.getY() -1);

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            position.setY(position.getY()-1);
        }
    }

    private void moveWest(int distance) {
        for (int i = 0; i < distance; i++) {
            var currentTile = currentField.getTile(position);

            if(!currentTile.isWestWalkable())
                return;

            var moveTile = currentField.getTile(position.getX() -1, position.getY());

            if(moveTile.isOccupied())
                return;

            currentTile.setContent(null);
            moveTile.setContent(this);

            position.setX(position.getX()-1);
        }
    }

    private void teleport(Connection connection) throws TeleportMiningMachineException,NullPointerException {
        if(connection == null)
            throw new TeleportMiningMachineException("Connection couldn't be found");

        if(currentField.getUuid() != connection.getSourceField().getUuid())
            throw new TeleportMiningMachineException("MiningMachine isn't on sourceField");

        if(!position.equals(connection.getSourceCoordinates()))
            throw new TeleportMiningMachineException("MiningMachine isn't on sourceCoordinate");

        var destinationTile = connection.getDestinationField().getTile(connection.getDestinationCoordinates());

        if(destinationTile.isOccupied())
            throw new TeleportMiningMachineException("Tile is already occupied!");

        var currentTile = currentField.getTile(position);
        currentTile.setContent(null);
        destinationTile.setContent(this);

        changePosition(connection.getDestinationField(), connection.getDestinationCoordinates());

    }
}

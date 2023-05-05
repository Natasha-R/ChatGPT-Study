package thkoeln.st.st2praktikum.exercise.movable;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.DataStorage;
import thkoeln.st.st2praktikum.exercise.command.CommandProzessing;
import thkoeln.st.st2praktikum.exercise.field.Connection;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.field.Obsticale;
import thkoeln.st.st2praktikum.exercise.command.CommandType;
import thkoeln.st.st2praktikum.exercise.field.Coordinate;

import java.util.Map;
import java.util.UUID;

@Getter
public class MiningMachine implements Moveable {
    private UUID uuid = UUID.randomUUID();
    @Setter
    private String name;

    private UUID fieldId;
    private Coordinate position = Coordinate.turnStringToCoordinate("(0,0)");


    public MiningMachine(String name) {
        this.name = name;
    }

    @Override
    public boolean chooseAction(String commandString) {
        CommandProzessing commandAtributes = new CommandProzessing(commandString);

        Coordinate wishedPosition;
        if (!commandAtributes.isStatus()) return false;
        if (commandAtributes.getCommandType().isMoveCommand()) {
            //TODO this if case in own method - is too big
            wishedPosition = calculateNewPosition(commandAtributes.getCommandType(), commandAtributes.getSteps());
            wishedPosition = correctToBeWithinBoarders(wishedPosition, DataStorage.getFieldMap().get(fieldId));
            wishedPosition = correctByObsticaleInTheWay(commandAtributes.getCommandType(), wishedPosition);
            wishedPosition = correctByMovableInTheWay(wishedPosition, commandAtributes.getCommandType(), fieldId);

            commandAtributes.setStatus(position != wishedPosition);
            position = wishedPosition;
            MiningMachine miningMachine = (MiningMachine) DataStorage.getMoveableMap().get(uuid);
            DataStorage.getMoveableMap().replace(uuid, miningMachine);

        } else if (CommandType.en == commandAtributes.getCommandType()) {
            commandAtributes.setStatus(positionForFirstTime(commandAtributes.getFieldId()));

        } else if (CommandType.tr == commandAtributes.getCommandType()) {
            commandAtributes.setStatus(positioningByTransition(commandAtributes.getFieldId()));

        } else return false;

        return commandAtributes.isStatus();
    }

    private Coordinate calculateNewPosition(CommandType commandType, int steps) {
        Coordinate coordinate = new Coordinate(position.x, position.y);
        switch (commandType) {
            case no:
                coordinate.y += steps;
                break;
            case so:
                coordinate.y -= steps;
                break;
            case ea:
                coordinate.x += steps;
                break;
            case we:
                coordinate.x -= steps;
        }
        return coordinate;
    }

    private Coordinate correctToBeWithinBoarders(Coordinate wishedPosition, Field field) {
        if (wishedPosition.x >= field.getWidth()) wishedPosition.x = field.getWidth() - 1;
        else if (wishedPosition.y >= field.getHeight()) wishedPosition.y = field.getHeight() - 1;
        else if (wishedPosition.x < 0) wishedPosition.x = 0;
        else if (wishedPosition.y < 0) wishedPosition.y = 0;
        return wishedPosition;
    }

    private Coordinate correctByObsticaleInTheWay(CommandType commandType, Coordinate wishedPosition) {
        switch (commandType) {
            case no:
                return correctForMovingNorth(wishedPosition);
            case so:
                return correctForMovingSouth(wishedPosition);
            case ea:
                return correctForMovingEast(wishedPosition);
            case we:
                return correctForMovingWest(wishedPosition);
        }
        return wishedPosition;
    }

    private Coordinate correctByMovableInTheWay(Coordinate wishedPosition, CommandType commandType, UUID fieldId) {
        for (Map.Entry<UUID, Moveable> entry : DataStorage.getMoveableMap().entrySet()) {
            Coordinate entryCoordinate = new Coordinate();
            entryCoordinate.x = entry.getValue().getPosition().x;
            entryCoordinate.y = entry.getValue().getPosition().y;

            //TODO You can not crash into yourself

            if (fieldId == entry.getValue().getFieldId() && wishedPosition == entryCoordinate) {
                switch (commandType) {
                    case no:
                        wishedPosition.y--;
                        break;
                    case so:
                        wishedPosition.y++;
                        break;
                    case ea:
                        wishedPosition.x--;
                        break;
                    case we:
                        wishedPosition.x++;
                }
                correctByMovableInTheWay(wishedPosition, commandType, fieldId);
            }
        }
        return wishedPosition;
    }

    private boolean positionForFirstTime(UUID wishedDestinationFieldId) {
        if (fieldId == null) {
            if (checkPositionIsFree(new Coordinate(0, 0), wishedDestinationFieldId)) {
                fieldId = wishedDestinationFieldId;
                MiningMachine miningMachine = (MiningMachine) DataStorage.getMoveableMap().get(uuid);
                DataStorage.getMoveableMap().replace(uuid, miningMachine);
                return true;
            }
        }
        return false;
    }

    private boolean positioningByTransition(UUID wishedDestinationFieldId) {

        for (Map.Entry<UUID, Connection> connectionEntry : DataStorage.getConnectionMap().entrySet()) {
            // geht die Connection von meinem Feld und Position aus?
            if (fieldId.equals(connectionEntry.getValue().getSourceFieldId()) && position.x == connectionEntry.getValue().getSourceCoordinate().x && position.y == connectionEntry.getValue().getSourceCoordinate().y) {
                // geht die Conenction auch dahin, wo ich will und ist das Ankunftsfeld frei?
                if (wishedDestinationFieldId.equals(connectionEntry.getValue().getDestinationFieldId()) && checkPositionIsFree(connectionEntry.getValue().getDestinationCoordinate(), connectionEntry.getValue().getDestinationFieldId())) {
                    position = connectionEntry.getValue().getDestinationCoordinate();
                    fieldId = connectionEntry.getValue().getDestinationFieldId();
                    MiningMachine miningMachine = (MiningMachine) DataStorage.getMoveableMap().get(uuid);
                    DataStorage.getMoveableMap().replace(uuid, miningMachine);
                    return true;
                }
            }
        }
        return false;
    }

    private Coordinate correctForMovingNorth(Coordinate wishedPosition) {
        Coordinate newCoordinate = new Coordinate(wishedPosition.x, wishedPosition.y);
        for (Map.Entry<UUID, Obsticale> entry : DataStorage.getHorizontalObsticalMap().entrySet()) {
            if (fieldId.equals(entry.getValue().getFieldId())) {
                if (position.x >= entry.getValue().getStart().x && position.x <= entry.getValue().getEnd().x - 1) {
                    if (position.y < entry.getValue().getStart().y && wishedPosition.y >= entry.getValue().getStart().y) {
                        newCoordinate.y = entry.getValue().getStart().y - 1;
                    }
                }
            }
        }
        return newCoordinate;
    }

    private Coordinate correctForMovingSouth(Coordinate wishedPosition) {
        Coordinate newCoordinate = new Coordinate(wishedPosition.x, wishedPosition.y);
        for (Map.Entry<UUID, Obsticale> entry : DataStorage.getHorizontalObsticalMap().entrySet()) {
            if (fieldId.equals(entry.getValue().getFieldId())) {
                if (position.x >= entry.getValue().getStart().x && position.x <= entry.getValue().getEnd().x - 1) {
                    if (position.y > entry.getValue().getStart().y && wishedPosition.y <= entry.getValue().getStart().y) {
                        newCoordinate.y = entry.getValue().getStart().y + 1;
                    }
                }
            }
        }
        return newCoordinate;
    }

    private Coordinate correctForMovingEast(Coordinate wishedPosition) {
        Coordinate newCoordinate = new Coordinate(wishedPosition.x, wishedPosition.y);
        for (Map.Entry<UUID, Obsticale> entry : DataStorage.getHorizontalObsticalMap().entrySet()) {
            if (fieldId.equals(entry.getValue().getFieldId())) {
                if (position.y >= entry.getValue().getStart().y && position.y <= entry.getValue().getEnd().y - 1) {
                    if (position.x < entry.getValue().getStart().x && wishedPosition.x >= entry.getValue().getStart().x) {
                        newCoordinate.x = entry.getValue().getStart().x - 1;
                    }
                }
            }
        }
        return newCoordinate;
    }

    private Coordinate correctForMovingWest(Coordinate wishedPosition) {
        Coordinate newCoordinate = new Coordinate(wishedPosition.x, wishedPosition.y);
        for (Map.Entry<UUID, Obsticale> entry : DataStorage.getHorizontalObsticalMap().entrySet()) {
            if (fieldId.equals(entry.getValue().getFieldId())) {
                if (position.y >= entry.getValue().getStart().y && position.y <= entry.getValue().getEnd().y - 1) {
                    if (position.x > entry.getValue().getStart().x && wishedPosition.x <= entry.getValue().getStart().x) {
                        newCoordinate.x = entry.getValue().getStart().x + 1;
                    }
                }
            }
        }
        return newCoordinate;
    }

    private boolean checkPositionIsFree(Coordinate destinationCoordinate, UUID destinationFieldID) {
        for (Map.Entry<UUID, Moveable> miningMachineEntry : DataStorage.getMoveableMap().entrySet()) {
            if (destinationFieldID.equals(miningMachineEntry.getValue().getFieldId())) {
                if (destinationCoordinate.x == miningMachineEntry.getValue().getPosition().x && destinationCoordinate.y == miningMachineEntry.getValue().getPosition().y) { //steht ihr auch da, wo ich hin will?
                    return false;
                }
            }
        }
        return true;
    }
}
package thkoeln.st.st2praktikum.exercise;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class MaintenanceDroid extends Moveable {
    private UUID uuid;
    private String name;
    private UUID spaceShipDeckId;
    private Coordinate position = Coordinate.switchStringToCoordinate("(0,0)");

    public MaintenanceDroid(String name) {
        spaceShipDeckId = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public boolean move(MaintenanceDroid maintenanceDroid, String commandString) {
        MoveSteps moveAttributes = new MoveSteps(commandString);
        if (!moveAttributes.isStatus()) return false;
        if (moveAttributes.getMoveType().checkMoveType()) {
            Coordinate newCoordinate = calculateNewPosition(maintenanceDroid.position, moveAttributes.getMoveType(), moveAttributes.getPlaceToMove());
            newCoordinate =positionBoarder(newCoordinate, MapsStorage.getSpaceShipDeckMap().get(maintenanceDroid.spaceShipDeckId));
            newCoordinate =positionByBarrierInTheWay();
            newCoordinate =positionByMovableInTheWay(newCoordinate, moveAttributes.moveType);
            newCoordinate =positionBoarder(newCoordinate, MapsStorage.getSpaceShipDeckMap().get(maintenanceDroid.spaceShipDeckId));


            maintenanceDroid.position = newCoordinate;
        } else if (MoveType.en == moveAttributes.getMoveType()) {
            moveAttributes.setStatus(positionFirstTime(maintenanceDroid, moveAttributes.getSpaceShipDeckId()));
        } else if (MoveType.tr == moveAttributes.getMoveType()) {
            Coordinate newCoordinate =positionTransition(maintenanceDroid.position, maintenanceDroid.getSpaceShipDeckId(), moveAttributes.getSpaceShipDeckId());
            moveAttributes.setStatus(maintenanceDroid.position != newCoordinate);

            maintenanceDroid.position = newCoordinate;
        }
        else  return false;

        return moveAttributes.isStatus();
    }

    public Coordinate calculateNewPosition(Coordinate oldCoordinate, MoveType moveType, Integer placeToMove) {
        switch (moveType) {
            case no:
                oldCoordinate.y += placeToMove;
            case so:
                oldCoordinate.y -= placeToMove;
            case ea:
                oldCoordinate.x += placeToMove;
            case we:
                oldCoordinate.x -= placeToMove;
        }
        return oldCoordinate;
    }

    @Override
    public Coordinate positionBoarder(Coordinate wishedPosition, SpaceShipDeck spaceShipDeck) {
        if (wishedPosition.x <= spaceShipDeck.getWidth()) wishedPosition.x = spaceShipDeck.getWidth() - 1;
        else if (wishedPosition.y <= spaceShipDeck.getHeight()) wishedPosition.y = spaceShipDeck.getHeight() - 1;
        else if (wishedPosition.x < 0) wishedPosition.x = 0;
        else if (wishedPosition.y < 0) wishedPosition.y = 0;
        return wishedPosition;
    }

    /*@Override
    public Coordinate positionByBarrierInTheWay(MoveType moveType, Coordinate currentPosition, Coordinate wishedPosition) {
        // hashMap Barrier ob welche vorhanden sind -> if true then schauen
        // wenn zwischen currentPosition und wishedPosition eine barrier , dann ein schritt zurück
        // forschleife immer wieder wiederholen

        if(MoveType.ea == moveType || MoveType.we == moveType ){
            switch (moveType){
                case ea: wishedPosition.x =   ;
                case we: wishedPosition.x = ;
            }
        }
            return null;
    }*/

    @Override
    public Coordinate positionByMovableInTheWay(Coordinate wishedPosition, MoveType moveType) {
        for (Map.Entry<UUID, MaintenanceDroid> maintenanceDroidEntry : MapsStorage.getMaintenanceDroidMap().entrySet()) {
            if (wishedPosition == maintenanceDroidEntry.getValue().getPosition()) {
                switch (moveType) {
                    case no:
                        wishedPosition.y--;
                    case so:
                        wishedPosition.y++;
                    case ea:
                        wishedPosition.x--;
                    case we:
                        wishedPosition.x++;
                }
            }
        }
        return wishedPosition;
    }

    @Override
    public Boolean positionFirstTime(MaintenanceDroid maintenanceDroid, UUID wishedDestinationSpaceShip) {
        if (maintenanceDroid.getUuid() == null) {
            if (checkPositionIsFree(new Coordinate(0, 0), wishedDestinationSpaceShip)) {
                maintenanceDroid.spaceShipDeckId = wishedDestinationSpaceShip;
                return true;
            }
        }
        return false;
    }


    public Boolean checkPositionIsFree(Coordinate destinationCoordinate, UUID destinationSpaceShipDeckId) {
        for (Map.Entry<UUID, MaintenanceDroid> maintenanceDroidEntry : MapsStorage.getMaintenanceDroidMap().entrySet()) {
            if (destinationSpaceShipDeckId == maintenanceDroidEntry.getValue().getUuid()) {
                if (destinationCoordinate == maintenanceDroidEntry.getValue().getPosition()) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public Coordinate positionTransition(Coordinate currentPosition, UUID currentSpacShipDeckId, UUID wishedDestinationSpaceShip) {
        for (Map.Entry<UUID, Connection> connectionEntry : MapsStorage.getConnectionMap().entrySet()) {
            if (currentSpacShipDeckId == connectionEntry.getValue().getSourceID() && currentPosition == connectionEntry.getValue().getSource()) {
                if (wishedDestinationSpaceShip == connectionEntry.getValue().getDestinationID() && checkPositionIsFree(connectionEntry.getValue().getDestination(), connectionEntry.getValue().getDestinationID())) {
                    currentPosition = connectionEntry.getValue().getDestination();
                }
            }
        }
        return currentPosition;
    }

    @Override
    public Coordinate positionByBarrierInTheWay(MoveType moveType, Coordinate currentPosition, Coordinate wishedPosition) {
        return null;
    }
}

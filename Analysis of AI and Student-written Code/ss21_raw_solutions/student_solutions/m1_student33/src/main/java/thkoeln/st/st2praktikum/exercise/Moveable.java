package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public abstract class Moveable {
    private UUID uuid;
    private UUID id;
    private UUID spaceShipDeckId;
    private Coordinate position = Coordinate.switchStringToCoordinate("(0,0)");

    public Moveable() {
        uuid = UUID.randomUUID();
    }

    public abstract boolean move(MaintenanceDroid maintenanceDroid, String commandString);

    public abstract Coordinate positionBoarder(Coordinate wishedPosition, SpaceShipDeck spaceShipDeck);

    public abstract Coordinate positionTransition(Coordinate currentPosition, UUID currentSpacShipDeckId, UUID wishedDestinationSpaceShip);

    public abstract Coordinate positionByBarrierInTheWay(MoveType moveType, Coordinate currentPosition, Coordinate wishedPosition);

    public abstract Coordinate positionByMovableInTheWay(Coordinate wishedPosition, MoveType moveType);

    public abstract Boolean positionFirstTime(MaintenanceDroid maintenanceDroid0,UUID wishedDestinationSpaceShip);


}

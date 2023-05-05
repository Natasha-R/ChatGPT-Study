package thkoeln.st.st2praktikum.exercise;


import java.util.UUID;

public class Robot extends AbstractRobot implements Occupied {

    public Robot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public XYPositionable initializeIntoRoom(XYPositionable newPosition) throws PositionBlockedException {
        Roomable newRoom = newPosition.getRoom();
        if (newRoom.roomPositionIsEmpty(newPosition)) {
            newRoom.addOccupiedPosition(this);
            setNewPosition(newPosition);
            return this.position;
        }
        throw new PositionBlockedException("The requested initialization position in the room is blocked");
    }

    @Override
    public XYPositionable simpleMovement(XYMovable movement) {
        movement = getCurrentRoom().validateMovement(movement);
        setNewPosition(movement.getCurrentPosition());
        return this.position;
    }

    @Override
    public XYPositionable transportRobot(Roomable targetRoom) throws NoDataFoundException, PositionBlockedException {
        Roomable oldRoom = getCurrentRoom();
        XYPositionable currentPosition = getCurrentPosition();
        XYPositionable newPosition;
        if (getCurrentRoom().isTransportable(currentPosition, targetRoom)) {
            newPosition = getCurrentRoom().getNewTransportedPosition(currentPosition, targetRoom);
            addRobotToNewRoom(targetRoom);
            removeRobotFromOldRoom(oldRoom);
            setNewPosition(newPosition);
        }
        throw new NoDataFoundException("No found connection for current position to target room.");
    }

    @Override
    public Boolean isOccupied(XYPositionable requestedPosition) {
        return (getCurrentPosition().equals(requestedPosition));
    }

    private Roomable getCurrentRoom() {
        return this.position.getRoom();
    }

    private void setNewPosition(XYPositionable position) {
        this.position = position;
    }

    private void addRobotToNewRoom(Roomable newRoom) {
        newRoom.addOccupiedPosition(this);
    }

    private void removeRobotFromOldRoom(Roomable oldRoom) {
        oldRoom.removeOccupiedPosition(id);
    }

    private XYPositionable getCurrentPosition() {
        return this.position;
    }

    @Override
    public void debugPrintRobotStatus() {
        System.out.println("RobotID: " + id + " (" + name + ")");
        System.out.println("Position: ");
        if (position == null)
            System.out.println("\tNULL");
        else
            System.out.println("\t"+position.debugPositionToString());
        System.out.println("\n");
    }

    @Override
    public String debugOccupationStatusToString() {
        return "\tRobotID: " + id + " (" + name + ")\n"+
                "\tPosition: \n" +
                ((position == null) ? "\t\tNULL" : "\t\t"+position.debugPositionToString()+"\n");
    }
}

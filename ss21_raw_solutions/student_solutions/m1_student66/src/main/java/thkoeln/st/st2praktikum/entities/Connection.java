package thkoeln.st.st2praktikum.entities;


import thkoeln.st.st2praktikum.interfaces.HitAble;
import thkoeln.st.st2praktikum.interfaces.UseAble;

public class Connection extends AbstractEntity implements UseAble {

    private Room sourceRoom;
    private Room destinationRoom;
    private Cell sourceCell;
    private Cell destinationCell;

    public Connection(Room sourceRoom, Cell sourceCell, Room destinationRoom, Cell destinationCell) {
        this.sourceRoom = sourceRoom;
        this.sourceCell = sourceCell;
        this.destinationRoom = destinationRoom;
        this.destinationCell = destinationCell;
    }

    public Cell getDestinationCell() {
        return destinationCell;
    }

    public Cell getSourceCell() {
        return sourceCell;
    }

    public Room getDestinationRoom() {
        return destinationRoom;
    }

    public Room getSourceRoom() {
        return sourceRoom;
    }

    @Override
    public Boolean isUseable() {
        for(HitAble hitAble : destinationRoom.getHitAbles()){
            if(hitAble.isHitByMove(destinationCell, destinationCell))
                return false;
        }
        return true;
    }
}

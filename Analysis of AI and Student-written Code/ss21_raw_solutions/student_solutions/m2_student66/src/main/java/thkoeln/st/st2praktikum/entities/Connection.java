package thkoeln.st.st2praktikum.entities;


import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.interfaces.HitAble;
import thkoeln.st.st2praktikum.interfaces.UseAble;

public class Connection extends AbstractEntity implements UseAble {

    private Room sourceRoom;
    private Room destinationRoom;
    private Point sourcePoint;
    private Point destinationPoint;

    public Connection(Room sourceRoom, Point sourcePoint, Room destinationRoom, Point destinationPoint) {
        this.sourceRoom = sourceRoom;
        this.sourcePoint = sourcePoint;
        this.destinationRoom = destinationRoom;
        this.destinationPoint = destinationPoint;
    }

    public Point getDestinationPoint() {
        return destinationPoint;
    }

    public Point getSourcePoint() {
        return sourcePoint;
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
            if(hitAble.isHitByMove(destinationPoint, destinationPoint))
                return false;
        }
        return true;
    }
}

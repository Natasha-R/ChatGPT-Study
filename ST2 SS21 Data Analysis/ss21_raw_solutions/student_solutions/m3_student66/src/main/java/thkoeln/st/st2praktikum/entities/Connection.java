package thkoeln.st.st2praktikum.entities;

import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.interfaces.HitAble;
import thkoeln.st.st2praktikum.interfaces.UseAble;

import javax.persistence.*;

@Entity
public class Connection extends AbstractEntity implements UseAble {

    @ManyToOne
    private Room sourceRoom;
    @ManyToOne
    private Room destinationRoom;
    @Embedded
    private Point sourcePoint;
    @Embedded
    private Point destinationPoint;

    public Connection(Room sourceRoom, Point sourcePoint, Room destinationRoom, Point destinationPoint) {
        this.sourceRoom = sourceRoom;
        this.sourcePoint = sourcePoint;
        this.destinationRoom = destinationRoom;
        this.destinationPoint = destinationPoint;
    }

    protected Connection(){}

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

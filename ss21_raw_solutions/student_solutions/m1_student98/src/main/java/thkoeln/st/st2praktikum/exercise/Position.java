package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class Position implements XYPositionable {

    private final int xPos;
    private final int yPos;
    private final Roomable room;


    @Override
    public Integer getXPos() {
        return this.xPos;
    }

    @Override
    public Integer getYPos() {
        return this.yPos;
    }

    @Override
    public Roomable getRoom() {
        return this.room;
    }

    @Override
    public UUID getRoomID() {
        return this.room.getId();
    }

    @Override
    public String coordinatesToString() {
        return "("+xPos+","+yPos+")";
    }

    @Override
    public Boolean equals(XYPositionable newPosition) {
        if (xPos != newPosition.getXPos())
            return false;
        if (yPos != newPosition.getYPos())
            return false;
        if (room.getId() != newPosition.getRoomID())
            return false;
        return true;
    }

    @Override
    public XYPositionable clone(Optional<Integer> newXPos, Optional<Integer> newYPos, Optional<Roomable> newRoom) {
        int xPos = (newXPos.isPresent()) ? newXPos.get() : this.xPos;
        int yPos = (newYPos.isPresent()) ? newYPos.get() : this.yPos;
        Roomable room = (newRoom.isPresent()) ? newRoom.get() : this.room;

        return new Position(xPos, yPos, room);
    }

    @Override
    public String debugPositionToString() {
        return "("+xPos+","+yPos+") Room:"+room.getId();
    }
}

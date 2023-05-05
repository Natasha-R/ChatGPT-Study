package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Field extends Map{

    protected ArrayList<Barrier> listOfBarriers = new ArrayList<>();

    public Field (UUID fieldUUID, Integer height, Integer width){
        identifier = fieldUUID;
        this.height = height;
        this.width = width;
    }

    @Override
    public Connection getConnection(Vector2D sourcePosition) {
        for(Connection each : listOfConnections){
            if(each.checkSourcePosition(sourcePosition)) return each;
        }
        return null;
    }

    @Override
    public Integer checkNorthDirection(Integer xFromMovable, Integer yFromMovable, Integer steps) {
        for(int i = 1; i <= steps;i++){
            if(yFromMovable+i >= height)
                return i-1;
            else if(!isNotBlocked(xFromMovable, yFromMovable + i))
                return i-1;
            else if(checkBarrierNorth(xFromMovable, yFromMovable+i-1)) {
                return i-1;
            }
        }
        return steps;
    }

    @Override
    public Integer checkWestDirection(Integer xFromMovable, Integer yFromMovable, Integer steps) {
        for(int i = 1; i <= steps;i++){
            if(xFromMovable-i < 0)
                return i-1;
            else if(!isNotBlocked(xFromMovable-i, yFromMovable))
                return i-1;
            else if(checkBarrierWest(xFromMovable-i+1, yFromMovable))
                return i-1;
        }
        return steps;
    }

    @Override
    public Integer checkSouthDirection(Integer xFromMovable, Integer yFromMovable, Integer steps) {
        for(int i = 1; i <= steps;i++){
            if(yFromMovable-i < 0)
                return i-1;
            else if(!isNotBlocked(xFromMovable, yFromMovable-i))
                return i-1;
            else if(checkBarrierSouth(xFromMovable, yFromMovable-i+1)) {
                return i-1;
            }
        }
        return steps;
    }

    @Override
    public Integer checkEastDirection(Integer xFromMovable, Integer yFromMovable, Integer steps) {
        for(int i = 1; i <= steps;i++){
            if(xFromMovable+i >= width)
                return i-1;
            else if(!isNotBlocked(xFromMovable+i, yFromMovable))
                return i-1;
            else if(checkBarrierEast(xFromMovable+i-1, yFromMovable))
                return i-1;
        }
        return steps;
    }
    private boolean checkBarrierNorth(Integer xPosition, Integer yPosition){
        for(Barrier barrier : listOfBarriers){
            if(barrier.isHorizontal()){
                if(barrier.getStart().getY().equals(yPosition+1) && barrier.xIsBetween(xPosition)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkBarrierWest(Integer xPosition, Integer yPosition){
        for(Barrier barrier : listOfBarriers){
            if(barrier.isVertical()){
                if(barrier.getStart().getX().equals(xPosition) && barrier.yIsBetween(yPosition)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkBarrierSouth(Integer xPosition, Integer yPosition){
        for(Barrier barrier : listOfBarriers){
            if(barrier.isHorizontal()){
                if(barrier.getStart().getY().equals(yPosition) && barrier.xIsBetween(xPosition)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkBarrierEast(Integer xPosition, Integer yPosition){
        for(Barrier barrier : listOfBarriers){
            if(barrier.isVertical()){
                if(barrier.getStart().getX().equals(xPosition+1) && barrier.yIsBetween(yPosition)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isNotBlocked(Integer xPosition, Integer yPosition) {
        for(Blocking each: listOfBlockingObjects)
            if(each.isBlocked(new Vector2D(xPosition, yPosition)))return false;
        return true;
    }

    public void addBarrier(Barrier barrier){
        listOfBarriers.add(barrier);
    }

    @Override
    public boolean addBlockingObject(Blocking blockingObject, Vector2D position) {
        if(isNotBlocked(position.getX(), position.getY())) {
            listOfBlockingObjects.add(blockingObject);
            return true;
        }
        return false;
    }

    @Override
    public void removeBlocking(Blocking movableObject) {
        listOfBlockingObjects.remove(movableObject);
    }

}

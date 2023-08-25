package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Field extends Map{

    @ElementCollection( targetClass = Barrier.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Barrier> barriers = new ArrayList<>();

    public Field (UUID fieldUUID, Integer height, Integer width){
        id = fieldUUID;
        this.height = height;
        this.width = width;
    }

    @Override
    public Connection getConnection(Vector2D sourcePosition, UUID desiredMapId) {
        for(TransportTechnology transportTechnology : listOfTransportTechnologies){
            for(Connection connection : transportTechnology.getConnections())
                if(connection.checkSourcePosition(sourcePosition) && connection.getSourceMap().equals(id) && connection.checkTargetMap(desiredMapId)) return connection;
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
        for(Barrier barrier : barriers){
            if(Barrier.isHorizontal(barrier.getStart(), barrier.getEnd())){
                if(barrier.getStart().getY().equals(yPosition+1) && barrier.xIsBetween(xPosition)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkBarrierWest(Integer xPosition, Integer yPosition){
        for(Barrier barrier : barriers){
            if(Barrier.isVertical(barrier.getStart(), barrier.getEnd())){
                if(barrier.getStart().getX().equals(xPosition) && barrier.yIsBetween(yPosition)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkBarrierSouth(Integer xPosition, Integer yPosition){
        for(Barrier barrier : barriers){
            if(Barrier.isHorizontal(barrier.getStart(), barrier.getEnd())){
                if(barrier.getStart().getY().equals(yPosition) && barrier.xIsBetween(xPosition)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkBarrierEast(Integer xPosition, Integer yPosition){
        for(Barrier barrier : barriers){
            if(Barrier.isVertical(barrier.getStart(), barrier.getEnd())){
                if(barrier.getStart().getX().equals(xPosition+1) && barrier.yIsBetween(yPosition)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isNotBlocked(Integer xPosition, Integer yPosition) {
        for(Vector2D position: listOfBlockingObjects)
            if(position.getX()==xPosition && position.getY()==yPosition)return false;
        return true;
    }

    public void addBarrier(Barrier barrier){
        barriers.add(barrier);
    }


}

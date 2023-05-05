package thkoeln.st.st2praktikum.exercise.Move;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Room.MoveAllowed;

public class BasicMovement extends Movement{

    private Hitbox hitbox;

    // check if the move is allowed and create a new Hitbox based on the updated position
    public Coordinate move(OrderType direction, Integer steps, Coordinate coordinate, MoveAllowed room){
        this.hitbox = new Hitbox(coordinate);
        Coordinate bufferCoordinate;
        while(steps > 0){
            bufferCoordinate = moveDirection(direction, hitbox, room);
            hitbox = new Hitbox(bufferCoordinate);
            steps -= 1;
        }
        return hitbox.getBasePosition();
    }

    public Coordinate moveDirection(OrderType direction, Hitbox hitbox, MoveAllowed room){
        switch(direction){
            case NORTH:
                if(canGoNorth(hitbox, room)){
                    return hitbox.getNorthPosition();
                }
                break;
            case EAST:
                if(canGoEast(hitbox, room)){
                    return hitbox.getEastPosition();
                }
                break;
            case SOUTH:
                if(canGoSouth(hitbox, room)){
                    return hitbox.getSouthPosition();
                }
                break;
            case WEST:
                if(canGoWest(hitbox, room)){
                    return hitbox.getWestPosition();
                }
                break;
            default:
                throw new RuntimeException("This move is not allowed!");
        }
        return hitbox.getBasePosition();
    }

    public boolean canGoNorth(Hitbox hitbox, MoveAllowed room){
        if(room.obstacleCollision(hitbox.getNorthPosition(), hitbox.getNorthEastPosition())){
            return false;
        }
        return room.canIGoThere(hitbox.getNorthPosition());
    }

    public boolean canGoEast(Hitbox hitbox, MoveAllowed room){
        if(room.obstacleCollision(hitbox.getEastPosition(), hitbox.getNorthEastPosition())){
            return false;
        }
        return room.canIGoThere(hitbox.getEastPosition());
    }

    public boolean canGoSouth(Hitbox hitbox, MoveAllowed room){
        if(room.obstacleCollision(hitbox.getBasePosition(), hitbox.getEastPosition())) {
            return false;
        }
        return room.canIGoThere(hitbox.getSouthPosition());
    }

    public boolean canGoWest(Hitbox hitbox, MoveAllowed room){
        if(room.obstacleCollision(hitbox.getBasePosition(), hitbox.getNorthPosition())) {
            return false;
        }
        return room.canIGoThere(hitbox.getWestPosition());
    }

    public BasicMovement(){
        ;
    }
}

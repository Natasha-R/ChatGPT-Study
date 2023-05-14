package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TidyUpRobot extends thkoeln.st.st1praktikum.core.AbstractEntity implements Moveable {
    private String name;
    @Embedded
    private Vector2D currentPosition;
    @Getter
    private UUID currentRoom = null;
    private boolean isPlaced = false;

    public TidyUpRobot(String nameParam) {
        name = nameParam;
    }

    public Boolean commandDecider(Task task, HashMap<UUID, Room> roomFinder) {

        if (task.getTaskType() == TaskType.TRANSPORT) {
            return teleport(task, roomFinder.get(getRoomId()) ,roomFinder.get(task.getGridId()));
        } else if (task.getTaskType() == TaskType.ENTER) {
            return placeInRoom(roomFinder.get(task.getGridId()));
        } else return moveTo(task, roomFinder.get(getRoomId()));
    }

    @Override
    public Boolean placeInRoom(Room room) {
        if(isPlaced) {
            return false;
        }
        if (room.getCoordinate(0, 0).isHasRobot()) {
            return false;
        } else {
            this.setCurrentRoom(room.getId());
            this.setCurrentPosition(0, 0, room);
            isPlaced = true;
            return true;
        }
    }

    @Override
    public Boolean moveTo(Task task, Room room) {
        int stepsToDo = task.getNumberOfSteps();
        TaskType direction = task.getTaskType();
        switch (direction) {
            case NORTH:
                 return moveNorth(stepsToDo, room);
            case EAST:
                 return moveEast(stepsToDo, room);
            case SOUTH:
                return moveSouth(stepsToDo, room);
            case WEST:
                return moveWest(stepsToDo, room);
            default:
                return true;
        }
    }

    public Boolean moveNorth(int stepsToDo, Room room) {
        for (int i = 0; i < stepsToDo; i++) {
            if (currentPosition.getY() == room.getHeight()-1){
                return true;
            }
            if(room.getCoordinate(currentPosition.getX(), currentPosition.getY() + 1).isHasRobot()) {
                return true;
            } else if(!room.getCoordinate(currentPosition.getX(), currentPosition.getY() + 1).isHasHorizontalBarrier()) {
                setCurrentPosition(currentPosition.getX(), currentPosition.getY()+1, room);
            } else if(!room.getCoordinate(currentPosition.getX() + 1, currentPosition.getY() + 1).isHasHorizontalBarrier()) {
                setCurrentPosition(currentPosition.getX(), currentPosition.getY()+1, room);
            } else return true;
        }
        return true;
    }

    public Boolean moveEast(int stepsToDo, Room room) {
        for (int i = 0; i < stepsToDo; i++) {
            if (currentPosition.getX() == room.getWidth()-1){
                return true;
            }
            if(room.getCoordinate(currentPosition.getX() + 1, currentPosition.getY()).isHasRobot()) {
                return true;
            } else if(!room.getCoordinate(currentPosition.getX() + 1, currentPosition.getY()).isHasVerticalBarrier()) {
                setCurrentPosition(currentPosition.getX()+1, currentPosition.getY(), room);
            } else if(!room.getCoordinate(currentPosition.getX() + 1, currentPosition.getY() + 1).isHasVerticalBarrier()) {
                setCurrentPosition(currentPosition.getX()+1, currentPosition.getY(), room);
            } else return true;
        }
        return true;
    }

    public Boolean moveSouth(int stepsToDo, Room room) {
        if(!room.getCoordinate(currentPosition.getX(), currentPosition.getY()).isHasHorizontalBarrier() || !room.getCoordinate(currentPosition.getX() + 1, currentPosition.getY()).isHasHorizontalBarrier()) {
            for (int i = 0; i < stepsToDo; i++) {
                if(currentPosition.getY() == 0) {
                    return true;
                }
                if (room.getCoordinate(currentPosition.getX(), currentPosition.getY() - 1).isHasRobot()) {
                    return true;
                } else if(!room.getCoordinate(currentPosition.getX(), currentPosition.getY() - 1).isHasHorizontalBarrier()) {
                    setCurrentPosition(currentPosition.getX(), currentPosition.getY()-1, room);
                } else if(room.getCoordinate(currentPosition.getX(), currentPosition.getY() - 1).isHasHorizontalBarrier()) {
                    setCurrentPosition(currentPosition.getX(), currentPosition.getY()-1, room);
                    if(room.getCoordinate(currentPosition.getX() + 1, currentPosition.getY()).isHasHorizontalBarrier()) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public Boolean moveWest(int stepsToDo, Room room) {
        if(!room.getCoordinate(currentPosition.getX(), currentPosition.getY()).isHasVerticalBarrier() || !room.getCoordinate(currentPosition.getX(), currentPosition.getY()+1).isHasVerticalBarrier()) {
            for (int i = 0; i < stepsToDo; i++) {
                if(currentPosition.getX() == 0) {
                    return true;
                }
                if (room.getCoordinate(currentPosition.getX()-1, currentPosition.getY()).isHasRobot()) {
                    return true;
                } else if(!room.getCoordinate(currentPosition.getX()-1, currentPosition.getY()).isHasVerticalBarrier()) {
                    setCurrentPosition(currentPosition.getX()-1, currentPosition.getY(), room);
                } else if(room.getCoordinate(currentPosition.getX()-1, currentPosition.getY()).isHasVerticalBarrier()) {
                    setCurrentPosition(currentPosition.getX()-1, currentPosition.getY(), room);
                    if(room.getCoordinate(currentPosition.getX(), currentPosition.getY()+1).isHasVerticalBarrier()) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Boolean teleport(Task task, Room currentRoom, Room travelToRoom) {
        if(currentRoom.getCoordinate(currentPosition.getX(), currentPosition.getY()).isHasConnection()) {
            this.setCurrentRoom(travelToRoom.getId());
            this.setCurrentPosition(currentRoom.getCoordinate(currentPosition.getX(), currentPosition.getY()).getConnectionCoordinate().getX(), currentRoom.getCoordinate(currentPosition.getX(), currentPosition.getY()).getConnectionCoordinate().getY(), currentRoom);
            changeFloorTileHasRobot(travelToRoom);
            return true;
        } else return false;
    }

    public void changeFloorTileHasRobot(Room room) {
        room.getCoordinate(currentPosition.getX(), currentPosition.getY()).setHasRobot();
    }


    public UUID getRoomId() {
        return currentRoom;
    }

    public void setCurrentRoom(UUID room) {
        currentRoom = room;
    }

    public void setCurrentPosition(int x, int y, Room room) {
        if(isPlaced){
            changeFloorTileHasRobot(room);
        }
        currentPosition = new Vector2D(x, y);
        if(this.getRoomId() == room.getId()) {
            changeFloorTileHasRobot(room);
        }
    }

    public Vector2D getVector2D() {
        return currentPosition;
    }



}


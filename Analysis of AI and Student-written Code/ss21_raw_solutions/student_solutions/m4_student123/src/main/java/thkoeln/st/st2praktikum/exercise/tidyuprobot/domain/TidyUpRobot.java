package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
public class TidyUpRobot {

    @Id
    private final UUID tidyUpRobotID;
    private String name;
    private UUID roomId;

    @Embedded
    private Vector2D coordinates;

    @ElementCollection
    private List<Task> taskHistory;

    protected TidyUpRobot(){
        this.tidyUpRobotID = UUID.randomUUID();
    }

    public TidyUpRobot( String name){
        this.tidyUpRobotID = UUID.randomUUID();
        this.name = name;
        this.coordinates = new Vector2D(0,0);
        this.taskHistory = new ArrayList<>();
    }

    /**
     * This method is responsible for the traverse/teleport of a Tidy Up Robot
     * @param connection Connection which is used to Transport
     * @return if the traversing of the Tidy Up Robot to the desired Room was successful
     */
    public Boolean traverseRobot(Optional<Connection> connection) {
        if(connection.isPresent()){
            traverseToOtherRoom(connection.get().getDestinationCoordinate(),connection.get().getDestinationRoomId());
            return true;
        }
        else return false;
    }
    /**
     * This method is responsible for the placing of a tidy up robot in a room
     * @param tidyUpRobotRepository repo of all tidy up robots
     * @param destinationRoomID the ID of the room where the robot should be placed
     * @return if the placing of the robot was possible or not
     */
    public Boolean placeRobot(TidyUpRobotRepository tidyUpRobotRepository, UUID destinationRoomID) {
        for (TidyUpRobot tidyUpRobot : tidyUpRobotRepository.findAll()) {
            if (tidyUpRobot.getRoomId() != null) {
                if (tidyUpRobot.getRoomId().compareTo(destinationRoomID) == 0 &&
                        tidyUpRobot.getVector2D().equals( new Vector2D(0,0))) {
                    return false;
                }
            }
        }

        this.placeInRoom(destinationRoomID);
        return true;
    }

    public boolean moveNorth(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderNorth() && this.getY() +1 < room.getRoomHeight() ){
                this.increaseY();
            }else break;
        }
        return true;
    }

    public boolean moveSouth(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderSouth() && this.getY() - 1 >=0) {
                this.decreaseY();
            } else break;
        }
        return true;
    }

    public boolean moveEast(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderEast() && this.getX() + 1 < room.getRoomWidth()) {
                this.increaseX();
            } else break;
        }
        return true;
    }

    public boolean moveWest(Room room, int moveAmount){
        for(int i = moveAmount; i > 0; i--){
            if(!room.getRoomCell(this.getX(),this.getY()).isBorderWest() && this.getX() - 1 >= 0) {
                this.decreaseX();
            } else break;
        }
        return true;
    }

    public String getCoordinateString(){ return this.coordinates.toString(); }
    public int getX(){ return this.coordinates.getX();}
    public int getY(){ return this.coordinates.getY();}

    public void setCoordinates(int x, int y){ this.coordinates = new Vector2D(x,y);}
    public void setCoordinates(Vector2D newCoordinate ) { this.coordinates = newCoordinate;}
    public Vector2D getVector2D() { return coordinates; }
    public void increaseX(){ this.coordinates= new Vector2D(this.coordinates.getX()+1, this.coordinates.getY()); }
    public void increaseY(){ this.coordinates= new Vector2D(this.coordinates.getX(), this.coordinates.getY()+1); }
    public void decreaseX(){ this.coordinates= new Vector2D(this.coordinates.getX()-1, this.coordinates.getY()); }
    public void decreaseY(){ this.coordinates= new Vector2D(this.coordinates.getX(), this.coordinates.getY()-1); }

    public UUID getTidyUpRobotID() { return this.tidyUpRobotID; }
    public String getRobotName() { return this.name; }
    public void setRobotName( String name) { this.name = name; }

    public UUID getRoomId(){ return this.roomId;}
    public void placeInRoom( UUID roomID) {this.roomId = roomID;}

    public void traverseToOtherRoom( int x, int y, UUID roomID) {
        this.setCoordinates(x, y);
        this.roomId = roomID;
    }

    public void traverseToOtherRoom(Vector2D newCoordinate, UUID roomID) {
        this.setCoordinates(newCoordinate);
        this.roomId = roomID;
    }
    public void addTaskToHistory(Task task){
        this.taskHistory.add(task);
    }

    public void deleteTasks() {
        this.taskHistory.clear();
    }
}

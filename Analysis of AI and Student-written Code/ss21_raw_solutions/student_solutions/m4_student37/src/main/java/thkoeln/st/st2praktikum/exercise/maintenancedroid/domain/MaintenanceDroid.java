package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceDroid {

    @Id
    public UUID uuid;

    private UUID spaceuuid;

    @Embedded
    private Vector2D Position;
    private String name;

    public MaintenanceDroid(String pname) {
        uuid= UUID.randomUUID();
        name = pname;
    }

    public UUID getId(){ return uuid;  }

    public UUID getSpaceShipDeckId(){ return spaceuuid; }

    public void enable(UUID spaceid){
        Position = new Vector2D(0,0);
        spaceuuid = spaceid;
    }

    public boolean transport(SpaceShipDeck sp, UUID spaceid) {
        boolean result=false;
        for(Connector con: sp.getConnectors()){
            if(con.getLocation1().equals(Position)){
                if(con.getSpacedestination().equals(spaceid)){
                    spaceuuid=spaceid;
                    Position=con.getLocation2();
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean move(Task task, SpaceShipDeck sp) {
        boolean stop = false;
        for(int i=0;i<task.getNumberOfSteps();i++){
            switch (task.getTaskType()){
                case NORTH:
                    for (Obstacle obstacle:sp.getObstacles()) {
                        if(Position.getY()==sp.getheight()||obstacle.sameX(Position.getX())&&obstacle.sameY(Position.getY()+1)&&!obstacle.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getObstacles().size()==0&&Position.getY()==sp.getheight()){
                        stop=true;
                    }
                    if(!stop) Position.updateY(1);
                    break;
                case EAST:
                    for (Obstacle obstacle:sp.getObstacles()) {
                        if(Position.getX()==sp.getwidth()||obstacle.sameX(Position.getX()+1)&&obstacle.sameY(Position.getY())&&obstacle.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getObstacles().size()==0&&Position.getX()==sp.getwidth()){
                        stop=true;
                    }
                    if(!stop) Position.updateX(1);
                    break;
                case SOUTH:
                    for (Obstacle obstacle:sp.getObstacles()) {
                        if(obstacle.sameX(Position.getX())&&obstacle.sameY(Position.getY())&&!obstacle.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getObstacles()==null||Position.getY()==0){
                        stop=true;
                    }
                    if(!stop) Position.updateY(-1);
                    break;
                case WEST:
                    for (Obstacle obstacle:sp.getObstacles()) {
                        if(obstacle.sameX(Position.getX())&&obstacle.sameY(Position.getY())&&obstacle.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getObstacles()==null||Position.getX()==0){
                        stop=true;
                    }
                    if(!stop) Position.updateX(-1);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return true;
    }

    public Vector2D getVector2D() {
        return Position;
    }
    public void setPoint(Vector2D point) {
        Position = point;
    }


}
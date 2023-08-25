package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceDroid {
    @Id
    private UUID droidId;

    private UUID spaceShipId;
    @Embedded
    private Vector2D Position;
    private String Devicename;

    public MaintenanceDroid(String name) {
        droidId = UUID.randomUUID();
        Devicename = name;
    }

    public UUID getDroidId() {
        return droidId;
    }
    public UUID getSpaceShipDeckId(){
        return spaceShipId;
    }

    public Vector2D getVector2D(){
        return Position;
    }

    public void enable(UUID spaceid){
        Position = new Vector2D(0,0);
        spaceShipId = spaceid;
    }

    public boolean move(Task task, SpaceShipDeck sp) {
        boolean stop = false;
        Integer steps= task.getNumberOfSteps();
        for (int i=0; i<steps;i++) {
            switch (task.getTaskType()) {
                case NORTH:
                    for (Obstacle obstacle: sp.getObstacles()) {
                        if (Position.getY()==sp.getheight()||obstacle.sameX(Position.getX())&&obstacle.sameY(Position.getY()+1)&&!obstacle.isVertical()) {
                            stop=true;
                        }
                    }
                    if (sp.getObstacles().size()==0&&Position.getX()== sp.getheight()){
                        stop=true;
                    }
                    if (!stop) Position.updateY(1);
                    break;
                case EAST:
                    for (Obstacle obstacle: sp.getObstacles()) {
                        if (Position.getX()==sp.getwidth()||obstacle.sameX(Position.getX()+1)&&obstacle.sameY(Position.getY())&&obstacle.isVertical()){
                            stop=true;
                        }
                    }
                    if (sp.getObstacles().size()==0&&Position.getX()== sp.getwidth()){
                        stop=true;
                    }
                    if (!stop) Position.updateX(1);
                    break;
                case SOUTH:
                    for (Obstacle obstacle: sp.getObstacles()) {
                        if (Position.getY()==0||(obstacle.sameX(Position.getX())&&obstacle.sameY(Position.getY())&&!obstacle.isVertical())){
                            stop=true;
                        }
                    }
                    if (sp.getObstacles()==null&&Position.getY()==0){
                        stop=true;
                    }
                    if (!stop) Position.updateY(-1);
                    break;
                case WEST:
                    for (Obstacle obstacle: sp.getObstacles()) {
                        if (Position.getX()==0||(obstacle.sameX(Position.getX())&&obstacle.sameY(Position.getY())&&obstacle.isVertical())){
                            stop=true;
                        }
                    }
                    if (sp.getObstacles().size()==0&&Position.getX()==0){
                        stop=true;
                    }
                    if (!stop) Position.updateX(-1);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return true;
    }

    public String getposition() {
        return "("+Position.getX()+","+Position.getY()+")";
    }

    public boolean transport(SpaceShipDeck sp, UUID gridID) {
        boolean result=false;
        for(Connector con: sp.getConnectors()){
            if(con.getLocation1().getX()==Position.getX()&&con.getLocation1().getY()==Position.getY()){
                if(con.getSpacedestination().equals(gridID)){
                    spaceShipId =gridID;
                    Position=con.getLocation2();
                    result = true;
                }
            }
        }
        return result;
    }
}
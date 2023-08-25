package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;


import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CleaningDevice {

    @Id
    public UUID uuid;

    private UUID spaceuuid;

    @Embedded
    private Point Position;
    private String name;

    public CleaningDevice(String pname) {
        uuid= UUID.randomUUID();
        name = pname;
    }

    public UUID getId(){ return uuid;  }

    public UUID getSpaceId(){ return spaceuuid; }

    public void enable(UUID spaceid){
        Position = new Point(0,0);
        spaceuuid = spaceid;
    }

    public boolean transport(Space sp, UUID spaceid) {
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

    public boolean move(Command command, Space sp) {
        boolean stop = false;
        for(int i=0;i<command.getNumberOfSteps();i++){
            switch (command.getCommandType()){
                case NORTH:
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getY()==sp.getheight()||wall.sameX(Position.getX())&&wall.sameY(Position.getY()+1)&&!wall.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getY()==sp.getheight()){
                        stop=true;
                    }
                    if(!stop) Position.updateY(1);
                    break;
                case EAST:
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getX()==sp.getwidth()||wall.sameX(Position.getX()+1)&&wall.sameY(Position.getY())&&wall.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getX()==sp.getwidth()){
                        stop=true;
                    }
                    if(!stop) Position.updateX(1);
                    break;
                case SOUTH:
                    for (Wall wall:sp.getWalls()) {
                        if(wall.sameX(Position.getX())&&wall.sameY(Position.getY())&&!wall.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls()==null||Position.getY()==0){
                        stop=true;
                    }
                    if(!stop) Position.updateY(-1);
                    break;
                case WEST:
                    for (Wall wall:sp.getWalls()) {
                        if(wall.sameX(Position.getX())&&wall.sameY(Position.getY())&&wall.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls()==null||Position.getX()==0){
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

    public Point getPoint() {
        return Position;
    }
    public void setPoint(Point point) {
        Position = point;
    }

}

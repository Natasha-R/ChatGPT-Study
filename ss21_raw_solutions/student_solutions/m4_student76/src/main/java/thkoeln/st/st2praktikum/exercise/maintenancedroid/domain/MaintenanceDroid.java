package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MaintenanceDroid extends AbstractEntity implements Moveable {


    private String name;

    @Embedded
    private Point point;

    private UUID spaceShipDeckId;

    @OneToOne
    private SpaceShipDeck currentSpaceShipDeck;

    @ElementCollection(targetClass = Command.class)
    private List<Command> commands = new ArrayList<Command>();

    public Point getPoint(){
        return this.point;
    }
    public UUID getSpaceId(){
        return this.spaceShipDeckId;
    }

    public void deleteCommands(){
        commands.clear();
    }
    public void addCommand(Command command){
        commands.add(command);
    }


    public void setXY(Integer x, Integer y){
        Point point = new Point(x,y);
        this.point = point;
    }

    @Override
    public void moveNorth(int command) {
        int yAfterCommand = getPoint().getY()+command;
        Integer yOfAnOtherDevice = null;

        for (int i = 0; i<getCurrentSpaceShipDeck().getMaintenanceDroids().size(); i++){
            Point point = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint();

            if (point.getX() == getPoint().getX() && getPoint().getY() < point.getY()) {
                yOfAnOtherDevice = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint().getY(); break;
            }
        }

        if(yAfterCommand> currentSpaceShipDeck.getHeight()) yAfterCommand = currentSpaceShipDeck.getHeight();

        if (currentSpaceShipDeck.getBarriers().isEmpty()){
            if(yOfAnOtherDevice != null && yAfterCommand >= yOfAnOtherDevice) yAfterCommand=yOfAnOtherDevice-1;
        }else {
            for (int i = 0; i < currentSpaceShipDeck.getBarriers().size(); i++) {
                Barrier barrier = currentSpaceShipDeck.getBarriers().get(i);

                if (barrier.getStart().getY() == barrier.getEnd().getY()) {

                    if (yOfAnOtherDevice != null && yAfterCommand >= yOfAnOtherDevice) yAfterCommand = yOfAnOtherDevice - 1;
                    else if (getPoint().getX() >= barrier.getStart().getX() && getPoint().getX() < barrier.getEnd().getX()) {
                        if (yAfterCommand >= barrier.getStart().getY()) yAfterCommand = barrier.getStart().getY() - 1;
                    }
                }
            }
        }
        setXY(getPoint().getX(),yAfterCommand);
    }

    @Override
    public void moveSouth(int command) {
        int y1 = getPoint().getY()-command;
        Integer y2 = null;

        for (int i = 0; i<getCurrentSpaceShipDeck().getMaintenanceDroids().size(); i++){
            Point point = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint();
            if ((point.getX()) == getPoint().getX() && getPoint().getY() > point.getY()) {
                y2 = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint().getY();
                break;
            }
        }

        if(y1< 0) y1 = 0;
        if (currentSpaceShipDeck.getBarriers().isEmpty()){
            if(y2 != null && y1 <= y2) y1=y2+1;
        }

        for (int i=0; i<currentSpaceShipDeck.getBarriers().size();i++){
            Barrier barrier = currentSpaceShipDeck.getBarriers().get(i);

            if(barrier.getStart().getY() == barrier.getEnd().getY()){
                if(y2 != null && y1<=y2 && y2>barrier.getStart().getY()) y1=y2+1;
                else if (getPoint().getX()<=barrier.getEnd().getX() && getPoint().getX()>barrier.getEnd().getX()){
                    if (y1<=barrier.getStart().getY()) y1=barrier.getStart().getY()+1;
                }
            }
        }
        setXY(getPoint().getX(),y1);
    }

    @Override
    public void moveWest(int command) {
        int x1 = getPoint().getX()-command;
        Integer x2 = null;

        for (int i = 0; i<getCurrentSpaceShipDeck().getMaintenanceDroids().size(); i++){
                Point point = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint();
            if (point.getY() == getPoint().getY() && getPoint().getX() > point.getX()) {
                x2 = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint().getX();
                break;
            }
        }

        if(x1< 0) x1 = 0;
        if (currentSpaceShipDeck.getBarriers().isEmpty()){
            if(x2 != null && x1 <= x2) x1=x2+1;
        }

        for (int i=0; i<currentSpaceShipDeck.getBarriers().size();i++){
            Barrier barrier = currentSpaceShipDeck.getBarriers().get(i);

            if(barrier.getStart().getX() == barrier.getEnd().getX()){
                if(x2 != null && x1<=x2 && x2>barrier.getStart().getX()) x1=x2+1;
                else if (getPoint().getY()>=barrier.getStart().getY() && getPoint().getY()<barrier.getEnd().getY()){
                    if (x1<=barrier.getStart().getX()) x1=barrier.getStart().getX()+1;
                }
            }
        }
        setXY(x1,getPoint().getY());
    }

    @Override
    public void moveEast(int command) {
        int x1 = getPoint().getX()+command;
        Integer x2 = null;

        for (int i = 0; i<getCurrentSpaceShipDeck().getMaintenanceDroids().size(); i++){
            Point point = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint();
            if (point.getY() == getPoint().getY() && getPoint().getX() < point.getX()) {
                x2 = getCurrentSpaceShipDeck().getMaintenanceDroids().get(i).getPoint().getX();
                break;
            }
        }

        if(x1> currentSpaceShipDeck.getWidth()) x1 = currentSpaceShipDeck.getWidth();

        if (currentSpaceShipDeck.getBarriers().isEmpty()){
            if(x2 != null && x1 >= x2) x1=x2-1;
        }else {
            for (int i=0; i<currentSpaceShipDeck.getBarriers().size();i++){
                Barrier barrier = currentSpaceShipDeck.getBarriers().get(i);

                if(barrier.getStart().getX() == barrier.getEnd().getX()){
                    if(x2 != null && x1>=x2 && x2<barrier.getStart().getX()) x1=x2-1;
                    else if (getPoint().getY()<=barrier.getStart().getY() && getPoint().getY()>barrier.getEnd().getY()){
                        if (x1>=barrier.getStart().getX()) x1=barrier.getStart().getX()-1;
                    }
                }
            }
        }
        setXY(x1,this.getPoint().getY());
    }
}

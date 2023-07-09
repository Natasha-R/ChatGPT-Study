package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import javax.persistence.*;
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
    protected Point point;

    protected UUID currentSpaceShipDeckUUID;

    @OneToOne
    protected SpaceShipDeck currentSpaceShipDeck;

    @ElementCollection(targetClass = Command.class)
    private List<Command> commands = new ArrayList<>();

    public Point getPoint(){ return this.point; }

    public UUID getSpaceShipDeckId(){return this.currentSpaceShipDeckUUID;}


    protected void setXY(int x, int y){
        this.point = new Point(x,y);
    }

    @Override
    public void moveNorth(int command) {
        int y1 = getPoint().getY()+command;
        Integer y0 = null; // y Coordinate of an other Cleaning Device if it's on the same way.

        for (int i=0; i<getCurrentSpaceShipDeck().maintenanceDroids.size(); i++){
            Point point = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint();

            if (point.getX() == getPoint().getX() && getPoint().getY() < point.getY()) {
                y0 = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint().getY(); break;
            }
        }

        if(y1> currentSpaceShipDeck.getHeight()) y1 = currentSpaceShipDeck.getHeight();

        if (currentSpaceShipDeck.barriers.isEmpty()){
            if(y0 != null && y1 >= y0) y1=y0-1;
        }else {
            for (int i = 0; i < currentSpaceShipDeck.barriers.size(); i++) {
                Barrier barrier = currentSpaceShipDeck.barriers.get(i);

                if (barrier.getStart().getY() == barrier.getEnd().getY()) {

                    if (y0 != null && y1 >= y0) y1 = y0 - 1;
                    else if (getPoint().getX() >= barrier.getStart().getX() && getPoint().getX() < barrier.getEnd().getX()) {
                        if (y1 >= barrier.getStart().getY()) y1 = barrier.getStart().getY() - 1;
                    }
                }
            }
        }
        setXY(getPoint().getX(),y1);
    }


    @Override
    public void moveSouth(int command) {
        int y1 = getPoint().getY()-command;
        Integer y2 = null;

        for (int i=0; i<getCurrentSpaceShipDeck().maintenanceDroids.size(); i++){
            Point point = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint();
            if ((point.getX()) == getPoint().getX() && getPoint().getY() > point.getY()) {
                y2 = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint().getY();
                break;
            }
        }

        if(y1< 0) y1 = 0;
        if (currentSpaceShipDeck.barriers.isEmpty()){
            if(y2 != null && y1 <= y2) y1=y2+1;
        }

        for (int i=0; i<currentSpaceShipDeck.barriers.size();i++){
            Barrier barrier = currentSpaceShipDeck.barriers.get(i);

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

        for (int i=0; i<getCurrentSpaceShipDeck().maintenanceDroids.size(); i++){
            Point point = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint();
            if (point.getY() == getPoint().getY() && getPoint().getX() > point.getX()) {
                x2 = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint().getX();
                break;
            }
        }

        if(x1< 0) x1 = 0;
        if (currentSpaceShipDeck.barriers.isEmpty()){
            if(x2 != null && x1 <= x2) x1=x2+1;
        }

        for (int i=0; i<currentSpaceShipDeck.barriers.size();i++){
            Barrier barrier = currentSpaceShipDeck.barriers.get(i);

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

        // fetch the y Coordinate if there is an other Cleaning Device on the same way that the current Cleaning Device should to take.
        for (int i=0; i<getCurrentSpaceShipDeck().maintenanceDroids.size(); i++){
            Point point = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint();
            if (point.getY() == getPoint().getY() && getPoint().getX() < point.getX()) {
                x2 = getCurrentSpaceShipDeck().maintenanceDroids.get(i).getPoint().getX();
                break;
            }
        }

        if(x1> currentSpaceShipDeck.getWidth()) x1 = currentSpaceShipDeck.getWidth();

        // if the Cleaning Device hit an other Cleaning Device.
        if (currentSpaceShipDeck.barriers.isEmpty()){
            if(x2 != null && x1 >= x2) x1=x2-1;
        }else {
            for (int i=0; i<currentSpaceShipDeck.barriers.size();i++){
                Barrier barrier = currentSpaceShipDeck.barriers.get(i);

                // check if there is an Horizontal barrier.
                if(barrier.getStart().getX() == barrier.getEnd().getX()){

                    // if the Cleaning Device hit an other one before the Barrier.
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

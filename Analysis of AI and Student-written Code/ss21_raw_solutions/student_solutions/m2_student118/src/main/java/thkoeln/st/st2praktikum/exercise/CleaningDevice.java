package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.UUID;

@Getter
@Setter
public class CleaningDevice extends AbstractEntity implements Moveable {


    private String name;
    private Coordinate coordinate;

    private UUID currentSpaceUUID;
    private Space currentSpace;


    protected void setXY(Integer x, Integer y){
        Coordinate coordinate = new Coordinate(x,y);
        this.coordinate = coordinate;
    }


    @Override
    public void moveNorth(int command) {
        int yAfterCommand = getCoordinate().getY()+command;
        Integer yOfAnOtherDevice = null; // y Coordinate of an other Cleaning Device if it's on the same way.

        for (int i=0; i<getCurrentSpace().cleaningDevices.size(); i++){
            Coordinate coordinate = getCurrentSpace().cleaningDevices.get(i).getCoordinate();

            if (coordinate.getX() == getCoordinate().getX() && getCoordinate().getY() < coordinate.getY()) {
                yOfAnOtherDevice = getCurrentSpace().cleaningDevices.get(i).getCoordinate().getY(); break;
            }
        }

        if(yAfterCommand> currentSpace.getHeight()) yAfterCommand = currentSpace.getHeight();

        if (currentSpace.barrierrs.isEmpty()){
            if(yOfAnOtherDevice != null && yAfterCommand >= yOfAnOtherDevice) yAfterCommand=yOfAnOtherDevice-1;
        }else {
            for (int i = 0; i < currentSpace.barrierrs.size(); i++) {
                Barrier barrier = currentSpace.barrierrs.get(i);

                if (barrier.getStart().getY() == barrier.getEnd().getY()) {

                    if (yOfAnOtherDevice != null && yAfterCommand >= yOfAnOtherDevice) yAfterCommand = yOfAnOtherDevice - 1;
                    else if (getCoordinate().getX() >= barrier.getStart().getX() && getCoordinate().getX() < barrier.getEnd().getX()) {
                        if (yAfterCommand >= barrier.getStart().getY()) yAfterCommand = barrier.getStart().getY() - 1;
                    }
                }
            }
        }
        setXY(getCoordinate().getX(),yAfterCommand);
    }

    @Override
    public void moveSouth(int command) {
        int y1 = getCoordinate().getY()-command;
        Integer y2 = null;

        for (int i=0; i<getCurrentSpace().cleaningDevices.size(); i++){
            Coordinate coordinate = getCurrentSpace().cleaningDevices.get(i).getCoordinate();
            if ((coordinate.getX()) == getCoordinate().getX() && getCoordinate().getY() > coordinate.getY()) {
                y2 = getCurrentSpace().cleaningDevices.get(i).getCoordinate().getY();
                break;
            }
        }

        if(y1< 0) y1 = 0;
        if (currentSpace.barrierrs.isEmpty()){
            if(y2 != null && y1 <= y2) y1=y2+1;
        }

        for (int i=0; i<currentSpace.barrierrs.size();i++){
            Barrier barrier = currentSpace.barrierrs.get(i);

            if(barrier.getStart().getY() == barrier.getEnd().getY()){
                if(y2 != null && y1<=y2 && y2>barrier.getStart().getY()) y1=y2+1;
                else if (getCoordinate().getX()<=barrier.getEnd().getX() && getCoordinate().getX()>barrier.getEnd().getX()){
                    if (y1<=barrier.getStart().getY()) y1=barrier.getStart().getY()+1;
                }
            }
        }
        setXY(getCoordinate().getX(),y1);
    }

    @Override
    public void moveWest(int command) {
        int x1 = getCoordinate().getX()-command;
        Integer x2 = null;

        for (int i=0; i<getCurrentSpace().cleaningDevices.size(); i++){
            Coordinate coordinate = getCurrentSpace().cleaningDevices.get(i).getCoordinate();
            if (coordinate.getY() == getCoordinate().getY() && getCoordinate().getX() > coordinate.getX()) {
                x2 = getCurrentSpace().cleaningDevices.get(i).getCoordinate().getX();
                break;
            }
        }

        if(x1< 0) x1 = 0;
        if (currentSpace.barrierrs.isEmpty()){
            if(x2 != null && x1 <= x2) x1=x2+1;
        }

        for (int i=0; i<currentSpace.barrierrs.size();i++){
            Barrier barrier = currentSpace.barrierrs.get(i);

            if(barrier.getStart().getX() == barrier.getEnd().getX()){
                if(x2 != null && x1<=x2 && x2>barrier.getStart().getX()) x1=x2+1;
                else if (getCoordinate().getY()>=barrier.getStart().getY() && getCoordinate().getY()<barrier.getEnd().getY()){
                    if (x1<=barrier.getStart().getX()) x1=barrier.getStart().getX()+1;
                }
            }
        }
        setXY(x1,getCoordinate().getY());
    }

    @Override
    public void moveEast(int command) {
        int x1 = getCoordinate().getX()+command;
        Integer x2 = null;

        // fetch the y Coordinate if there is an other Cleaning Device on the same way that the current Cleaning Device should to take.
        for (int i=0; i<getCurrentSpace().cleaningDevices.size(); i++){
            Coordinate coordinate = getCurrentSpace().cleaningDevices.get(i).getCoordinate();
            if (coordinate.getY() == getCoordinate().getY() && getCoordinate().getX() < coordinate.getX()) {
                x2 = getCurrentSpace().cleaningDevices.get(i).getCoordinate().getX();
                break;
            }
        }

        if(x1> currentSpace.getWidth()) x1 = currentSpace.getWidth();

        // if the Cleaning Device hit an other Cleaning Device.
        if (currentSpace.barrierrs.isEmpty()){
            if(x2 != null && x1 >= x2) x1=x2-1;
        }else {
            for (int i=0; i<currentSpace.barrierrs.size();i++){
                Barrier barrier = currentSpace.barrierrs.get(i);

                // check if there is an Horizontal barrier.
                if(barrier.getStart().getX() == barrier.getEnd().getX()){

                    // if the Cleaning Device hit an other one before the Barrier.
                    if(x2 != null && x1>=x2 && x2<barrier.getStart().getX()) x1=x2-1;
                    else if (getCoordinate().getY()<=barrier.getStart().getY() && getCoordinate().getY()>barrier.getEnd().getY()){
                        if (x1>=barrier.getStart().getX()) x1=barrier.getStart().getX()-1;
                    }
                }
            }
        }
        setXY(x1,this.getCoordinate().getY());
    }
}

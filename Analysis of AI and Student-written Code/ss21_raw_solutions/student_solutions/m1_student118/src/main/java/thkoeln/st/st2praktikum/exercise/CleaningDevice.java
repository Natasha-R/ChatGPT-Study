package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;

import java.util.UUID;

@Getter
@Setter
public class CleaningDevice extends AbstractEntity implements Moveable{


    private String name;
    protected int x=0;
    protected int y=0;

    protected UUID currentSpaceUUID;
    protected Space currentSpace;

    protected String coordinate = "("+x+","+y+")";

    protected void setXY(int x, int y){
        this.x=x;
        this.y=y;
    }


    @Override
    public void moveNorth(int command) {

        int newY_coordinate = getY();

        if (currentSpace.barrierrs.isEmpty()){
            if (newY_coordinate+command>currentSpace.getHeight()) setY(currentSpace.getHeight());
            else {
                newY_coordinate+=command;
                setY(newY_coordinate);
                coordinate = "(" + getX() + "," + getY() + ")";
            }
        }else {
            for (int i = 0; i < currentSpace.barrierrs.size(); i++) {

                // check if there is an Horizontal barrier.
                if (currentSpace.barrierrs.get(i).charAt(3) == currentSpace.barrierrs.get(i).charAt(9)) {

                    // get the details from the barrier String.
                    int barrierBegin = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(1)));
                    int barrierEnd = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(7)));
                    int barrierPosition = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(3)));

                    // check if the cleaning device below the barrier.
                    if ((getX() >= barrierBegin && getX() < barrierEnd) && getY() < barrierPosition) {

                        // if the cleaning device hit the barrier.
                        if (newY_coordinate + command > barrierPosition - 1){
                            setY(barrierPosition - 1);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }

                        // if the cleaning device doesn't hit the barrier.
                        else {
                            newY_coordinate += command;
                            setY(newY_coordinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }

                    // if the cleaning device above the barrier.
                    else {
                        if (newY_coordinate+command>currentSpace.getHeight()) setY(currentSpace.getHeight());
                        else {
                            newY_coordinate+=command;
                            setY(newY_coordinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }
                }

                // if the space doesn't contains Horizontal barriers
                else {
                    if (newY_coordinate+command>currentSpace.getHeight()) setY(currentSpace.getHeight());
                    else {
                        newY_coordinate+=command;
                        setY(newY_coordinate);
                        coordinate = "(" + getX() + "," + getY() + ")";
                    }
                }

                break;

            }
        }

    }

    @Override
    public void moveSouth(int command) {

        int newY_Coodinate = getY();

        if (currentSpace.barrierrs.isEmpty()){
            if (newY_Coodinate-command<0) setY(0);
            else {
                newY_Coodinate-=command;
                setX(newY_Coodinate);
                coordinate = "(" + getX() + "," + getY() + ")";
            }

        }else {
            for (int i = 0; i < currentSpace.barrierrs.size(); i++) {

                // check if there is an Horizontal barrier.
                if (currentSpace.barrierrs.get(i).charAt(3) == currentSpace.barrierrs.get(i).charAt(9)) {

                    // get the details from the barrier String.
                    int barrierBegin = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(1)));
                    int barrierEnd = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(7)));
                    int barrierPosition = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(3)));

                    // check if the cleaning device above the barrier.
                    if ((getX() > barrierBegin && getX() <= barrierEnd ) && getY() > barrierPosition) {
                        if (newY_Coodinate-command > barrierPosition) {
                            setY(barrierPosition);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }

                        // if the cleaning device doesn't hit the barrier.
                        else {
                            newY_Coodinate -= command;
                            setX(newY_Coodinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }

                    // if the cleaning device below the barrier.
                    else {
                        if (newY_Coodinate - command < 0) setY(0);
                        else {
                            newY_Coodinate -= command;
                            setX(newY_Coodinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }
                }

                // if the space doesn't contains Horizontal barriers
                else {
                    if (newY_Coodinate - command < 0) setY(0);
                    else {
                        newY_Coodinate -= command;
                        setX(newY_Coodinate);
                        coordinate = "(" + getX() + "," + getY() + ")";
                    }
                }
                break;
            }
        }

    }

    @Override
    public void moveWest(int command) {

        int newX_Coordinate = getX();

        if (currentSpace.barrierrs.isEmpty()){
            if (newX_Coordinate-command<0) setX(0);
            else {

                newX_Coordinate-=command;
                setX(newX_Coordinate);
                coordinate = "(" + getX() + "," + getY() + ")";
            }

        }else {
            for (int i = 0; i < currentSpace.barrierrs.size(); i++) {

                // check if there is a Vertical barrier.
                if (currentSpace.barrierrs.get(i).charAt(1) == currentSpace.barrierrs.get(i).charAt(7)) {

                    // get the details from the barrier String.
                    int barrierBegin = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(3)));
                    int barrierEnd = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(9)));
                    int barrierPosition = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(1)));

                    // check if the cleaning device right of the barrier.
                    if ((getY() >= barrierBegin && getY() < barrierEnd) && getX() > barrierPosition -1) {

                        // if the cleaning device hit the barrier.
                        if (newX_Coordinate - command < barrierPosition) {
                            setX(barrierPosition);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }

                        // if the cleaning device doesn't hit the barrier.
                        else {
                            newX_Coordinate -= command;
                            setX(newX_Coordinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }

                    //if the cleaning device left of the barrier.
                    else {
                        if (newX_Coordinate-command<0) setX(0);
                        else {

                            newX_Coordinate-=command;
                            setX(newX_Coordinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }
                }

                // if the space doesn't contains Vertical barriers
                else {
                    if (newX_Coordinate-command<0) setX(0);
                    else {

                        newX_Coordinate-=command;
                        setX(newX_Coordinate);
                        coordinate = "(" + getX() + "," + getY() + ")";
                    }
                }
                break;
            }
        }
    }

    @Override
    public void moveEast(int command) {

        int newX_Coordinate = getX();

        if (currentSpace.barrierrs.isEmpty()){
            if (command+newX_Coordinate>currentSpace.getWidth()) setX(currentSpace.getWidth());
            else {

                newX_Coordinate+=command;
                setX(newX_Coordinate);
                coordinate = "(" + getX() + "," + getY() + ")";
            }

        }else {
            for (int i = 0; i < currentSpace.barrierrs.size(); i++) {

                // check if there is a Vertical barrier.
                if (currentSpace.barrierrs.get(i).charAt(1) == currentSpace.barrierrs.get(i).charAt(7)) {

                    // get the details from the barrier String.
                    int barrierBegin = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(3)));
                    int barrierEnd = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(9)));
                    int barrierPosition = Integer.parseInt(String.valueOf(currentSpace.barrierrs.get(i).charAt(1)));

                    // check if the cleaning device left of the barrier.
                    if ((getY() >= barrierBegin && getY() < barrierEnd) && getX() < barrierPosition) {

                        // if the cleaning device hit the barrier.
                        if (newX_Coordinate + command > barrierPosition - 1) {
                            setX(barrierPosition - 1);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }

                        // if the cleaning device doesn't hit the barrier.
                        else {
                            newX_Coordinate += command;
                            setX(newX_Coordinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }
                    //if the cleaning device right of the barrier.
                    else {
                        if (command + newX_Coordinate > currentSpace.getWidth()) setX(currentSpace.getWidth());
                        else {

                            newX_Coordinate += command;
                            setX(newX_Coordinate);
                            coordinate = "(" + getX() + "," + getY() + ")";
                        }
                    }
                }

                // if the space doesn't contains Vertical barriers
                else {
                    if (command + newX_Coordinate > currentSpace.getWidth()) setX(currentSpace.getWidth());
                    else {

                        newX_Coordinate += command;
                        setX(newX_Coordinate);
                        coordinate = "(" + getX() + "," + getY() + ")";
                    }
                }
                break;
            }
        }
    }
}

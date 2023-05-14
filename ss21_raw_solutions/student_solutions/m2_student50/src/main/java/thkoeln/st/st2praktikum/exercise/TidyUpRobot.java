package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TidyUpRobot extends AbstractEntity implements Moveable {


    private String name;
    protected Coordinate coordinate;

    protected UUID currentRoomUUID;
    protected Room currentRoom;

    protected void setXY(int x, int y){
        Coordinate coordinate = new Coordinate(x,y);
        this.coordinate = coordinate;
    }

    @Override
    public void moveEast(int command) {
        int x_R1 = getCoordinate().getX() + command;
        Integer x_R2 = null;

        //if robots are in same path
        for (int i = 0; i< getCurrentRoom().tidyUpRobots.size(); i++){
            Coordinate coordinate = getCurrentRoom().tidyUpRobots.get(i).getCoordinate();
            if (coordinate.getY() == getCoordinate().getY() && getCoordinate().getX() < coordinate.getX()) {
                x_R2 = getCurrentRoom().tidyUpRobots.get(i).getCoordinate().getX();
                break;
            }
        }

        if(x_R1 >= currentRoom.getWidth()) x_R1 = currentRoom.getWidth() - 1;

        if (currentRoom.walls.isEmpty()){
            if(x_R2 != null && x_R1 >= x_R2) x_R1=x_R2-1;
        }else {
            for (int i = 0; i< currentRoom.walls.size(); i++){
                Wall wall = currentRoom.walls.get(i);

                if(wall.getStart().getX() == wall.getEnd().getX()){
                    if(x_R2 != null && x_R1 >= x_R2 && x_R2 < wall.getStart().getX()) x_R1=x_R2-1;
                    else if (getCoordinate().getY()<=wall.getStart().getY() && getCoordinate().getY()>wall.getEnd().getY()){
                        if (x_R1>=wall.getStart().getX()) x_R1=wall.getStart().getX()-1;
                    }
                }
            }
        }
        setXY(x_R1,this.getCoordinate().getY());
    }

    @Override
    public void moveNorth(int command) {
        int y_R1 = getCoordinate().getY()+command;
        Integer y_R2 = null;

        for (int i = 0; i< getCurrentRoom().tidyUpRobots.size(); i++){
            Coordinate coordinate = getCurrentRoom().tidyUpRobots.get(i).getCoordinate();

            if (coordinate.getX() == getCoordinate().getX() && getCoordinate().getY() < coordinate.getY()) {
                y_R2 = getCurrentRoom().tidyUpRobots.get(i).getCoordinate().getY(); break;
            }
        }

        if(y_R1 >= currentRoom.getHeight()) y_R1 = currentRoom.getHeight() - 1;

        if (currentRoom.walls.isEmpty()){
            if(y_R2 != null && y_R1 >= y_R2) y_R1=y_R2 - 1;
        }else {
            for (int i = 0; i < currentRoom.walls.size(); i++) {
                Wall wall = currentRoom.walls.get(i);

                if (wall.getStart().getY() == wall.getEnd().getY()) {

                    if (y_R2 != null && y_R1 >= y_R2) y_R1 = y_R2 - 1;
                    else if (getCoordinate().getX() >= wall.getStart().getX() && getCoordinate().getX() < wall.getEnd().getX()) {
                        if (y_R1 >= wall.getStart().getY()) y_R1 = wall.getStart().getY() - 1;
                    }
                }
            }
        }
        setXY(getCoordinate().getX(),y_R1);
    }

    @Override
    public void moveSouth(int command) {
        int y_R1 = getCoordinate().getY()-command;
        Integer y_R2 = null;

        for (int i = 0; i< getCurrentRoom().tidyUpRobots.size(); i++){
            Coordinate coordinate = getCurrentRoom().tidyUpRobots.get(i).getCoordinate();
            if ((coordinate.getX()) == getCoordinate().getX() && getCoordinate().getY() > coordinate.getY()) {
                y_R2 = getCurrentRoom().tidyUpRobots.get(i).getCoordinate().getY();
                break;
            }
        }

        if(y_R1 < 0) y_R1 = 0;
        if (currentRoom.walls.isEmpty()){
            if(y_R2 != null && y_R1 <= y_R2) y_R1 = y_R2 + 1;
        }

        for (int i = 0; i< currentRoom.walls.size(); i++){
            Wall wall = currentRoom.walls.get(i);

            if(wall.getStart().getY() == wall.getEnd().getY()){
                if(y_R2 != null && y_R1<=y_R2 && y_R2>wall.getStart().getY()) y_R1=y_R2+1;
                else if (getCoordinate().getX()<=wall.getEnd().getX() && getCoordinate().getX()>wall.getEnd().getX()){
                    if (y_R1<=wall.getStart().getY()) y_R1=wall.getStart().getY()+1;
                }
            }
        }
        setXY(getCoordinate().getX(),y_R1);
    }

    @Override
    public void moveWest(int command) {
        int x_R1 = getCoordinate().getX()-command;
        Integer x_R2 = null;

        for (int i = 0; i< getCurrentRoom().tidyUpRobots.size(); i++){
            Coordinate coordinate = getCurrentRoom().tidyUpRobots.get(i).getCoordinate();
            if (coordinate.getY() == getCoordinate().getY() && getCoordinate().getX() > coordinate.getX()) {
                x_R2 = getCurrentRoom().tidyUpRobots.get(i).getCoordinate().getX();
                break;
            }
        }

        if(x_R1< 0) x_R1 = 0;
        if (currentRoom.walls.isEmpty()){
            if(x_R2 != null && x_R1 <= x_R2) x_R1=x_R2+1;
        }

        for (int i = 0; i< currentRoom.walls.size(); i++){
           Wall wall = currentRoom.walls.get(i);

            if(wall.getStart().getX() == wall.getEnd().getX()){
                if(x_R2 != null && x_R1<=x_R2 && x_R2 > wall.getStart().getX()) x_R1=x_R2+1;
                else if (getCoordinate().getY()>=wall.getStart().getY() && getCoordinate().getY()<wall.getEnd().getY()){
                    if (x_R1<=wall.getStart().getX()) x_R1=wall.getStart().getX()+1;
                }
            }
        }
        setXY(x_R1,getCoordinate().getY());
    }
}

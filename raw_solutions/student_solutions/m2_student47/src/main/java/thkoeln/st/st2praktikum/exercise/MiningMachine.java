package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bytecode.Throw;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class MiningMachine implements Moveable {

    private UUID machineID;
    private String name;
    private Point point;
    private UUID fieldID ;

    public MiningMachine(String name){
        this.machineID = UUID.randomUUID();
        this.name = name;
        this.point = new Point(0,0);
    }

    @Override
    public void moveWest(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines)
    {
        if (point.getX() == 0) return;
        Point nextPoint = new Point(point.getX()-1, point.getY());
        if (!isBlocked(fieldList, nextPoint)) this.point = nextPoint;
    }

    @Override
    public void moveSouth(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines){
        if (point.getY() == 0) return;
        Point nextPoint = new Point(point.getX(), point.getY()-1);
        if (!isBlocked(fieldList, nextPoint)) this.point = nextPoint;
    }

    @Override
    public void moveEast(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines){
        Point nextPoint = new Point(point.getX()+1, point.getY());
        if (!isBlocked(fieldList, nextPoint)) this.point = nextPoint;
    }

    @Override
    public void moveNorth(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines){
        Point nextPoint = new Point(point.getX(), point.getY()+1);
        if (!isBlocked(fieldList, nextPoint) && !isBlockedByMachine(machines,nextPoint,fieldID)) this.point = nextPoint;
    }

    public Boolean isBlockedByMachine(ArrayList<MiningMachine>machines,Point point,UUID fieldID){
        for (MiningMachine machine : machines) {
            if (machine.getPoint().equals(point) && machine.getFieldID().equals(fieldID)) return true;
        }
        return false;
    }

    public Boolean isBlocked(ArrayList<Field> fieldList,Point point) {
        for (Field value : fieldList) {
            if (this.getFieldID() == value.getFieldID()) {
                if (point.getY() == value.getHeight()) return true;
                if (point.getX() == value.getWidth()) return true;
                for( int i = 0; i < value.getBarriers().size();i++){
                    if( value.getBarriers().get(i).getStart().getY().equals(point.getY())
                            && value.getBarriers().get(i).getEnd().getY().equals(point.getY())
                            && value.getBarriers().get(i).getStart().getX() <= point.getX()
                            && point.getX() < value.getBarriers().get(i).getEnd().getX()){
                        return true;
                    }
                }
            }
        }

    return false;
    }
}


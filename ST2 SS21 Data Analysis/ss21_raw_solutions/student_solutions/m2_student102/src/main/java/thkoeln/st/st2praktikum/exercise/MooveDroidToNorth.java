package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MooveDroidToNorth {
    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;
    private final ArrayList<SpaceShip> spaceShipList;
    private String commandString;

    public MooveDroidToNorth(Droid droid, ArrayList<Barrier> allBarrierList, ArrayList<SpaceShip> spaceShipList) {
        this.droid = droid;
        this.allBarrierList = allBarrierList;
        this.spaceShipList = spaceShipList;
    }

    public void updateDroidPosition(Command command) {
        Barrier limit=new Barrier();
        int value = command.getNumberOfSteps();

        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType()=='H')
                limit=tmp;
        }
        if(limit.getStart()==null)
            droid.setPositionY( droid.getPositionY()+ value);
        else if(droid.getPositionY() < limit.getStart().getY()  && isInInterval(droid.getPositionX(),limit.getStart().getX() ,limit.getEnd().getX() )){
            if( (droid.getPositionY()+ value)>=limit.getStart().getY()  )
                droid.setPositionY( limit.getStart().getY()-1 );
            else
                droid.setPositionY( droid.getPositionY()+ value);
        }
        else{
            if( (droid.getPositionY()+ value)>= getSpaceShipMaxValue(droid.getIdSpaceShip() ) ){
                droid.setPositionY( getSpaceShipMaxValue(droid.getIdSpaceShip() )-1);
            }
            else
                droid.setPositionY( droid.getPositionY()+ value);
        }
    }
    public Boolean isInInterval(int number, int from ,int to ){
        if(from<=number && number<to)
            return true;
        return to <= number && number < from;
    }
    public int getSpaceShipMaxValue(UUID idSpaceship){
        int res=0;
        for(SpaceShip tmp:spaceShipList){
            if(tmp.getId().equals(idSpaceship)){
                res=tmp.getX();
            }
        }
        return  res;
    }
}

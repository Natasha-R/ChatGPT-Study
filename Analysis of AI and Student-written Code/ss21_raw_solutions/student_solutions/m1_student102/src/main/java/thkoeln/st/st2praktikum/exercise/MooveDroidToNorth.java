package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MooveDroidToNorth {
    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;
    private final ArrayList<SpaceShip> spaceShipList;
    private final String commandString;

    public MooveDroidToNorth(Droid droid, ArrayList<Barrier> allBarrierList, ArrayList<SpaceShip> spaceShipList, String commandString) {
        this.droid = droid;
        this.allBarrierList = allBarrierList;
        this.spaceShipList = spaceShipList;
        this.commandString = commandString;
    }

    public void updateDroidPosition() {
        Barrier limit=new Barrier();
        int value = Integer.parseInt(commandString.substring(4, 5));

        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType()=='H'){
                limit=tmp;
            }
        }
        if(droid.getPositionY() < limit.getFirstBarrierPointY() && isInInterval(droid.getPositionX(),limit.getFirstBarrierPointX(),limit.getSecondBarrierPointX())){
            if( (droid.getPositionY()+ value)>=limit.getFirstBarrierPointY() )
                droid.setPositionY( limit.getFirstBarrierPointY()-1 );
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

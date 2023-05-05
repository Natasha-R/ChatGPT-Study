package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MooveDroidToEast {
    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;
    private final ArrayList<SpaceShip> spaceShipList;

    public MooveDroidToEast(Droid droid,ArrayList<SpaceShip> spaceList, ArrayList<Barrier> barrierList) {
        this.droid=droid;
        this.allBarrierList=barrierList;
        this.spaceShipList=spaceList;

    }

    public void updateDroidPosition(String commandStr) {
        Barrier limit=new Barrier();
        int value = Integer.parseInt(commandStr.substring(4, 5));
        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip()) && tmp.getBarrierType()=='V'){
                limit=tmp;
            }
        }
        if(droid.getPositionX() < limit.getFirstBarrierPointX() && isInInterval(droid.getPositionY(),limit.getFirstBarrierPointY(),limit.getSecondBarrierPointY())){

            if( (droid.getPositionX()+ value)<limit.getFirstBarrierPointX() )
                droid.setPositionX( droid.getPositionX()+ value);
            else
                droid.setPositionX( limit.getFirstBarrierPointX()-1 );
        }
        else {
            if( (droid.getPositionX() + value)>getSpaceShipMaxValue(droid.getIdSpaceShip()) )
                droid.setPositionX(getSpaceShipMaxValue(droid.getIdSpaceShip())-1);
            else
                droid.setPositionX(droid.getPositionX() + value);
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

package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MooveDroidToEast {
    private final Droid droid;
    private final ArrayList<Barrier> allBarrier;
    private final ArrayList<SpaceShip> spaceShipList;

    public MooveDroidToEast(Droid droid,ArrayList<SpaceShip> spaceList, ArrayList<Barrier> barrierList) {
        this.droid=droid;
        this.allBarrier=barrierList;
        this.spaceShipList=spaceList;
    }

    public void updateDroidPosition(Command command) {
        Barrier limit=new Barrier();
        int value = command.getNumberOfSteps();
        for(Barrier tmp:allBarrier){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType()=='V')
                limit=tmp;
        }
        if(limit.getStart()==null)
            droid.setPositionX( droid.getPositionX()+ value);
        else if(droid.getPositionX() < limit.getStart().getX() && isInInterval(droid.getPositionY(),limit.getStart().getY(),limit.getEnd().getY() )){

            if( (droid.getPositionX()+ value)<limit.getStart().getX() )
                droid.setPositionX( droid.getPositionX()+ value);
            else
                droid.setPositionX( limit.getStart().getX()-1 );
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

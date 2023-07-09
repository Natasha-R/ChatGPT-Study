package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class MooveDroidToWest {

    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;

    public MooveDroidToWest(Droid droid, ArrayList<Barrier> barrierList) {
        this.droid=droid;
        this.allBarrierList=barrierList;
    }


    public void updateDroidPosition(String commandStr) {
        Barrier limit=new Barrier();
        int value = Integer.parseInt(commandStr.substring(4, 5));
        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType() =='V'){
                limit=tmp;
            }
        }
        if(droid.getPositionX() >= limit.getFirstBarrierPointX() && isInInterval(droid.getPositionY(),limit.getFirstBarrierPointY(),limit.getSecondBarrierPointY())){
            droid.setPositionX(Math.max((droid.getPositionX() - value), limit.getFirstBarrierPointX()));
        }
        else{
            droid.setPositionX(Math.max(droid.getPositionX() - value, 0));
        }
    }
    public Boolean isInInterval(int number, int from ,int to ){
        if(from<=number && number<to)
            return true;
        return to <= number && number < from;
    }
}

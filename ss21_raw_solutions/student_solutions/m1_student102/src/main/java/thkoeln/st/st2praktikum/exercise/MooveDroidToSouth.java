package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class MooveDroidToSouth {
    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;
    private final String commandString;

    public MooveDroidToSouth(Droid droid, ArrayList<Barrier> allBarrierList, String commandString) {
        this.droid = droid;
        this.allBarrierList = allBarrierList;
        this.commandString = commandString;
    }

    public Boolean isInInterval(int number, int from , int to ){
        if(from<=number && number<to)
            return true;
        return to <= number && number < from;
    }

    public void updateDroidPosition() {
        Barrier limit=new Barrier();
        int value = Integer.parseInt(commandString.substring(4, 5));

        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType() =='H'){
                limit=tmp;
            }
        }
        if(droid.getPositionY() > limit.getFirstBarrierPointY() && isInInterval(droid.getPositionX(),limit.getFirstBarrierPointX(),limit.getSecondBarrierPointX())){
            droid.setPositionY(Math.max((droid.getPositionY() - value), limit.getFirstBarrierPointY()));
        }
        else{
            droid.setPositionY(Math.max((droid.getPositionY() - value), 0));
        }
    }
}

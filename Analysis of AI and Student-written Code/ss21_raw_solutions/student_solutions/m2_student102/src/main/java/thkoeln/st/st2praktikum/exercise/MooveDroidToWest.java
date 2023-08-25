package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class MooveDroidToWest {

    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;

    public MooveDroidToWest(Droid droid, ArrayList<Barrier> barrierList) {
        this.droid=droid;
        this.allBarrierList=barrierList;
    }


    public void updateDroidPosition(Command command) {
        Barrier limit=new Barrier();
        int value = command.getNumberOfSteps();
        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType() =='V'){
                limit=tmp;
            }
        }
        if(limit.getStart()==null)
            droid.setPositionX(Math.max(droid.getPositionX() - value, 0));
        else if(droid.getPositionX() >= limit.getStart().getX()  && isInInterval(droid.getPositionY(),limit.getStart().getY() ,limit.getEnd().getY() )){
            droid.setPositionX(Math.max((droid.getPositionX() - value), limit.getStart().getX() ));
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

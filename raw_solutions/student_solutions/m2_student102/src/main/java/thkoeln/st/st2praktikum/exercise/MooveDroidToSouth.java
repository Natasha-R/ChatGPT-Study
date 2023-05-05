package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class MooveDroidToSouth {
    private final Droid droid;
    private final ArrayList<Barrier> allBarrierList;


    public MooveDroidToSouth(Droid droid, ArrayList<Barrier> allBarrierList) {
        this.droid = droid;
        this.allBarrierList = allBarrierList;
    }

    public Boolean isInInterval(int number, int from , int to ){
        if(from<=number && number<to)
            return true;
        return to <= number && number < from;
    }

    public void updateDroidPosition(Command command ) {
        Barrier limit=new Barrier();
        int value = command.getNumberOfSteps();

        for(Barrier tmp:allBarrierList){
            if(droid.getIdSpaceShip().equals(tmp.getIdSpaceShip() ) && tmp.getBarrierType() =='H'){
                limit=tmp;
            }
        }
        if(limit.getStart()==null)
            droid.setPositionY( droid.getPositionY()-value );
        else if(droid.getPositionY() > limit.getStart().getY() && isInInterval(droid.getPositionX(),limit.getStart().getX() ,limit.getEnd().getX() )){
            droid.setPositionY(Math.max((droid.getPositionY() - value), limit.getStart().getY() ));
        }
        else{
            droid.setPositionY(Math.max((droid.getPositionY() - value), 0));
        }
    }
}

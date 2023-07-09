package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class DroidInitialisable {
    private final ArrayList<Droid> droidList;

    public ArrayList<Droid> getDroidList() {
        return droidList;
    }
    public DroidInitialisable( ArrayList<Droid> droidList ) {
        this.droidList = droidList;
    }

    public Boolean updateDroidList( Command command ){

        for(Droid element: droidList){
            if( element.getPositionX()==0 && element.getPositionY()==0){
                if(!isDroidOnInitialPosition(command.getGridId()) ) {
                    element.setIdSpaceShip(command.getGridId());
                    return true;
                }
                else
                    return false;
            }
        }

        return false;
    }
    public Boolean isDroidOnInitialPosition( UUID idSpaceship ){
        for(Droid tmp:droidList){
            if(tmp.getPositionX()==0 && tmp.getPositionY()==0 && idSpaceship.equals(tmp.getIdSpaceShip()))
                return true;
        }
        return false;
    }
}

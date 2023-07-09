package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Traversable {
    private final Droid droid;
    private final ArrayList<Connection> allConnectionPoint;

    public Traversable(Droid droid,  ArrayList<Connection> connectionList) {
        this.droid = droid;
        this.allConnectionPoint=connectionList;
    }

    public Boolean updateDroidPosition(Command command){
        if(droid.getIdSpaceShip()==null)
            return false;
        else{
            UUID commandIdSpaceShip = command.getGridId();
            if(droid.getIdSpaceShip()==null) { }
            else{
                for(Connection tmp:allConnectionPoint){
                    if(droid.getIdSpaceShip().equals(tmp.getIdSource() ) ){
                        int coordinateSourceX=tmp.getSource().getX() ;
                        int coordinateSourceY= tmp.getSource().getY();
                        int coordinateDestinationX= tmp.getDestination().getX();
                        int coordinateDestinationY= tmp.getDestination().getY();

                        if(droid.getPositionX()==coordinateSourceX && droid.getPositionY()==coordinateSourceY && commandIdSpaceShip.equals(tmp.getIdDestination()) ){
                            droid.setIdSpaceShip(commandIdSpaceShip);
                            droid.setPositionX(coordinateDestinationX);
                            droid.setPositionY(coordinateDestinationY);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}

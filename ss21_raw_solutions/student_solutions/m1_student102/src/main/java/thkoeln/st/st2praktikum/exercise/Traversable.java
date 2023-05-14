package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Traversable {
    private final Droid droid;
    private final ArrayList<Connection> connectionPointList;

    public Traversable(Droid droid,  ArrayList<Connection> connectionList) {
        this.droid = droid;
        this.connectionPointList=connectionList;
    }

    public Boolean updateDroidPosition(String commandString){

        if(droid.getIdSpaceShip()==null)
            return false;
        else{
            UUID commandIdSpaceShip = UUID.fromString(commandString.substring(4, commandString.length() - 1));
            if(droid.getIdSpaceShip()==null) {
            }
            else{
                for(Connection tmp:connectionPointList){

                    if(droid.getIdSpaceShip().equals(tmp.getIdSource() ) ){
                        int coordinateSourceX=Integer.parseInt(tmp.getSource().substring(1,2)  );
                        int coordinateSourceY=Integer.parseInt(tmp.getSource().substring(3,4)  );
                        int coordinateDestinationX=Integer.parseInt( tmp.getDestination().substring(1,2));
                        int coordinateDestinationY=Integer.parseInt( tmp.getDestination().substring(3,4));

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

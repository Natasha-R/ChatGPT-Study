package thkoeln.st.st2praktikum.exercise;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.connexion.Connexion;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;

import java.util.ArrayList;
import java.util.UUID;
@Setter
@Component
public class Transport {
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    private  ArrayList<Connexion> allConnectionPoint;

    public Transport(){}
    public Transport(ArrayList<Connexion> connectionList){ allConnectionPoint= connectionList ; }

    public Boolean updateDroidPosition(UUID maintenanceDroidId, Command command){
        //System.out.println(connexionRepository.findAll().iterator().next() );

        MaintenanceDroid droid = maintenanceDroidRepository.findById(maintenanceDroidId).get();
        Connexion connexion=new Connexion() ;
        if (droid.getSpaceShipDeckId()== null)
            return false;
        else {
            UUID commandIdSpaceShip = command.getGridId();
            if (droid.getSpaceShipDeckId() == null) {
            } else {
                for (Connexion tmp : allConnectionPoint) {
                    if (droid.getSpaceShipDeckId().equals(tmp.getIdSource())) {
                        int coordinateSourceX = tmp.getPointSource().getX();
                        int coordinateSourceY = tmp.getPointSource().getY();
                        int coordinateDestinationX = tmp.getPointDestination().getX();
                        int coordinateDestinationY = tmp.getPointDestination().getY();

                        if (droid.getPoint().getX() == coordinateSourceX && droid.getPoint().getY() == coordinateSourceY && commandIdSpaceShip.equals(tmp.getIdDestination())) {
                            System.out.println("hahahahahahahahahahahahah");
                            droid.setSpaceShipDeckId(commandIdSpaceShip);
                            droid.setPoint(new Point( coordinateDestinationX , coordinateDestinationY) );
                            maintenanceDroidRepository.save(droid);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}


/*
if (droid.getSpaceShipDeckId() == null)
            return false;
        else {
            if (droid.getSpaceShipDeckId() == null) {
            } else {
                for( Connexion tmp: connexionRepository.findAll() ){
                    if( droid.getSpaceShipDeckId().equals( tmp.getIdSource() ) && droid.getPoint().equals( tmp.getPointSource() ) )
                        connexion=tmp;
                    if ( command.getGridId() .equals( connexion.getIdDestination() ) ){
                        droid.setSpaceShipDeckId( command.getGridId() );
                        droid.setPoint( connexion.getPointDestination() );
                        maintenanceDroidRepository.save( droid );
                        return true;
                    }

                }
            }
        }
        return false;
 */
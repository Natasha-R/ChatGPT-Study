package thkoeln.st.st2praktikum.exercise.movement;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import java.util.ArrayList;
import java.util.UUID;

@Setter
@Component
public class Transport {
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    private ArrayList<Connection> allConnectionPoint;

    public Transport(){}
    public Transport(ArrayList<Connection> connectionList){ allConnectionPoint= connectionList ; }

    public Boolean updateDroidPosition(UUID maintenanceDroidId, Command command){
        //System.out.println(connexionRepository.findAll().iterator().next() );

        MaintenanceDroid droid = maintenanceDroidRepository.findById(maintenanceDroidId).get();
        Connection connexion=new Connection() ;
        if (droid.getSpaceShipDeckId()== null)
            return false;
        else {
            UUID commandIdSpaceShip = command.getGridId();
            if (droid.getSpaceShipDeckId() == null) {
            } else {
                for (Connection tmp : allConnectionPoint) {
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

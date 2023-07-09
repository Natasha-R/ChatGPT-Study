package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandDto;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.movement.*;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class MaintenanceDroidService {
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    AddDroidInSpaceship addDroidInSpaceship;
    @Autowired
    Transport transport;
    @Autowired
    MoveToEast moveToEast;
    @Autowired
    MoveToNorth moveToNorth;
    @Autowired
    MoveToSouth moveToSouth;
    @Autowired
    MoveToWest moveToWest;

    @Autowired
    private final SpaceShipDeckService spaceShipDeckService=new SpaceShipDeckService();
    @Autowired
    private final TransportCategoryService transportCategoryService=new TransportCategoryService();
    private final ArrayList<Barrier> allBarrier=spaceShipDeckService.getAllBarrier();
    private final ArrayList<Connection> allConnectionPoint= transportCategoryService.getAllConnectionPoint();

    private ArrayList<Command> allCommands=new ArrayList<>();


    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid=new MaintenanceDroid();
        maintenanceDroid.setPoint( new Point(0,0) );
        maintenanceDroid.setName( name );
        //making Consistent
        maintenanceDroidRepository.save( maintenanceDroid );

        return maintenanceDroid.getDroidID();

    }

    public Boolean executeCommand(UUID maintenanceDroidId, Command command) {
        command.setMaintenanceDroidId( maintenanceDroidId);
        allCommands.add(command);
        System.out.println("Les barrieres "+ spaceShipDeckService.getAllBarrier());
        switch (command.getCommandType()) {
            case NORTH:
                moveToNorth.setAllBarrier( spaceShipDeckService.getAllBarrier());
                moveToNorth.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case SOUTH:
                moveToSouth.setAllBarrier( spaceShipDeckService.getAllBarrier() );
                moveToSouth.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case EAST:
                moveToEast.setAllBarrier( spaceShipDeckService.getAllBarrier() );
                moveToEast.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case WEST:
                moveToWest.setAllBarrier( spaceShipDeckService.getAllBarrier() );
                moveToWest.updateDroidPosition(maintenanceDroidId,command);
                return true;
            case ENTER:
                System.out.println("*****Entrer dans 1 *********");
                Boolean state = addDroidInSpaceship.updateDroidList( maintenanceDroidId , command) ;
                return state;
            case TRANSPORT:
                transport.setAllConnectionPoint( transportCategoryService.getAllConnectionPoint() );
                return transport.updateDroidPosition(maintenanceDroidId , command) ;
            default:
                return false;
        }
    }

    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().getSpaceShipDeckId();
    }


    public Point getMaintenanceDroidPoint(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findById(maintenanceDroidId).get().getPoint();
    }
    public ArrayList<Command> getAllCommand(){
        return allCommands;
    }
}

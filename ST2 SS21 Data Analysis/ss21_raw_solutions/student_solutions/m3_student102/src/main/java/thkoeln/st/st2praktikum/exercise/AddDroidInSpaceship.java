package thkoeln.st.st2praktikum.exercise;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;

import java.util.UUID;

@Component
public class AddDroidInSpaceship {
    @Autowired
    MaintenanceDroidRepository maintenanceDroidRepository;

    public Boolean updateDroidList(UUID maintenanceDroid , Command command) {
       MaintenanceDroid droid = maintenanceDroidRepository.findById(maintenanceDroid).get();
        for ( MaintenanceDroid element : maintenanceDroidRepository.findAll() ) {
            if (element.getPoint().getX() == 0 && element.getPoint().getY() == 0) {
                if (!isDroidOnInitialPosition(command.getGridId())) {
                    element.setSpaceShipDeckId( command.getGridId() );
                    element.setPoint(new Point(0,0));
                    droid.setSpaceShipDeckId( command.getGridId()  );
                    droid.setPoint( new Point(0,0) );
                    maintenanceDroidRepository.save( droid );
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public Boolean isDroidOnInitialPosition(UUID idSpaceship) {
        for (MaintenanceDroid tmp : maintenanceDroidRepository.findAll() ) {
            if (tmp.getPoint().getX() == 0 && tmp.getPoint().getY() == 0 && idSpaceship.equals( tmp.getSpaceShipDeckId() ))
                return true;
        }
        return false;
    }
    public Boolean exec( UUID maintenanceDroid , Command command){
        System.out.println(" ***** details de La commande ******");
        System.out.println("Command:  spaceId"+command.getGridId()+" Type "+command.getCommandType());
        System.out.println("Droid dans BD : "+maintenanceDroidRepository.findById( maintenanceDroid ).get().getName()+" ID droid ="+maintenanceDroidRepository.findById( maintenanceDroid ).get().getDroidID()+" Son SpaceShip "+maintenanceDroidRepository.findById( maintenanceDroid ).get().getSpaceShipDeckId() );
        MaintenanceDroid tmp=maintenanceDroidRepository.findById(maintenanceDroid).get();
        tmp.setSpaceShipDeckId( command.getGridId() );
        System.out.println("Droid TMP: "+tmp.getName()+" ID droid ="+tmp.getDroidID()+" Son SpaceShip "+tmp.getSpaceShipDeckId() );

        maintenanceDroidRepository.save( tmp );
        System.out.println("Apres modif dans la base on aura");
        System.out.println("Droid dans BD : "+maintenanceDroidRepository.findById( maintenanceDroid ).get().getName()+" ID droid ="+maintenanceDroidRepository.findById( maintenanceDroid ).get().getDroidID()+" Son SpaceShip "+maintenanceDroidRepository.findById( maintenanceDroid ).get().getSpaceShipDeckId() );

        return false;
    }
}

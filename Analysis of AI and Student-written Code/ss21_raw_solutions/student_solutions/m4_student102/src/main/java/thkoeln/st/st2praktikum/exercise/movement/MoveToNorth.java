package thkoeln.st.st2praktikum.exercise.movement;


import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceshipRepository;

import java.util.ArrayList;
import java.util.UUID;

@Setter
@Component
public class MoveToNorth {
    @Autowired
    MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    SpaceshipRepository spaceShipRepository;
    private ArrayList<Barrier> allBarrier;



    //a changer completement le code qui est dupliqué
    public void updateDroidPosition(UUID maintenanceDroid , Command command) {
        MaintenanceDroid droid = maintenanceDroidRepository.findById(maintenanceDroid).get();
        Barrier limit = new Barrier();
        int value = command.getNumberOfSteps();

        for (Barrier tmp : allBarrier) {
            if (droid.getSpaceShipDeckId().equals(tmp.getIdSpaceShip()) && tmp.getBarrierType() == 'H')
                limit = tmp;
        }
        if (limit.getStart() == null) {
            droid.getPoint().setY( droid.getPoint().getY() + value ) ;
            maintenanceDroidRepository.save(droid);
        }
        else if (droid.getPoint().getY()< limit.getStart().getY() && isInInterval(droid.getPoint().getX(), limit.getStart().getX(), limit.getEnd().getX())) {
            if ((droid.getPoint().getY() + value) >= limit.getStart().getY()) {
                droid.getPoint().setY( limit.getStart().getY() - 1 );
                maintenanceDroidRepository.save(droid);
            }
            else {
                droid.getPoint().setY( droid.getPoint().getY() + value );
                maintenanceDroidRepository.save(droid);
            }
        } else {
            if ((droid.getPoint().getY() + value) >= getSpaceShipMaxValue(droid.getSpaceShipDeckId())) {
                droid.getPoint().setY( getSpaceShipMaxValue(droid.getSpaceShipDeckId() ) - 1 );
                maintenanceDroidRepository.save(droid);
            } else {
                droid.getPoint().setY( droid.getPoint().getY() + value );
                maintenanceDroidRepository.save(droid);
            }
        }
    }

    public Boolean isInInterval(int number, int from, int to) {
        if (from <= number && number < to)
            return true;
        return to <= number && number < from;
    }

    public int getSpaceShipMaxValue(UUID idSpaceship) {
        return spaceShipRepository.findById(idSpaceship).get().getHeight();
    }
}

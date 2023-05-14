package thkoeln.st.st2praktikum.exercise;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.spaceship.SpaceShipRepository;

import java.util.ArrayList;
import java.util.UUID;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;

@Setter
@Component
public class MoveToEast {
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    private SpaceShipRepository spaceShipRepository;
    private  ArrayList<Barrier> allBarrier;

    public MoveToEast( ) {}

    public void updateDroidPosition(UUID maintenanceDroid,Command command) {
        MaintenanceDroid droid = maintenanceDroidRepository.findById(maintenanceDroid).get();
        Barrier limit = new Barrier();
        int value = command.getNumberOfSteps();
        for (Barrier tmp : allBarrier) {
            if (droid.getSpaceShipDeckId().equals(tmp.getIdSpaceShip()) && tmp.getBarrierType() == 'V')
                limit = tmp;
        }
        if (limit.getStart() == null) {
            droid.getPoint().setX(droid.getPoint().getX() + value);
            maintenanceDroidRepository.save(droid);
        }
        else if (droid.getPoint().getX() < limit.getStart().getX() && isInInterval(droid.getPoint().getY(), limit.getStart().getY(), limit.getEnd().getY())) {

            if ((droid.getPoint().getX() + value) < limit.getStart().getX()) {
                droid.getPoint().setX(droid.getPoint().getX() + value);
                maintenanceDroidRepository.save(droid);
            }
            else {
                droid.getPoint().setX( limit.getStart().getX() - 1 );
                maintenanceDroidRepository.save(droid);
            }
        } else {
            if ((droid.getPoint().getX() + value) > getSpaceShipMaxValue(droid.getSpaceShipDeckId())) {
                droid.getPoint().setX( getSpaceShipMaxValue( droid.getSpaceShipDeckId() ) - 1 );
                maintenanceDroidRepository.save(droid);
            }
            else {
                droid.getPoint().setX( droid.getPoint().getX()+ value );
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

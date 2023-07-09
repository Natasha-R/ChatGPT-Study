package thkoeln.st.st2praktikum.exercise;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;

import java.util.ArrayList;
import java.util.UUID;

@Setter
@Component
public class MoveToSouth {
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    private  ArrayList<Barrier> allBarrier;

    public void updateDroidPosition(UUID maintenanceDroid, Command command) {
        MaintenanceDroid droid = maintenanceDroidRepository.findById(maintenanceDroid).get();
        Barrier limit = new Barrier();
        int value = command.getNumberOfSteps();

        for (Barrier tmp : allBarrier) {
            if (droid.getSpaceShipDeckId().equals(tmp.getIdSpaceShip()) && tmp.getBarrierType() == 'H') {
                limit = tmp;
            }
        }
        if (limit.getStart() == null) {
            droid.getPoint().setY( droid.getPoint().getY() - value);
            maintenanceDroidRepository.save(droid);
        }
        else if (droid.getPoint().getY() > limit.getStart().getY() && isInInterval(droid.getPoint().getX(), limit.getStart().getX(), limit.getEnd().getX())) {
            droid.getPoint().setY( Math.max((droid.getPoint().getY() - value), limit.getStart().getY()) );
            maintenanceDroidRepository.save(droid);
        } else {
            droid.getPoint().setY( Math.max((droid.getPoint().getY()- value), 0) );
            maintenanceDroidRepository.save(droid);
        }
    }
    public Boolean isInInterval(int number, int from, int to) {
        if (from <= number && number < to)
            return true;
        return to <= number && number < from;
    }
}

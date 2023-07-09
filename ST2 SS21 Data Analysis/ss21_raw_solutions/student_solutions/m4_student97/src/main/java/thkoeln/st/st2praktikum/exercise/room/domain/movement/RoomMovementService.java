package thkoeln.st.st2praktikum.exercise.room.domain.movement;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domaintypes.Occupying;
import thkoeln.st.st2praktikum.exercise.domaintypes.Passable;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.Moveable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;

import java.util.ArrayList;
import java.util.List;

public class RoomMovementService {

    private final Room room;
    private final TidyUpRobotRepository tidyUpRobotRepository;


    public RoomMovementService(Room room, TidyUpRobotRepository tidyUpRobotRepository) {
        this.room = room;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
    }


    public Boolean roomPositionIsEmpty(Point position) {
        RoomMovementProcessing roomMovementProcessing = new RoomMovementProcessing(room, getOccupyingElements(), getPassableElements());
        return roomMovementProcessing.roomPositionIsEmpty(position);
    }

    public Moveable movePosition(Moveable movement) {
        RoomMovementProcessing roomMovementProcessing = new RoomMovementProcessing(room, getOccupyingElements(), getPassableElements());
        return roomMovementProcessing.movePosition(movement);
    }

    private List<Occupying> getOccupyingElements() {
        List<Occupying> occupyings = new ArrayList<>();
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotRepository.findTidyUpRobotsByRoom(this.room);
        for (TidyUpRobot tidyUpRobot : tidyUpRobots) {
            occupyings.add(tidyUpRobot);
        }
        return occupyings;
    }

    private List<Passable> getPassableElements() {
        List<Passable> passables = new ArrayList<>();
        for (Barrier barrier : room.getBarriers()) {
            passables.add(new BarrierMovementValidation(barrier));
        }
        return passables;
    }
}

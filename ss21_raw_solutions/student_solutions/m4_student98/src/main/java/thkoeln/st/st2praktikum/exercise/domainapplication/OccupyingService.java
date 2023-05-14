package thkoeln.st.st2praktikum.exercise.domainapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domaintypes.Occupying;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OccupyingService {

    private final TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    public OccupyingService(TidyUpRobotRepository tidyUpRobotRepository) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
    }

    public List<Occupying> getAllOccupyingElementsForRoom(Room room) {
        List<Occupying> occupyings = new ArrayList<>();
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotRepository.findTidyUpRobotsByRoom(room);
        for (TidyUpRobot tidyUpRobot : tidyUpRobots) {
            occupyings.add(tidyUpRobot);
        }
        return occupyings;
    }
}

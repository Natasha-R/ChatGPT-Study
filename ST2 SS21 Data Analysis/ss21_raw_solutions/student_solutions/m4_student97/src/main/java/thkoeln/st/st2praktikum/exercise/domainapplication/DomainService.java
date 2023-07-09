package thkoeln.st.st2praktikum.exercise.domainapplication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;

@Service
public class DomainService {

    @Getter
    private final RoomService roomService;
    @Getter
    private final TidyUpRobotService tidyUpRobotService;
    @Getter
    private final TransportCategoryService transportCategoryService;
    @Getter
    private final OccupyingService occupyingService;

    @Autowired
    public DomainService(
            RoomService roomService,
            TidyUpRobotService tidyUpRobotService,
            TransportCategoryService transportCategoryService,
            OccupyingService occupyingService)
    {
        this.roomService = roomService;
        this.tidyUpRobotService = tidyUpRobotService;
        this.transportCategoryService = transportCategoryService;
        this.occupyingService = occupyingService;
    }

}

package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thkoeln.st.st2praktikum.exercise.repositories.CleaningDeviceRepositry;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepositry;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceRepositry;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepositry;

import java.util.List;
import java.util.UUID;

@Service
public class Controller {
    @Getter private SpaceRepositry spaceRepositry;
    @Getter private CleaningDeviceRepositry cleaningDeviceRepositry;
    @Getter private TransportCategoryRepositry transportCategoryRepositry;
    @Getter private ConnectionRepositry connectionRepositry;

    private CleaningDevice mm = null;

    @Autowired
    public Controller(SpaceRepositry spaceRepositry, CleaningDeviceRepositry cleaningDeviceRepositry,
                      TransportCategoryRepositry transportCategoryRepositry, ConnectionRepositry connectionRepositry) {
        this.spaceRepositry = spaceRepositry;
        this.cleaningDeviceRepositry = cleaningDeviceRepositry;
        this.transportCategoryRepositry = transportCategoryRepositry;
        this.connectionRepositry = connectionRepositry;
    }

    public boolean execute(UUID mmID, Task cmd){
        mm = cleaningDeviceRepositry.get(mmID);
        try {
            if (cmd.getTaskType() == TaskType.ENTER)
                entry(cmd.getGridId());
            else if (cmd.getTaskType() == TaskType.TRANSPORT)
                travers(cmd.getGridId());
            else
                move(cmd);
            return true;
        } catch (Exception e) {
            System.out.println("Command "+cmd.toString()+" failed:\n"+e.getMessage());
            return false;
        }
    }

    private void move(Task cmd){
        var steps = cmd.getNumberOfSteps();
        var space = spaceRepositry.get(mm.getSpaceId());
        for (int i = 0; i < steps; i++) { moveStep(cmd.getTaskType(), space.getWalls()); }
    }

    private void moveStep(TaskType dir, List<Wall> Walls){
        for (Wall b : Walls){ if (b.blocks(mm.getPoint(), dir)) return; }
        var newPoint = mm.getPoint().copy();
        if (dir == TaskType.NORTH) newPoint.moveNo();
        if (dir == TaskType.EAST) newPoint.moveEa();
        if (dir == TaskType.SOUTH) newPoint.moveSo();
        if (dir == TaskType.WEST) newPoint.moveWe();

        if (occupiedCoords(mm.getSpaceId(), newPoint))
            return;
        mm.setPoint(newPoint);
    }

    private void travers(UUID destSpaceID){
        var Space = spaceRepositry.get(mm.getSpaceId());
        var connection = Space.getConnections().get(destSpaceID);
        if (connection == null)
            throw new InvalidCommandException("Connection does not exist");
        if (!connection.getSrc().equals(mm.getPoint()))
            throw new InvalidCommandException("Not standing on a Tunnel");
        if (occupiedCoords(destSpaceID, connection.getDest()))
            throw new InvalidCommandException("Entry Space is occupied by another machine");
        mm.setSpaceId(destSpaceID);
        mm.setPoint(connection.getDest());
    }

    private void entry(UUID destSpaceID){
        if (mm.getSpaceId() != null)
            throw new InvalidCommandException("Can't re-entry machine already in a Space");
        if (occupiedCoords(destSpaceID, mm.getPoint()))
            throw new InvalidCommandException("Entry Space is occupied by another machine");
        mm.setSpaceId(destSpaceID);
        cleaningDeviceRepositry.put(mm);
    }

    private boolean occupiedCoords(UUID spaceID, Point point){
        var blocking = cleaningDeviceRepositry.findByPoint(spaceID, point);
        return !blocking.isEmpty();
    }
}

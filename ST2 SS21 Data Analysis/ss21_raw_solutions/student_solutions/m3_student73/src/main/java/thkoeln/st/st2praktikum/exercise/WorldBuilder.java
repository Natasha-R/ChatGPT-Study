package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class WorldBuilder {
    private RoomFactory roomBuilder = new RoomFactory();
    private RobotFactory robotBuilder = new RobotFactory();
    private TransportSystemFactory transportSystemBuilder = new TransportSystemFactory();
    private HashMap<UUID, TidyUpRobot> robotFinder = new HashMap<UUID, TidyUpRobot>();
    private HashMap<UUID, Room> roomFinder = new HashMap<UUID, Room>();
    private HashMap<UUID, TransportSystem> transportSystemFinder = new HashMap<UUID, TransportSystem>();
    @Autowired
    public TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public TransportSystemRepository transportSystemRepository;

    public UUID createRoom(int width, int height) {
        Room room = new Room(width, height);
        roomFinder.put(room.getId(), room);
        roomRepository.save(room);
        return room.getId();
    }

    public UUID createRobot(String name) {
        TidyUpRobot robot = robotBuilder.getRobot(name);
        robotFinder.put(robot.getId(), robot);
        tidyUpRobotRepository.save(robot);
        return robot.getId();
    }

    public UUID createTransportSystem(String systemName) {
        TransportSystem system = transportSystemBuilder.getTransportsystem(systemName);
        transportSystemFinder.put(system.getId(), system);
        transportSystemRepository.save(system);
        return system.getId();
    }
    
    public HashMap<UUID, TidyUpRobot> getRobotFinder() {
        return robotFinder;
    }

    public HashMap<UUID, Room> getRoomFinder() {
        return roomFinder;
    }

    public HashMap<UUID, TransportSystem> getTransportSystemFinder() { return transportSystemFinder; }


}

package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.ConnectionRepository;

public interface Movable {

    public boolean command(Order order, ConnectionRepository connectionRepository, SpaceRepository spaceRepository);

    public boolean transport(ConnectionRepository connectionRepository , String destination , SpaceRepository spaceHashMap);

    public boolean moveWest(Integer distanceParameter, Space space);

    public boolean moveEast(Integer distanceParameter, Space space);

    public boolean moveNorth(Integer distanceParameter, Space space);

    public boolean moveSouth(Integer distanceParameter, Space space);

    public boolean canCollide();

}

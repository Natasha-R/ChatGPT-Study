package thkoeln.st.st2praktikum.exercise.Interfaces;

import thkoeln.st.st2praktikum.exercise.Entities.Obstacle;
import thkoeln.st.st2praktikum.exercise.Entities.SpaceShipDeck;

public interface CreateObstacleable {
    Obstacle createObstacle(SpaceShipDeck spaceShipDeck, String obstacleString);

    void addObstacleOnSpaceShipDeck(SpaceShipDeck spaceShipDeck);


}

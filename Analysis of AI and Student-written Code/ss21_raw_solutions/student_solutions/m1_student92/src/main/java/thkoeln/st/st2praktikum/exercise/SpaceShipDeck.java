package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceShipDeck implements SpaceShipDeckInterface{
    UUID id;

    Integer height;
    Integer width;

    List<String> xWalls = new ArrayList<>();
    List<String> yWalls = new ArrayList<>();

    List<Connection> connections = new ArrayList<>();
}

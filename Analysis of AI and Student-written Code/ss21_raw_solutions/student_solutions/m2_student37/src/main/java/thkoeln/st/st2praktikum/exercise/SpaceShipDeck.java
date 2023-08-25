package thkoeln.st.st2praktikum.exercise;

import java.util.LinkedList;
import java.util.UUID;

public class SpaceShipDeck {
        private UUID id;
        private int width;
        private int height;
        private LinkedList<Obstacle> obstacles;
        private LinkedList<Connector> connectors;

        public SpaceShipDeck(int top, int span){
            id=UUID.randomUUID();
            height = top;
            width = span;
            obstacles = new LinkedList<>();
            connectors = new LinkedList<>();
        }

        public UUID getId() {
            return id;
        }

        public void addConnector(Connector connector){
            connectors.add(connector);
        }

        public LinkedList<Obstacle> getObstacles() {
            return obstacles;
        }

        public void addObstacle(Obstacle obstacle) {
            obstacles.add(obstacle);
        }

        public LinkedList<Connector> getConnectors() {
            return connectors;
        }

        public int getwidth() {
            return width;
        }

        public int getheight() {
            return height;
        }
}



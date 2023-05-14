package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceShipDeck implements SpaceObservable {
    private UUID id;
    private Integer height;
    private Integer width;
    private List<Connection> connections = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();

    public List<Wall> getxWalls() {
        List<Wall> xWalls = new ArrayList<>();
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getStart().getY().equals(walls.get(i).getEnd().getY())) {
                xWalls.add(walls.get(i));
            }
        }
        return xWalls;
    }

    public List<Wall> getyWalls() {
        List<Wall> yWalls = new ArrayList<>();
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getStart().getX().equals(walls.get(i).getEnd().getX())) {
                yWalls.add(walls.get(i));
            }
        }
        return yWalls;
    }

    @Override
    public Boolean isFieldFree(Integer x, Integer y, MaintenanceDroidRepository maintenanceDroidRepository) {
        //Überprüft Grenzen des Decks
        if (x < 0 || x > width)
            return false;
        if (y < 0 || y > height)
            return false;

        //Überprüft das sich kein anderer Droid auf dem Feld befindet
        for (int i = 0; i < maintenanceDroidRepository.getMaintenanceDroids().size(); i++) {
            if (maintenanceDroidRepository.getMaintenanceDroids().get(i).getVector2D().getX().equals(x) && maintenanceDroidRepository.getMaintenanceDroids().get(i).getVector2D().getY().equals(y))
                return false;
        }
        return true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }


    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }


    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }


    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getStart().getX() < 0 || walls.get(i).getStart().getY() < 0) {
                throw new IllegalArgumentException("wall out of bounds");
            }
            if (walls.get(i).getEnd().getX() > width || walls.get(i).getEnd().getY() > height) {
                throw new IllegalArgumentException("wall out of bounds");
            }
        }

        this.walls = walls;
    }
}

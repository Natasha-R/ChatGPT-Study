package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShipDeck extends AbstractEntity {

    private int height, width;
    private String[][] grid = new String[width][height];
    @ManyToMany(cascade = CascadeType.ALL)
    private List<TransportTechnology> transportTechnologiesList = new ArrayList<>();

    @ElementCollection (fetch = FetchType.EAGER)
    private List<Wall> wallList = new ArrayList<>();


    public ShipDeck(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new String[width][height];
    }

    public List<TransportTechnology> getTransportTechnology(){
        return transportTechnologiesList;
    }


    public void buildWall(Wall wallString) {

        int fromX = wallString.getStart().getX();
        int fromY = wallString.getStart().getY();
        int toX = wallString.getEnd().getX();
        int toY = wallString.getEnd().getY();


        if (fromX<0 || fromX > grid[0].length || fromY <0 || fromY > grid.length || toX<0 || toX>grid[0].length || toY<0 || toY > grid.length)
            throw new IllegalArgumentException("Barriere out of bounds nicht zulässig");        //Out of Bounds check


        if (fromX == toX) {
            for (int j = fromY; j < toY && j < grid.length; j++) {
                if (grid[fromX][j]==null) {
                    grid[fromX][j] = "Wan1";
                }

                if (fromX !=0) {
                    if (grid[fromX - 1][j]==null) {
                        grid[fromX - 1][j] = "Wan2";
                    }
                }else {
                    if (grid[fromX + 1][j] ==null){
                        grid[fromX + 1][j] = "Wan2";}}

            }

        } else if (fromY == toY) {

            for (int j = fromX; j < toX; j++) {
                if (grid[j][fromY] ==null){
                    grid[j][fromY] = "Wan1";}

                if (fromY !=0) {
                    if (grid[j][fromY - 1] ==null){
                        grid[j][fromY - 1] = "Wan2";
                    }
                }else {if (grid[j][fromY +1] ==null){
                    grid[j][fromY +1] = "Wan2";
                }}
            }
        } else
            throw new UnsupportedOperationException();
    }


    public UUID addConnection(TransportTechnology transportTechnology, UUID entranceShipDeckID, Coordinate entranceCoordinates, UUID exitShipDeckID, Coordinate exitCoordinates){
        transportTechnologiesList.add(transportTechnology);
        Connection connection = new Connection(entranceShipDeckID,entranceCoordinates,exitShipDeckID,exitCoordinates) {
        };
        int check = 0;
        if (entranceCoordinates.getY()<0|| entranceCoordinates.getX()<0||   exitCoordinates.getX()<0||
            exitCoordinates.getY()<0|| entranceCoordinates.getX()>grid.length -1||
            entranceCoordinates.getY()>grid[0].length-1 || exitCoordinates.getX()>grid.length-1
           ||exitCoordinates.getY()>grid.length-1)
        throw new IllegalArgumentException("Out of Bound Connections nicht möglich");

        if (transportTechnologiesList!=null)

            check=getTransportTechnology().indexOf(transportTechnology);
        if (check<0){
            throw new IllegalArgumentException();
        }
        transportTechnologiesList.get(transportTechnologiesList.indexOf(transportTechnology)).getConnectionList().add(connection);
        return connection.getId();
    }
}




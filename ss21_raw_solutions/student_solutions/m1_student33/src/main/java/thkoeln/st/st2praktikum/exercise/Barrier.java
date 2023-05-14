package thkoeln.st.st2praktikum.exercise;

import lombok.Data;

import java.util.UUID;

@Data
public class Barrier {
    private UUID spaceShipId;
    private UUID uuid;
    private Coordinate begin;
    private Coordinate end;

    public Barrier(UUID spaceShipId, String coordinates) {
            uuid = UUID.randomUUID();
            this.spaceShipId = spaceShipId;
            getTheCoordinates(coordinates);
    }

    /**
     *
     * @param coordinates format "(2,3)-(10,3)"
     */
    public void getTheCoordinates(String coordinates){
        String[] limit =  coordinates.split("-");
        begin = Coordinate.switchStringToCoordinate(limit[0]);
        end = Coordinate.switchStringToCoordinate(limit[1]);
    }




}

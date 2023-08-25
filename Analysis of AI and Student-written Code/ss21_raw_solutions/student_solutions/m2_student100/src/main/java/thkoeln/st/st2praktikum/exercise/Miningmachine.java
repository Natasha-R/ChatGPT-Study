package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

public class Miningmachine implements SpawnmachineInterface, ExecutecommandInterface, Directionmoveable, Connectionmoveable {
    private String name;
    private UUID miningmachineuuid;
    private Coordinate coordinates = new Coordinate("(0,0)");
    private boolean executefailer = false;


    public Miningmachine(String name, UUID miningmachineuuid) {
        this.name = name;
        this.miningmachineuuid = miningmachineuuid;

    }



    @Override
    public Fieldminingmachinehashmap executeCommand(OrderType moveCommand, Integer steps, UUID uuid,
                                                    Miningmachinelist miningmachinelist, Fieldllist fieldlist, Connectionlist connectionlist,
                                                    Fieldminingmachinehashmap fieldminingmachinehashmap, UUID fieldwhererobotisplacedon, UUID miningmachineuuid,
                                                    Machinecoordinateshashmap machinecoordinateshashmap) {

        switch (moveCommand) {
            case ENTER: {
                spawnMiningmachine(uuid, fieldlist, fieldminingmachinehashmap, miningmachineuuid, machinecoordinateshashmap, miningmachinelist);
                break;
            }
            case TRANSPORT: {
                moveInconnection(miningmachineuuid, miningmachinelist, connectionlist, fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case NORTH: {
                moveNorth(steps, miningmachinelist, fieldlist, fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case SOUTH: {
                moveSouth(steps, miningmachinelist, fieldlist, fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case WEST: {
                moveWest(steps, miningmachinelist, fieldlist,
                        fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case EAST: {
                moveEast(steps, miningmachinelist, fieldlist,
                        fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
        }
        return fieldminingmachinehashmap;
    }


    @Override
    public void spawnMiningmachine(UUID fielduuid, Fieldllist fieldllist,
                                   Fieldminingmachinehashmap fieldminingmachinehashmap, UUID miningmachineuuid,
                                   Machinecoordinateshashmap machinecoordinateshashmap, Miningmachinelist miningmachinelist) {
        boolean breaker = false;
        int[] tempcoordinates = machinecoordinateshashmap.getMiningmachinecoordinatehashmap().
                get(miningmachinelist.getMiningmachinelist().get(0).miningmachineuuid);

        for (Miningmachine miningMachine : miningmachinelist.getMiningmachinelist()) {
            if (tempcoordinates != null) {
                if (tempcoordinates[0] == miningMachine.coordinates.getX()) {
                    breaker = true;
                    executefailer = false;
                }
            }
        }
        if (!breaker) {
            executefailer = true;
            int[] temptempcoordinates = new int[]{0};
            machinecoordinateshashmap.getMiningmachinecoordinatehashmap().put(miningmachinelist.getMiningmachinelist().get(0).miningmachineuuid, temptempcoordinates);
        }
        fieldminingmachinehashmap.getFieldminingmachinehashmap().put(miningmachineuuid, fielduuid);


    }

    @Override
    public void moveNorth(Integer stringsteps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap fieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon) {
       // int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (getCoordinates().getY() + 1 == wall.getStart().getY()) {
                            if (getCoordinates().getX() >= wall.getStart().getX() && getCoordinates().getX() < wall.getEnd().getX()) {
                                breaker = true;
                            }
                        }
                    }
                    if (!breaker) {
                      //  coordinates[1] = coordinates[1] + 1;
                        coordinates = new Coordinate("("+ coordinates.getX() + ","+ (coordinates.getY()+1)+ ")" );

                    }
                }
            }
        }

    }

    @Override
    public void moveSouth(Integer stringsteps, Miningmachinelist miningmachinelist,
                          Fieldllist fieldlist, Fieldminingmachinehashmap fieldminingmachinehashmap,
                          UUID fieldwhereRobotisplacedon) {

        boolean breaker = false;
        // int steps = Integer.parseInt(stringsteps);
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (getCoordinates().getY() == wall.getStart().getY()) {
                            if (getCoordinates().getX() >= wall.getStart().getX() && getCoordinates().getX() <= wall.getEnd().getX()) {
                                breaker = true;
                            }
                        }
                    }
                    if (!breaker) {
                     //   coordinates[1] = coordinates[1] - 1;
                        coordinates = new Coordinate("("+ coordinates.getX() + ","+ (coordinates.getY()-1)+ ")" );

                    }
                }
            }
        }

    }

    @Override
    public void moveEast(Integer stringsteps, Miningmachinelist miningmachinelist,
                         Fieldllist fieldlist, Fieldminingmachinehashmap fieldminingmachinehashmap,
                         UUID fieldwhereRobotisplacedon) {

     //   int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (getCoordinates().getX() + 1 == wall.getStart().getX()) {
                            if (getCoordinates().getY() >= wall.getStart().getY() && getCoordinates().getY() < wall.getEnd().getX()) {
                                breaker = true;
                            }
                        }
                    }
                    if (!breaker) {
                     //   coordinates[0] = coordinates[0] + 1;
                        coordinates = new Coordinate("("+ (coordinates.getX()+1) + ","+ coordinates.getY()+ ")" );

                    }
                }
            }

        }
    }

    @Override
    public void moveWest(Integer stringsteps, Miningmachinelist miningMachinelist, Fieldllist fieldlist,
                         Fieldminingmachinehashmap fieldMiningMachineHashMap, UUID fieldwhererobotisplacedon) {

       // int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhererobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (getCoordinates().getX() == wall.getStart().getX()) {
                            if (getCoordinates().getY() >= wall.getStart().getX() && getCoordinates().getY() < wall.getEnd().getX()) {
                                breaker = true;
                            }
                        }
                    }
                    if (!breaker) {
                     //   coordinates[0] = coordinates[0] - 1;
                        coordinates = new Coordinate("("+ (coordinates.getX()-1) + ","+ coordinates.getY()+ ")" );

                    }
                }
            }

        }
    }


    @Override
    public void moveInconnection(UUID miningmachineuuid, Miningmachinelist miningmachinelist,
                                 Connectionlist connectionslist, Fieldminingmachinehashmap fieldminingmachinehashmap
            , UUID fieldwhererobotisplacedon) {

        for (Connection connection : connectionslist.getConnectionlist()) {
            executefailer = coordinates.equals(connection.getSourceCoordinate());
            if (isExecutefailer()) {
                coordinates = connection.getDestinationCoordinate();
                fieldminingmachinehashmap.getFieldminingmachinehashmap().put(miningmachineuuid, connection.getDestinationFieldid());
                break;
            }
        }

    }


    public String getName() {
        return name;
    }

    public UUID getMiningmachineuuid() {
        return miningmachineuuid;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isExecutefailer() {
        return executefailer;
    }

    public void setExecutefailer(boolean executefailer) {
        this.executefailer = executefailer;
    }

}
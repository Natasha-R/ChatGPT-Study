package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

public class Miningmachine implements SpawnmachineInterface, ExecutecommandInterface, Directionmoveable, Connectionmoveable {
    String name;
    UUID miningmachineuuid;
    int coordinates[] = {0,0};
    boolean executefailer = false;


    public Miningmachine(String name, UUID miningmachineuuid) {
        this.name = name;
        this.miningmachineuuid = miningmachineuuid;

    }



    @Override
    public Fieldminingmachinehashmap executecommand(String moveCommand, String steps, String uuid,
                                                    Miningmachinelist miningmachinelist, Fieldllist fieldlist, Connectionlist connectionlist,
                                                    Fieldminingmachinehashmap fieldminingmachinehashmap, UUID fieldwhererobotisplacedon, UUID miningmachineuuid,
                                                    Machinecoordinateshashmap machinecoordinateshashmap) {

        switch (moveCommand) {
            case "en": {
                spawnminingmachine(uuid, fieldlist, fieldminingmachinehashmap, miningmachineuuid, machinecoordinateshashmap, miningmachinelist);
                break;
            }
            case "tr": {
                moveinconnection(miningmachineuuid, miningmachinelist, connectionlist, fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case "no": {
                movenorth(steps, miningmachinelist, fieldlist, fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case "so": {
                movesouth(steps, miningmachinelist, fieldlist, fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case "we": {
                movewest(steps, miningmachinelist, fieldlist,
                        fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
            case "ea": {
                moveeast(steps, miningmachinelist, fieldlist,
                        fieldminingmachinehashmap, fieldwhererobotisplacedon);
                break;
            }
        }
        return fieldminingmachinehashmap;
    }


    @Override
    public Fieldminingmachinehashmap spawnminingmachine(String fielduuid, Fieldllist fieldllist,
                                                        Fieldminingmachinehashmap fieldminingmachinehashmap, UUID miningmachineuuid,
                                                        Machinecoordinateshashmap machinecoordinateshashmap, Miningmachinelist miningmachinelist) {
        boolean breaker = false;
        int[] tempcoordinates = machinecoordinateshashmap.miningmachinecoordinatehashmap.
                get(miningmachinelist.miningmachinelist.get(0).miningmachineuuid);

        for(Miningmachine miningMachine : miningmachinelist.miningmachinelist) {
            if(tempcoordinates != null) {
                if (tempcoordinates[0] == miningMachine.coordinates[0] ) {
                    breaker = true;
                    executefailer = false;
                }
            }
        }
        if (!breaker) {
            executefailer = true;
            int[] temptempcoordinates = new int[] {0};
            machinecoordinateshashmap.miningmachinecoordinatehashmap.put(miningmachinelist.miningmachinelist.get(0).miningmachineuuid, temptempcoordinates);
        }
        fieldminingmachinehashmap.fieldminingmachinehashmap.put(miningmachineuuid,UUID.fromString(fielduuid));

        return fieldminingmachinehashmap;

    }

    @Override
    public Fieldminingmachinehashmap movenorth(String stringsteps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap fieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon ) {
            int steps = Integer.parseInt(stringsteps);
            boolean breaker = false;
                for(Field field : fieldlist.fieldList) {
                    if (field.fielduuid.equals(fieldwhereRobotisplacedon)) {
                        for (int i = 0; i < steps; i++) {
                            for (Wall wall : field.walllist) {
                               if(coordinates[1]+1 == wall.coordinates[0][1]) {
                                   if(coordinates[0] >= wall.coordinates[0][0] && coordinates[0] < wall.coordinates[1][0]) {
                                           breaker = true;
                                    }
                                }
                            }
                            if(!breaker) {
                                coordinates[1]=coordinates[1]+1;
                            }
                        }
                    }
                }
                return fieldMiningMachineHashMap;
            }

    @Override
    public Fieldminingmachinehashmap movesouth(String stringsteps, Miningmachinelist miningmachinelist,
                                               Fieldllist fieldlist, Fieldminingmachinehashmap fieldminingmachinehashmap,
                                               UUID fieldwhereRobotisplacedon) {

        boolean breaker = false;
        int steps = Integer.parseInt(stringsteps);
        for(Field field : fieldlist.fieldList) {
            if (field.fielduuid.equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < steps; i++) {
                    for (Wall wall : field.walllist) {
                        if (coordinates[1] == wall.coordinates[0][1]) {
                            if (coordinates[0] >= wall.coordinates[0][0] && coordinates[0] <= wall.coordinates[1][0]) {
                                breaker = true;
                            }
                        }
                    }
                        if(!breaker) {
                            coordinates[1]=coordinates[1]-1;

                    }
                }
            }
        }
        return fieldminingmachinehashmap;
    }

    @Override
    public Fieldminingmachinehashmap moveeast(String stringsteps, Miningmachinelist miningmachinelist,
                                              Fieldllist fieldlist, Fieldminingmachinehashmap fieldminingmachinehashmap,
                                              UUID fieldwhereRobotisplacedon) {

        int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.fieldList) {
            if (field.fielduuid.equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < steps; i++) {
                    for (Wall wall : field.walllist) {
                        if (coordinates[0] + 1 == wall.coordinates[0][0]) {
                            if (coordinates[1] >= wall.coordinates[0][1] && coordinates[1] < wall.coordinates[1][0]) {
                                breaker = true;
                            }
                        }
                    }
                        if (!breaker) {
                            coordinates[0] = coordinates[0] + 1;

                    }
                }
            }

        }
        return fieldminingmachinehashmap;
    }

    @Override
    public Fieldminingmachinehashmap movewest(String stringsteps, Miningmachinelist miningMachinelist, Fieldllist fieldlist,
                                              Fieldminingmachinehashmap fieldMiningMachineHashMap, UUID fieldwhererobotisplacedon) {

        int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.fieldList) {
            if (field.fielduuid.equals(fieldwhererobotisplacedon)) {
                for (int i = 0; i < steps; i++) {
                    for (Wall wall : field.walllist) {
                        if (coordinates[0] == wall.coordinates[0][0]) {
                            if (coordinates[1] >= wall.coordinates[0][0] && coordinates[1] < wall.coordinates[1][0]) {
                                breaker = true;
                            }
                        }
                    }
                        if (!breaker) {
                            coordinates[0] = coordinates[0] - 1;

                    }
                }
            }

        }
        return fieldMiningMachineHashMap;
    }


    @Override
    public Fieldminingmachinehashmap moveinconnection(UUID miningmachineuuid, Miningmachinelist miningmachinelist,
                                                      Connectionlist connectionslist, Fieldminingmachinehashmap fieldminingmachinehashmap
                            , UUID fieldwhererobotisplacedon) {

        for (Connection connection : connectionslist.connectionlist) {
            executefailer = Arrays.equals(coordinates, connection.sourcecoordinateinarray);
            if (executefailer) {
                coordinates = connection.destinationcoordinateinarray;
                fieldminingmachinehashmap.fieldminingmachinehashmap.put(miningmachineuuid, connection.destinationFieldid);
                break;
            }
        }
        return fieldminingmachinehashmap;

    }
}




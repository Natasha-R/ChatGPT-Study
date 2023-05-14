package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiningMachine extends AbstractEntity implements SpawnmachineInterface, ExecutecommandInterface, Directionmoveable, Connectionmoveable  {


    private UUID fieldid;
    private UUID miningmachineuuid;
    @Id
    public UUID getId() {
        return miningmachineuuid;
    }

    public void setId(UUID id) {
        this.miningmachineuuid = id;
    }

    private String name;
    private boolean executefailer = false;



    @Embedded
    private Coordinate coordinates = new Coordinate("(0,0)");


    public MiningMachine(String name, UUID miningmachineuuid) {
        this.name = name;
        this.miningmachineuuid = miningmachineuuid;

    }


    @Override
    public Coordinate executeCommand(OrderType moveCommand, Integer steps, UUID uuid,
                                     MiningMachinelist miningmachinelist, Fieldllist fieldlist, ConnectionList connectionlist,
                                     MiningMachineRepository miningMachineRepository, UUID fieldwhererobotisplacedon, UUID miningmachineuuid,
                                     Machinecoordinateshashmap machinecoordinateshashmap) {

        switch (moveCommand) {
            case ENTER:
                coordinates = spawnMiningmachine(uuid, fieldlist, miningMachineRepository, miningmachineuuid, machinecoordinateshashmap, miningmachinelist);
                break;

            case TRANSPORT:
                coordinates = moveInconnection(miningmachineuuid, miningmachinelist, connectionlist, miningMachineRepository, fieldwhererobotisplacedon);
                break;

            case NORTH:
                coordinates = moveNorth(steps, miningmachinelist, fieldlist, miningMachineRepository, fieldwhererobotisplacedon);
                break;

            case SOUTH:
                coordinates = moveSouth(steps, miningmachinelist, fieldlist, miningMachineRepository, fieldwhererobotisplacedon);
                break;

            case WEST:
                coordinates = moveWest(steps, miningmachinelist, fieldlist,
                        miningMachineRepository, fieldwhererobotisplacedon);
                break;

            case EAST:
                coordinates = moveEast(steps, miningmachinelist, fieldlist,
                        miningMachineRepository, fieldwhererobotisplacedon);
                break;
        }
        return coordinates;
    }


    @Override
    public Coordinate spawnMiningmachine(UUID fielduuid, Fieldllist fieldllist,
                                   MiningMachineRepository miningMachineRepository, UUID miningmachineuuid,
                                   Machinecoordinateshashmap machinecoordinateshashmap, MiningMachinelist miningmachinelist) {
        boolean breaker = false;
        int[] tempcoordinates = machinecoordinateshashmap.getMiningmachinecoordinatehashmap().
                get(miningmachinelist.getMiningmachinelist().get(0).miningmachineuuid);

        for (MiningMachine miningMachine : miningmachinelist.getMiningmachinelist()) {
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
        fieldid = fielduuid;
        for(MiningMachine machine: miningmachinelist.getMiningmachinelist()) {
            if(machine.getMiningmachineuuid() == miningmachineuuid) {
                miningMachineRepository.save(machine);
            }
        }

    return coordinates;
    }

    @Override
    public Coordinate moveNorth(Integer stringsteps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon) {
        // int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (coordinates.getY() + 1 == wall.getStart().getY()) {
                            if (coordinates.getX() >= wall.getStart().getX() && coordinates.getX() < wall.getEnd().getX()) {
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
        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }

    @Override
    public Coordinate moveSouth(Integer stringsteps, MiningMachinelist miningmachinelist,
                          Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                          UUID fieldwhereRobotisplacedon) {

        boolean breaker = false;
        // int steps = Integer.parseInt(stringsteps);
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (coordinates.getY() == wall.getStart().getY()) {
                            if (coordinates.getX() >= wall.getStart().getX() && coordinates.getX() <= wall.getEnd().getX()) {
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


        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }

    @Override
    public Coordinate moveEast(Integer stringsteps, MiningMachinelist miningmachinelist,
                         Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon) {

        //   int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (coordinates.getX() + 1 == wall.getStart().getX()) {
                            if (coordinates.getY() >= wall.getStart().getY() && coordinates.getY() < wall.getEnd().getX()) {
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
        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }

    @Override
    public Coordinate moveWest(Integer stringsteps, MiningMachinelist miningMachinelist, Fieldllist fieldlist,
                               MiningMachineRepository miningMachineRepository, UUID fieldwhererobotisplacedon) {

        // int steps = Integer.parseInt(stringsteps);
        boolean breaker = false;
        for (Field field : fieldlist.getFieldList()) {
            if (field.getFielduuid().equals(fieldwhererobotisplacedon)) {
                for (int i = 0; i < stringsteps; i++) {
                    for (Wall wall : field.getWalllist()) {
                        if (coordinates.getX() == wall.getStart().getX()) {
                            if (coordinates.getY() >= wall.getStart().getX() && coordinates.getY() < wall.getEnd().getX()) {
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


        fieldid = fieldwhererobotisplacedon;
        return coordinates;
    }


    @Override
    public Coordinate moveInconnection(UUID miningmachineuuid, MiningMachinelist miningmachinelist,
                                       ConnectionList connectionslist, MiningMachineRepository miningMachineRepository
            , UUID fieldwhererobotisplacedon) {

        for (Connection connection : connectionslist.getConnectionlist()) {
            executefailer = coordinates.equals(connection.getSourceCoordinate());
            if (isExecutefailer()) {
                coordinates = connection.getDestinationCoordinate();
                fieldid = connection.getDestinationFieldid();
                break;
            }
        }
        return coordinates;
    }


    public String getName() {
        return name;
    }


    public UUID getFieldId() {
        return fieldid;
    }

    public void setFieldId(UUID fieldid) {
        this.fieldid = fieldid;
    }

    public Coordinate getCoordinate() {
        return coordinates;
    }

    public void setCoordinate(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isExecutefailer() {
        return executefailer;
    }

    public void setExecutefailer(boolean executefailer) {
        this.executefailer = executefailer;
    }



}


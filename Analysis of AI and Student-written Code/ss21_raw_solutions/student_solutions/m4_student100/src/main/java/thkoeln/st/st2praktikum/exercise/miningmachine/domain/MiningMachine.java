package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import com.jayway.jsonpath.internal.function.numeric.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiningMachine extends AbstractEntity implements SpawnmachineInterface, ExecutecommandInterface, Directionmoveable, Connectionmoveable {



    private UUID fieldid;

    private String name;

    private boolean executefailer = false;



    @Id
    @Getter
    private UUID miningmachineuuid = UUID.randomUUID();

    public UUID getId() {
        return miningmachineuuid;
    }

    public void setId(UUID id) {
        this.miningmachineuuid = id;
    }

    @Getter
    @ElementCollection
    private List<Order> orderlist = new ArrayList<Order>();



    @Embedded
    private Coordinate coordinates = Coordinate.fromString("(0,0)");


    public MiningMachine(String name, UUID miningmachineuuid) {
        this.name = name;
        this.miningmachineuuid = miningmachineuuid;

    }




    @Override
    public Coordinate executeCommand(OrderType moveCommand, Integer steps, UUID uuid,
                                     MiningMachinelist miningMachineList,
                                     MiningMachineRepository miningMachineRepository, UUID fieldwhererobotisplacedon, UUID miningmachineuuid,
                                     Machinecoordinateshashmap machinecoordinateshashmap, FieldRepository fieldRepository,
                                     TransportCategoryRepository transportCategoryRepository) {

        switch (moveCommand) {
            case ENTER:
                coordinates = spawnMiningmachine(uuid, miningMachineList,  miningMachineRepository, miningmachineuuid, machinecoordinateshashmap);
                break;

            case TRANSPORT:
                coordinates = moveInconnection(miningmachineuuid, miningMachineList, miningMachineRepository,fieldwhererobotisplacedon,transportCategoryRepository);
                break;

            case NORTH:
                coordinates = moveNorth(steps,  miningMachineList , miningMachineRepository, fieldwhererobotisplacedon, fieldRepository);
                break;

            case SOUTH:
                coordinates = moveSouth(steps, miningMachineList , miningMachineRepository, fieldwhererobotisplacedon, fieldRepository);
                break;

            case WEST:
                coordinates = moveWest(steps, miningMachineList,
                        miningMachineRepository, fieldwhererobotisplacedon, fieldRepository);
                break;

            case EAST:
                coordinates = moveEast(steps, miningMachineList ,
                        miningMachineRepository, fieldwhererobotisplacedon, fieldRepository);
                break;
        }
        return coordinates;
    }


    @Override
    public Coordinate spawnMiningmachine(UUID fielduuid,   MiningMachinelist miningMachineList,
                                         MiningMachineRepository miningMachineRepository, UUID miningmachineuuid,
                                         Machinecoordinateshashmap machinecoordinateshashmap) {


        boolean breaker = false;




        int[] tempcoordinates = machinecoordinateshashmap.getMiningmachinecoordinatehashmap().
        get(miningMachineList.getMiningmachinelist().get(0).miningmachineuuid);

        for (MiningMachine miningMachine : miningMachineList.getMiningmachinelist()) {
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
            machinecoordinateshashmap.getMiningmachinecoordinatehashmap()
                    .put(miningMachineList.getMiningmachinelist().get(0).miningmachineuuid, temptempcoordinates);
        }
        fieldid = fielduuid;
        for(MiningMachine machine: miningMachineList.getMiningmachinelist()) {
            if(machine.getId() == miningmachineuuid) {
                miningMachineRepository.save(machine);
            }
        }

        return coordinates;
    }

    @Override
    public Coordinate moveNorth(Integer stringsteps, MiningMachinelist miningMachineList,
                                MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository
                               ) {

       Iterable<Field> fields = fieldRepository.findAll();
        boolean breaker = false;
        for (Field field : fields) {
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
                        coordinates =  Coordinate.fromString("("+ coordinates.getX() + ","+ (coordinates.getY()+1)+ ")" );

                    }
                }
            }
        }
        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }

    @Override
    public Coordinate moveSouth(Integer stringsteps, MiningMachinelist miningMachineList,
                                MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository) {
        Iterable<Field> fields = fieldRepository.findAll();
        boolean breaker = false;
        for (Field field : fields) {
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

                        coordinates =  Coordinate.fromString("("+ coordinates.getX() + ","+ (coordinates.getY()-1)+ ")" );

                    }
                }
            }
        }


        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }

    @Override
    public Coordinate moveEast(Integer stringsteps, MiningMachinelist miningMachineList,
                               MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository) {
        Iterable<Field> fields = fieldRepository.findAll();
        boolean breaker = false;
        for (Field field : fields) {
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
                        coordinates = Coordinate.fromString("("+ (coordinates.getX()+1) + ","+ coordinates.getY()+ ")" );

                    }
                }
            }
        }
        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }

    @Override
    public Coordinate moveWest(Integer stringsteps, MiningMachinelist miningMachineList,
                               MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository) {

        Iterable<Field> fields = fieldRepository.findAll();
        boolean breaker = false;
        for (Field field : fields) {
            if (field.getFielduuid().equals(fieldwhereRobotisplacedon)) {
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
                        coordinates = Coordinate.fromString("("+ (coordinates.getX()-1) + ","+ coordinates.getY()+ ")" );

                    }
                }
            }
        }


        fieldid = fieldwhereRobotisplacedon;
        return coordinates;
    }


    @Override
    public Coordinate moveInconnection(UUID miningmachineuuid, MiningMachinelist miningMachineList,
                                       MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon, TransportCategoryRepository transportCategoryRepository
            ) {

       Iterable<TransportCategory> transportCategories = transportCategoryRepository.findAll();
    Boolean breaker = true;
        for (TransportCategory transportCategory :  transportCategories ) {
            for (Connection connection : transportCategory.getListConnection()) {
                executefailer = coordinates.equals(connection.getSourceCoordinate());
                if (isExecutefailer() && breaker) {
                    coordinates = connection.getDestinationCoordinate();
                    fieldid = connection.getDestinationFieldid();
                    breaker = false;
                }
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


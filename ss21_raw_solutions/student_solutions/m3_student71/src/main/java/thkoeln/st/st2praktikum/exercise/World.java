package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
public class World {

    @Id
    @GeneratedValue
    Long id;

    @Getter
    @OneToMany
    private final List<Field> fieldList = new ArrayList<>();

    @Getter
    @OneToMany
    private final List<MiningMachine> miningMachineList = new ArrayList<>();

    public void addField(Field field){
        fieldList.add(field);
    }

    public Field getField(UUID uuid){
        return fieldList.stream().filter(f -> f.uuid.equals(uuid)).findFirst().orElse(null);
    }

    public void putMiningMachine(UUID uuid, MiningMachine miningMachine){
        miningMachineList.add(miningMachine);
    }

    public MiningMachine getMiningMachine(UUID uuid){
        return miningMachineList.stream().filter(m -> m.uuid.equals(uuid)).findFirst().orElse(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void executeCommand(UUID miningMachineId, Command command) throws SpawnMiningMachineException {

        switch (command.getCommandType()) {
            case ENTER:
                var field = fieldList.stream().filter(f -> f.uuid.equals(command.getGridId())).findFirst().orElse(null);
                field.spawnMiningMachine(getMiningMachine(miningMachineId) ,new Coordinate(0,0));
            break;

            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
            case TRANSPORT:
            default:
                throw new SpawnMiningMachineException();
        }

    }

}

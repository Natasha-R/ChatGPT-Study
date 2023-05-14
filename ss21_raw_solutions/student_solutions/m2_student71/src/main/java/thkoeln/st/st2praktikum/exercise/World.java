package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class World {

    private static World instance;

    private World(){
        instance = this;
    }

    public static World getInstance(){
        return (instance == null) ? new World() : instance;
    }

    @Getter
    private HashMap<UUID, Field> fieldList = new HashMap<UUID, Field>();
    private HashMap<UUID, MiningMachine> miningMachineList = new HashMap<UUID, MiningMachine>();

    public void putField(UUID uuid, Field field){
        fieldList.put(uuid, field);
    }

    public Field getField(UUID uuid){
        return fieldList.get(uuid);
    }

    public void putMiningMachine(UUID uuid, MiningMachine miningMachine){
        miningMachineList.put(uuid, miningMachine);
    }

    public MiningMachine getMiningMachine(UUID uuid){
        return miningMachineList.get(uuid);
    }
}

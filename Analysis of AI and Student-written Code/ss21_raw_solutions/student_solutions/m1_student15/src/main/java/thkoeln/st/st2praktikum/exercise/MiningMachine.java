package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine implements MiningMachineInterface {

    UUID id = UUID.randomUUID();
    String fieldId;
    String name;
    Coordinate currentPos;

    public MiningMachine(String name){
        this.name = name;
    }
    public UUID getId(){
        return id;
    }
    public MiningMachine getMiningMachine(){
        return this;
    }
}

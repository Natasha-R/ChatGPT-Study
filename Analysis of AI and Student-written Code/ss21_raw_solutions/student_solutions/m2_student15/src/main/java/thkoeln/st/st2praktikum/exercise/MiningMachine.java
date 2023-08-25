package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class MiningMachine implements MiningMachineInterface {

    private UUID id = UUID.randomUUID();
    private String fieldId;
    private String name;
    private Point currentPos;

    public MiningMachine(String name){
        this.name = name;
    }
    public MiningMachine getMiningMachine(){
        return this;
    }
}


package thkoeln.st.st2praktikum.exercise.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class MiningMachine {

    UUID miningMachineId;
    UUID fieldId;
    Point position;
    String name;
    public MiningMachine(String name) {
        this.miningMachineId = UUID.randomUUID();
        this.name=name;
    }

}

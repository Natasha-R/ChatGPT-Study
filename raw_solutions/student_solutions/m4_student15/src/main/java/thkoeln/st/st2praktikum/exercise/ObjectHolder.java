package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineCommands;

public class ObjectHolder {
    @Getter
    private static MiningMachineCommands miningMachineCommands = new MiningMachineCommands();
}

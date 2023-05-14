package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainComponent {
    @Autowired
    public static MachineDirections machineDirections;

    @Autowired
    public MainComponent(MachineDirections machineDirections) {
        this.machineDirections = machineDirections;
    }
}

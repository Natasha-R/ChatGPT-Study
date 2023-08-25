package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {
    @Autowired
    public static MiningMachineComponent miningMachineComponent;

    @Autowired
    public TestComponent(MiningMachineComponent miningMachineComponent) {
        TestComponent.miningMachineComponent = miningMachineComponent;
    }
}

package thkoeln.st.st2praktikum.exercise.Component;

public interface IConnection extends IComponentWithUUID {
    MiningMachine getMachineOnSourceField();
    MiningMachine getMachineOnDestinationField();
}

package thkoeln.st.st2praktikum.exercise;

public class TransportSystemFactory {
    public TransportSystem getTransportsystem(String system) {
        return new TransportSystem(system);
    }
}

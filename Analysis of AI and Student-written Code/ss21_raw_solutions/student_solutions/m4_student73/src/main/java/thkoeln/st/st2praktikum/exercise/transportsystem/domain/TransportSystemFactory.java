package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import thkoeln.st.st2praktikum.exercise.TransportSystem;

public class TransportSystemFactory {
    public TransportSystem getTransportsystem(String system) {
        return new TransportSystem(system);
    }
}

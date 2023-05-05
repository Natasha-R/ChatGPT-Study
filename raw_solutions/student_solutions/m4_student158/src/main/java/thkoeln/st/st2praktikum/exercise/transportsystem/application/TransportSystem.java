package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import thkoeln.st.st2praktikum.exercise.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class TransportSystem extends AbstractEntity {

    private String name;

    public static TransportSystem fromString(String name){
        return new TransportSystem(name);
    }

    public TransportSystem() {
    }

    private TransportSystem(String name) {
        this.name = name;
    }
}

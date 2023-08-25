package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;

@Entity
public class TransportSystem extends AbstractEntity{

    private String name;

    public static TransportSystem fromShit(String name){
        return new TransportSystem(name);
    }

    public TransportSystem() {
    }

    private TransportSystem(String name) {
        this.name = name;
    }
}

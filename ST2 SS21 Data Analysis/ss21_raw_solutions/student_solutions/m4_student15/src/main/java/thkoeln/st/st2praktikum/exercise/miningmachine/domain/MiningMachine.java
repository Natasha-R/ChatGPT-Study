package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.persistence.*;
import java.util.*;


@Getter
@Setter
@Entity
public class MiningMachine{

    @Id
    private UUID id = UUID.randomUUID();
    //@ManyToOne
    private UUID fieldId;
    private String name;
    @Embedded
    private Point point;
    @ElementCollection(targetClass = Task.class, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();


    public MiningMachine(){}
    public MiningMachine(String name){ this.name = name; }
    public MiningMachine getMiningMachine(){
        return this;
    }
}


package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


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

    public MiningMachine(){}
    public MiningMachine(String name){
        this.name = name;
    }
    public MiningMachine getMiningMachine(){
        return this;
    }
}

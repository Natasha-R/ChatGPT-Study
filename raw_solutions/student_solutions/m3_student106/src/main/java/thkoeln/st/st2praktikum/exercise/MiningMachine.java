package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Blockable;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.moveable.MiningMachineMovement;
import thkoeln.st.st2praktikum.exercise.moveable.Moveable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Setter
@Getter
public class MiningMachine implements Moveable, Blockable {
    @Id
    private final UUID uuid = UUID.randomUUID();

    private String name;
    @Embedded
    private Point point = new Point("(0,0)");

    private UUID fieldId;

    @ManyToOne
    public Field field;

    @Embedded
    private Task task;


    protected MiningMachine() {
    }

    public MiningMachine(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Point> getBlockedPoints() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(this.point);
        return list;
    }

    @Override
    public boolean move(Task task) {
        MiningMachineMovement movement = new MiningMachineMovement();
        return movement.move(task, uuid);
    }
}
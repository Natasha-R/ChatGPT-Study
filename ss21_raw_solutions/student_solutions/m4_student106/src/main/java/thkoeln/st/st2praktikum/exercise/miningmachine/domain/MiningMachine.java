package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Blockable;
import thkoeln.st.st2praktikum.exercise.Moveable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @ElementCollection(targetClass = Task.class)
    private List<Task> tasks = new ArrayList<>();


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

    public void addTask(Task task){
        tasks.add(task);
    }

    public void deleteTaskHistory(){
        ArrayList<Task> newList = new ArrayList<>();
        tasks = newList;
    }
}
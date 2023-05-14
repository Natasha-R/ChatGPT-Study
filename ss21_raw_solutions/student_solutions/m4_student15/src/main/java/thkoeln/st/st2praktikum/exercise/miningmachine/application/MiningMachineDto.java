package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiningMachineDto {
    UUID fieldId;
    String name;
    Point point;
    List<Task> tasks;
}
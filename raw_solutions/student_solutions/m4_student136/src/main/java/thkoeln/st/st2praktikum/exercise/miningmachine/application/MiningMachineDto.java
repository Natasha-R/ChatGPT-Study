package thkoeln.st.st2praktikum.exercise.miningmachine.application;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiningMachineDto
{
    private UUID uuid;
    private String name;
    private Field grid;
    private Vector2D coordinate;
}
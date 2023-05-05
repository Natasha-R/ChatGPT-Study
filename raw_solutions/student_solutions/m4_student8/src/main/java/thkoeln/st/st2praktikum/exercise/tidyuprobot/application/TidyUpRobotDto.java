package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobotDto
{
    private UUID robotID;

    private String name;
}

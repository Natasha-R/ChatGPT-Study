package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TidyUpRobotDto implements Serializable
{
    private UUID ID;
    private String name;

    public UUID getId(){
        return ID;
    }

    public String getName(){
        return name;
    }
}

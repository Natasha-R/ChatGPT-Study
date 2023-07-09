package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CleaningDeviceDto {
    private String name;
    private Point coordinates;
}

package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractDto;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2DDto;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

/**
 * javaDoc
 *
 * @author gloewen
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CleaningDeviceDto extends AbstractDto<CleaningDevice> {

    private String id;
    private String name;
    private Vector2DDto vector2D;
    private Space space;

}

package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractDto;

/**
 * javaDoc
 *
 * @author gloewen
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vector2DDto extends AbstractDto<Vector2D> {
    private int x;
    private int y;
}

package thkoeln.st.st2praktikum.exercise.space.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractDto;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2DDto;
import thkoeln.st.st2praktikum.exercise.space.domain.transition.BlockedTransition;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockedTransitionDto extends AbstractDto<BlockedTransition> {

    private String uuid;
    private Vector2DDto fromVector2D;
    private Vector2DDto toVector2D;

}

package thkoeln.st.st2praktikum.exercise.space.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractDto;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceDto extends AbstractDto<Space> {

    private String uuid;
    private int width;
    private int height;
    private BlockedTransitionDto[] blockedTransitions;

}

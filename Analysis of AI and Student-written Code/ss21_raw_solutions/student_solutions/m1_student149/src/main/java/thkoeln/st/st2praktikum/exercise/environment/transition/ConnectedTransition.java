package thkoeln.st.st2praktikum.exercise.environment.transition;

import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;

public class ConnectedTransition extends Transition<EnvironmentPosition> {

    public ConnectedTransition(EnvironmentPosition fromEnvironmentPosition, EnvironmentPosition toEnvironmentPosition) {
        this.fromPosition = fromEnvironmentPosition;
        this.toPosition = toEnvironmentPosition;
    }

}

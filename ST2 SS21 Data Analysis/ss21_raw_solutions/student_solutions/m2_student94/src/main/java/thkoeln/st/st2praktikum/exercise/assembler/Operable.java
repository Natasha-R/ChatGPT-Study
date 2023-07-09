package thkoeln.st.st2praktikum.exercise.assembler;

import thkoeln.st.st2praktikum.exercise.cleaningDevice.Instructable;
import thkoeln.st.st2praktikum.exercise.assembler.Queryable;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;

public interface Operable extends Queryable {
    void addSpace(SpaceManageable space);
    void addCleaningDevice(Instructable cleaningDevice);
}

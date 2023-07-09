package thkoeln.st.st2praktikum.exercise.world2;

import thkoeln.st.st2praktikum.exercise.world2.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.world2.field.Field;
import thkoeln.st.st2praktikum.exercise.world2.miningMaschine.Miningmaschine;

import java.util.HashMap;
import java.util.UUID;

public interface Cloud {
    HashMap<UUID, Miningmaschine> getMiningmaschineHashMap();
    HashMap<UUID, Field> getFieldHashMap();
}

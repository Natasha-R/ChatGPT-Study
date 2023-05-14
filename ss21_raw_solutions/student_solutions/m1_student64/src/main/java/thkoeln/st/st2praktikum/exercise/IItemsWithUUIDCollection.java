package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.ICollection;
import thkoeln.st.st2praktikum.exercise.World.IHasUUID;

import java.util.UUID;

public interface IItemsWithUUIDCollection<T extends IHasUUID> extends ICollection<T> {
    T findByUUID(UUID itemUUID);
}

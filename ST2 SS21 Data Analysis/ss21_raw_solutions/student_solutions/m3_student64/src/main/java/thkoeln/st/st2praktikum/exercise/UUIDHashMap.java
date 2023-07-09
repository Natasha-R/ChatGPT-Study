package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class UUIDHashMap<T extends IHasUUID> {
    HashMap<UUID, T> hashMap = new HashMap<>();

    public T get(UUID elementUUID)
    {
        return this.hashMap.get(elementUUID);
    }

    public T get(String elementUUID)
    {
        return this.get(UUID.fromString(elementUUID));
    }

    public void add(T uuidElement)
    {
        this.hashMap.put(uuidElement.getUUID(), uuidElement);
    }

    public Iterator<T> iterator() {
        return this.hashMap.values().iterator();
    }
}

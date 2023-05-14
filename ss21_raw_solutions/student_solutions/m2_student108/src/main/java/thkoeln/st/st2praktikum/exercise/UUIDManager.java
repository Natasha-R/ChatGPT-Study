package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Component.IComponentWithUUID;

import java.util.ArrayList;
import java.util.UUID;

public class UUIDManager {
    ArrayList<IComponentWithUUID> UUIDToObject;

    public UUIDManager () {
        UUIDToObject = new ArrayList();
    }

    public void addGameComponent(IComponentWithUUID gameComponent) {
        UUIDToObject.add(gameComponent);
    }

    public IComponentWithUUID getObjectFromUUID(UUID id) {
        for (int index=0; index<UUIDToObject.size(); index++) {
            if (UUIDToObject.get(index).getUUID() == id || UUIDToObject.get(index).getUUID().toString().equals(id.toString())) {
                return UUIDToObject.get(index);
            }
        }
        return null;
    }

    public UUID getUUIDFromObject(IComponentWithUUID gameComponent) {
        for (int index=0; index<UUIDToObject.size(); index++) {
            if (UUIDToObject.get(index) == gameComponent) {
                return UUIDToObject.get(index).getUUID();
            }
        }
        return null;
    }
}

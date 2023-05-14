package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.FieldComponent.IComponent;

import java.util.ArrayList;
import java.util.UUID;

public class UUIDManager implements IUUIDManager {
    public final ArrayList<IComponent> UUIDToObject;

    public UUIDManager () {
        UUIDToObject = new ArrayList();
    }

    @Override
    public void rememberObject(IComponent gameComponent) {
        UUIDToObject.add(gameComponent);
    }

    @Override
    public IComponent getObjectFromUUID(UUID id) {
        for (int index=0; index<UUIDToObject.size(); index++) {
            if (UUIDToObject.get(index).getUUID() == id) {
                return UUIDToObject.get(index);
            }
        }
        return null;
    }

    @Override
    public IComponent getObjectFromUUIDAsString(String id) {
        for (int index=0; index<UUIDToObject.size(); index++) {
            if (UUIDToObject.get(index).getUUID().toString().equals(id)) {
                return UUIDToObject.get(index);
            }
        }
        return null;
    }
}

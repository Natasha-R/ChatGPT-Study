package thkoeln.st.st2praktikum.exercise.exception;

import java.util.UUID;
import java.util.function.Supplier;

public class UUIDElementNotFoundException extends IllegalArgumentException {

    public UUIDElementNotFoundException(Class clazz, UUID uuid) {
        super("There is no " + clazz.getSimpleName() + " with id: " + uuid + " in this service");
    }

    public static Supplier<UUIDElementNotFoundException> getExceptionSupplier(Class clazz, UUID uuid) {
        return () -> new UUIDElementNotFoundException(clazz, uuid);
    }
}

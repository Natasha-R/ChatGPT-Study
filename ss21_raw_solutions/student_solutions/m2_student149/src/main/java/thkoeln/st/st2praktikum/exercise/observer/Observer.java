package thkoeln.st.st2praktikum.exercise.observer;

public interface Observer<T> {
    void update(T oldValue, T newValue);
}

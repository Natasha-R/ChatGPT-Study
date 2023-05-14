package thkoeln.st.st2praktikum.exercise.observer;

import java.util.ArrayList;
import java.util.List;

public interface Observable<T> {

    List<Observer> observers = new ArrayList<>();

    default void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }

    default void unregisterObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    default void notifyObservers(T oldValue, T newValue) {
        observers.forEach(observer -> observer.update(oldValue, newValue));
    }

}

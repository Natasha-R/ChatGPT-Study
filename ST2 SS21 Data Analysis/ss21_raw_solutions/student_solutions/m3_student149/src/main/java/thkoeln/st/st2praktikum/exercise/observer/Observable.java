package thkoeln.st.st2praktikum.exercise.observer;

public interface Observable {

    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void notifyObservers();

}

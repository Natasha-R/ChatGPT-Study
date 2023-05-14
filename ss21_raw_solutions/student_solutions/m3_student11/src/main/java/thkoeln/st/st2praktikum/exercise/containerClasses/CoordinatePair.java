package thkoeln.st.st2praktikum.exercise.containerClasses;

import thkoeln.st.st2praktikum.exercise.Coordinate;

public class CoordinatePair implements ObjectPair {
    private final Coordinate first;
    private final Coordinate second;

    public CoordinatePair(Coordinate first, Coordinate second){
        this.first = first;
        this.second = second;
    }


    public Coordinate getFirst(){
        return this.first;
    }

    public Coordinate getSecond(){
        return this.second;
    }
}

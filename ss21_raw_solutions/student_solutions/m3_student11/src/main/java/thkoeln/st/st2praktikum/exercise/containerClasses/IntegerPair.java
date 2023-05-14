package thkoeln.st.st2praktikum.exercise.containerClasses;


public class IntegerPair implements ObjectPair {
    private final Integer first;
    private final Integer second;

    public IntegerPair(Integer first, Integer second){
        this.first = first;
        this.second = second;
    }

    public Integer getFirst(){ return first; }

    public Integer getSecond(){ return second; }
}

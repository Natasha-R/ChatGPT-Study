package thkoeln.st.st2praktikum.exercise;

public interface Blockable
{
    boolean blocksMovementInDirection(int[] coordinates, Command direction, Field fieldInQuestion);
}
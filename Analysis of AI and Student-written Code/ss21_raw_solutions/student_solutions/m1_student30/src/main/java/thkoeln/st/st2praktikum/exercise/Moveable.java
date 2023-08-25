package thkoeln.st.st2praktikum.exercise;

public interface Moveable {


    void moveNorth(int moves, ShipDeck ShipDeck);
    void moveEast(int moves, ShipDeck ShipDeck);
    void moveSouth(int moves, ShipDeck ShipDeck);
    void moveWest(int moves, ShipDeck ShipDeck);
}
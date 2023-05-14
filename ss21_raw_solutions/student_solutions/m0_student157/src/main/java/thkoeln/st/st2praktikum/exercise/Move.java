package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Move {

    public int positionX;
    public int positionY;

    public Direction direction;

    public Move(int positionX, int positionY, Direction direction) {
        this.positionY = positionY;
        this.positionX = positionX;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return positionX == move.positionX && positionY == move.positionY && direction == move.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY, direction);
    }
}

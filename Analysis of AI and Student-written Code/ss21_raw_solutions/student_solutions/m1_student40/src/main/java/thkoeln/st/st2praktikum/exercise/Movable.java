package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

enum moveType {
    moveNorth,
    moveSouth,
    moveWest,
    moveEast
}

public abstract class Movable extends Entity {
    @Getter
    protected Coordinate position = new Coordinate(0,0);

    public void move(moveType type, int amount) {
        switch (type) {
            case moveEast: {
                this.position.move(amount, 0);
                break;
            }
            case moveWest: {
                this.position.move(-amount, 0);
                break;
            }
            case moveSouth: {
                this.position.move(0, -amount);
                break;
            }
            case moveNorth: {
                this.position.move(0, amount);
                break;
            }
        }
    }
}

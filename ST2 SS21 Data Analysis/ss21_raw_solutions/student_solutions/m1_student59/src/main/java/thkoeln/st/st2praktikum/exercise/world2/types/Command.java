package thkoeln.st.st2praktikum.exercise.world2.types;

import java.util.UUID;

public class Command {

    private final Direction direction;
    private final Integer power;
    private final UUID uuid;

    public Command(Direction direction,Integer power) {
        this.direction = direction;
        this.power = power;
        this.uuid = null;
    }

    public Command(Direction direction, UUID uuid) {
        this.uuid = uuid;
        this.direction = direction;
        this.power = null;
    }

    public UUID getUUID() throws NullPointerException{
        return this.uuid;
    };
    public Integer getPower() throws NullPointerException{
        return this.power;
    }
    public Direction getDirection(){
        return this.direction;
    }

    @Override
    public String toString() {
        return "Command{" +
                "direction=" + direction +
                ", power=" + power +
                ", uuid=" + uuid +
                '}';
    }
}


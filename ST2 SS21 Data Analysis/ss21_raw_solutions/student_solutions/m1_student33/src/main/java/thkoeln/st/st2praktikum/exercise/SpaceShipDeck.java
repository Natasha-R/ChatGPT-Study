package thkoeln.st.st2praktikum.exercise;

import lombok.Data;

import java.util.UUID;

@Data
public class SpaceShipDeck {
    private int width;
    private int height;
    private UUID uuid;

    public SpaceShipDeck(int height, int width){
        uuid = UUID.randomUUID();
        this.width = width;
        this.height = height;
    }
}

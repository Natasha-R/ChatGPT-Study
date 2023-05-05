package thkoeln.st.st2praktikum.exercise;
import java.util.UUID;

public class Deck {

    private final UUID fieldID;
    private final int fieldwidth;
    private final int fieldheight;


    public Deck(int width, int height) {
        this.fieldheight = height;
        this.fieldwidth = width;
        this.fieldID = UUID.randomUUID();
    }


    public int getFieldWidth() {
        return fieldwidth;
    }

    public int getFieldHeight() {
        return fieldheight;
    }

    public UUID getFieldID() {
        return fieldID;
    }


}
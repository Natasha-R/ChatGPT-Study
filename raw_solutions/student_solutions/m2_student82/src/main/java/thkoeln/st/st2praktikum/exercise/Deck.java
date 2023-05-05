package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Deck implements DeckController, UUidable, Dimensions {
    private final UUID deckId;
    private final Integer height;
    private final Integer width;

    final private HashMap<UUID, Field> fieldList;
    final ArrayList<WallTranslator> wallList = new ArrayList<>();



    public Deck(Integer height, Integer width) {
        this.deckId = UUID.randomUUID();
        this.height = height;
        this.width = width;
        this.fieldList = new HashMap<>();
        this.createField();
    }


    private void createField(){
        for(int y = 0; y < this.width + 1; y++){
            for(int x = 0; x < this.height + 1; x++ ){
                Field field = new Field(this.deckId, new Coordinate(x, y));
                this.fieldList.put(field.getID(), field);
            }
        }
    }

    @Override
    public UUID getID() {
        return this.deckId;
    }

    @Override
    public Deck getDeck() {
        return this;
    }


    public HashMap<UUID, Field> getFieldList(){
        return this.fieldList;
    }

    public Field getFieldFromCoordinate(Coordinate coordinate) throws NoFieldException {
        AtomicReference<Field> result = new AtomicReference<>();

        this.fieldList.forEach((key, element) -> {
            if(element.getCoordinate().equals(coordinate)){
                result.set(element);
            }
        });
        if (result.get() != null) {
            return result.get();
        }else{
            throw new NoFieldException(coordinate.toString());
        }

    }

    public ArrayList<WallTranslator> getWallList() {
        return wallList;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidht() {
        return this.width;
    }
}

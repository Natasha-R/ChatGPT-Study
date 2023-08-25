package thkoeln.st.st2praktikum.exercise.world2.field;

import thkoeln.st.st2praktikum.exercise.world2.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoSquareFoundException;
import thkoeln.st.st2praktikum.exercise.world2.services.UUidable;
import thkoeln.st.st2praktikum.exercise.world2.square.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Field implements UUidable,FieldController, FieldDimensions{

    private final UUID fieldId;
    private final Integer height;
    private final Integer width;
    private final HashMap<UUID, Square> squareHashMap;
    private final ArrayList<Barrier> barrierList;


    public Field(Integer height, Integer width){
        this.fieldId = UUID.randomUUID();
        this.height = height;
        this.width = width;
        this.squareHashMap = new HashMap<>();
        this.barrierList = new ArrayList<>();
        this.createSquares();
    }

    private void createSquares() {
        for(int y=0;y<this.height;y++){
            for(int x=0;x<this.width;x++){
                final Square square = new Square(this.fieldId ,new Coordinate(x,y));
                this.squareHashMap.put(square.getId(), square);
            }
        }
    }

    @Override
    public UUID getId() {
        return this.fieldId;
    }

    @Override
    public Field getField() {
        return this;
    }

    @Override
    public HashMap<UUID,Square> getSquareHashmap() {
        return this.squareHashMap;
    }

    @Override
    public ArrayList<Barrier> getBarrierList() {
        return this.barrierList;
    }

    @Override
    public Square getSquareFromCoordinate(Coordinate coordinate) throws NoSquareFoundException {
        AtomicReference<Square> result = new AtomicReference<>();
        this.squareHashMap.forEach((key, value) -> {
            if(value.getCoordinate().toString().equals(coordinate.toString())){
                result.set(value);
            }
        });
        if(result.get() != null){
            return result.get();
        }else{
            throw new NoSquareFoundException(result.get().getCoordinate().toString());
        }
    }


    @Override
    public Integer getHeight() {
        return this.height;
    }

    @Override
    public Integer getWidth() {
        return this.width;
    }
}

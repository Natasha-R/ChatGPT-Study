package thkoeln.st.st2praktikum.exercise.world2.barrier;

import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.services.CoordinateService;
import thkoeln.st.st2praktikum.exercise.world2.services.UUidable;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Barrier implements UUidable, BarrierController {

    private final UUID barrierId;
    private final UUID fieldId;
    private final ArrayList<Coordinate> barrierCoordinates;

    public Barrier(UUID fieldId, String barrierString){
        this.fieldId = fieldId;
        this.barrierId = UUID.randomUUID();
        this.barrierCoordinates = new ArrayList<>();
        this.createBarrier(barrierString);

    }

    @Override
    public String toString() {
        return "Barrier{" +
                "barrierId=" + barrierId +
                ", fieldId=" + fieldId +
                ", barrierCoordinates=" + barrierCoordinates +
                '}';
    }

    void createBarrier(String barrierString){
        String[] barrierItem = barrierString.split("-");
        Coordinate barrierStart = CoordinateService.stringToCoordinate(barrierItem[0]);
        Coordinate barrierEnd = CoordinateService.stringToCoordinate(barrierItem[1]);

        if(barrierStart.getXAxis().equals(barrierEnd.getXAxis())){
            for (int i = barrierStart.getYAxis(); i < barrierEnd.getYAxis()+1; i++) {
                String barrierListItem = "("+barrierStart.getXAxis()+","+i+")";
                this.getBarrierCoordinates().add(CoordinateService.stringToCoordinate(barrierListItem));
            }
        }
        if(barrierStart.getYAxis().equals(barrierEnd.getYAxis())){
            for (int i = barrierStart.getXAxis(); i < barrierEnd.getXAxis()+1; i++) {
                String barrierListItem = "("+i+","+barrierStart.getYAxis()+")";
                this.getBarrierCoordinates().add(CoordinateService.stringToCoordinate(barrierListItem));
            }
        }
    }

    @Override
    public UUID getId() {
        return this.barrierId;
    }

    @Override
    public Barrier getBarrier() {
        return this;
    }

    @Override
    public UUID getFieldId() {
        return this.fieldId;
    }

    @Override
    public ArrayList<Coordinate> getBarrierCoordinates() {
        return this.barrierCoordinates;
    }

    @Override
    public boolean containsMaybeBarrier(Coordinate maybeBarrierStartCoordinates, Coordinate maybeBarrierEndCoordinates) {
        AtomicBoolean result = new AtomicBoolean(false);
        AtomicInteger count = new AtomicInteger();
        this.barrierCoordinates.forEach(coordinate -> {
            if(coordinate.toString().equals(maybeBarrierStartCoordinates.toString())){
                AtomicInteger startIndex = count;
                if(startIndex.get() + 1 < this.barrierCoordinates.size()){
                    result.set(this.barrierCoordinates.get(startIndex.get() + 1).toString().equals(maybeBarrierEndCoordinates.toString()));
                }
            }
            count.getAndIncrement();
        });
        return result.get();

    }
}

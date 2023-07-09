package thkoeln.st.st2praktikum.exercise.field.domain;


import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.GeometricPositionDesignations;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
public class Field {

    @Id
    private final UUID fieldId = UUID.randomUUID();

    private Integer width;

    private Integer height;

    @Transient
    private final List<Barrier> barriers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private final List<Point> points = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    private final Map<UUID, Connection> connections = new HashMap<>();


    public Field(Integer height, Integer width){
        this.width = width;
        this.height = height;
        this.fillPoints();
    }

    protected Field(){}

    public void addBarrier(Barrier barrier) {
        this.barriers.add(barrier);
    }
    public void addConnection(Connection connection) throws RuntimeException {
        this.connections.put(connection.getConnectionId(), connection);
    }

    public Boolean checkHorizontalBarriers(Point miningmaschinePoint, OrderType orderType) throws IllegalStateException {
        switch (orderType){
            case NORTH:
                if((miningmaschinePoint.getY()+1) == this.height){
                    return false;
                }
                break;
            case SOUTH:
                if((miningmaschinePoint.getY()-1) < 0){
                    return false;
                }
                break;
        }
        for (Barrier barrier: this.barriers) {
            if(barrier.getGeometricPositionDesignations().equals(GeometricPositionDesignations.HORIZONTAL)){
                switch (orderType){
                    case NORTH:
                        if((miningmaschinePoint.getY()+1) == barrier.getStart().getY()){
                            return miningmaschinePoint.getX() < barrier.getStart().getX() || miningmaschinePoint.getX() >= barrier.getEnd().getX();
                        }else{
                            return true;
                        }
                    case SOUTH:
                        if((miningmaschinePoint.getY()-1) < 0){
                            return false;
                        }
                        if(miningmaschinePoint.getY().equals(barrier.getStart().getY())){
                            return miningmaschinePoint.getX() < barrier.getStart().getX() || miningmaschinePoint.getX() >= barrier.getEnd().getX();
                        }else{
                            return true;
                        }
                }
            }
        }
        return true;
    }

    public Boolean checkVerticalBarriers(Point miningmaschinePoint, OrderType orderType) throws IllegalStateException {
        switch (orderType){
            case EAST:
                if((miningmaschinePoint.getX()+1) == this.width){
                    return false;
                }
                break;
            case WEST:
                if((miningmaschinePoint.getX()-1) < 0){
                    return false;
                }
                break;
        }
        for (Barrier barrier: this.barriers) {
            if(barrier.getGeometricPositionDesignations().equals(GeometricPositionDesignations.VERTICAL)){
                switch (orderType){
                    case EAST:
                        if((miningmaschinePoint.getX()+1) == barrier.getStart().getX()){
                            return miningmaschinePoint.getY() < barrier.getStart().getY() || miningmaschinePoint.getY() >= barrier.getEnd().getY();
                        }else{
                            return true;
                        }
                    case WEST:
                        if(miningmaschinePoint.getX().equals(barrier.getStart().getX())){
                            return miningmaschinePoint.getY() < barrier.getStart().getY() || miningmaschinePoint.getY() >= barrier.getEnd().getY();
                        }else{
                            return true;
                        }
                }
            }
        }
        return true;
    }

    public Boolean checkMiningmaschinesInDirection(Point point, MiningMachineRepository miningMachineRepository, OrderType orderType, UUID miningmschineId) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        miningMachineRepository.findAll().forEach(miningmaschineEntry -> {
            if(miningmaschineEntry.getSpawned() && !miningmaschineEntry.getMiningmaschineId().equals(miningmschineId)){
                switch (orderType){
                    case NORTH:
                        result.set(miningmaschineEntry.getFieldId().equals(this.fieldId) && miningmaschineEntry.getPoint().equals(new Point(point.getX(), (point.getY()) + 1)));
                        break;
                    case EAST:
                        result.set(miningmaschineEntry.getFieldId().equals(this.fieldId) && miningmaschineEntry.getPoint().equals(new Point((point.getX()+1), point.getY())));
                        break;
                    case SOUTH:
                        result.set(miningmaschineEntry.getFieldId().equals(this.fieldId) && miningmaschineEntry.getPoint().equals(new Point(point.getX(), (point.getY())-1)));
                        break;
                    case WEST:
                        result.set(miningmaschineEntry.getFieldId().equals(this.fieldId) && miningmaschineEntry.getPoint().equals(new Point((point.getX()-1), point.getY())));
                        break;
                }
            }
        });
        return result.get();
    }

    private void fillPoints(){
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                points.add(new Point(x,y));
            }
        }
    }
}

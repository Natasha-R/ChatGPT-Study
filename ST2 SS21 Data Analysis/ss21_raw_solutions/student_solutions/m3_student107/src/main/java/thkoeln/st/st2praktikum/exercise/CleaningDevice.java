package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
public class CleaningDevice implements Unique, Movable {


    public UUID getId() {
        return this.id;
    }

    public Coordinate getCoordinate() {
        return this.CleaningDeviceCoordinate;
    }

    public String getName() {
        return this.name;
    }

    public UUID getSpaceId() {
        return this.spaceID;
    }

    public Space getSpace() {return this.space;}

    @Id
    private UUID id;

    private UUID spaceID;

    @OneToOne
    private Space space;
    @Embedded
    private Coordinate CleaningDeviceCoordinate;

    private String name;

    private boolean collideable;

    public CleaningDevice(){
        this.collideable = true;
        this.id = UUID.randomUUID();
    }

    public CleaningDevice(String name){
        this();
        this.name = name;
    }

    public boolean canCollide(){
        return this.collideable;
    }



    public boolean command(Order order, ConnectionRepository connectionRepository, SpaceRepository spaceRepository){
        switch (order.getOrderType()){
            case TRANSPORT:
                return this.transport(connectionRepository, order.getGridId().toString(), spaceRepository);
            case NORTH:
                return this.moveNorth(order.getNumberOfSteps(),spaceRepository.findById(this.getSpaceId()).get());
            case EAST:
                return this.moveEast(order.getNumberOfSteps(),spaceRepository.findById(this.getSpaceId()).get());
            case SOUTH:
                return this.moveSouth(order.getNumberOfSteps(),spaceRepository.findById(this.getSpaceId()).get());
            case WEST:
                return this.moveWest(order.getNumberOfSteps(),spaceRepository.findById(this.getSpaceId()).get());
            case ENTER:
                return this.enable(spaceRepository.findById(order.getGridId()).get());
            default:
                return false;
        }
    };






    public boolean transport(ConnectionRepository connectionRepository, String destination , SpaceRepository spaceRepository){
        HashMap<UUID,Space> spaceHashMap = new HashMap<UUID,Space>();
        HashMap<UUID,Connection> connectionHashMap = new HashMap<UUID,Connection>();
        spaceRepository.findAll().iterator().forEachRemaining(
                x -> spaceHashMap.put(x.getId(),x)
        );
        connectionRepository.findAll().iterator().forEachRemaining(
                x -> connectionHashMap.put(x.getId(),x)
        );



        List<Connection> validconnection = connectionHashMap.entrySet().stream().map(Map.Entry::getValue)
            .filter(x -> x.getDestinationSpaceId().equals(UUID.fromString(destination)))
            .filter(x -> x.getSourceSpaceId().equals(this.getSpaceId()))
            .filter(x -> x.getSourceCoordinate().equals(this.getCoordinate()))
            .collect(Collectors.toList());
        if(validconnection.size()>0){
            List<CleaningDevice> otherdevices = spaceHashMap.get(UUID.fromString(destination)).getCleaningDeviceList().stream()
                .filter(x -> x.getCoordinate().equals(validconnection.get(0).getDestinationCoordinate()))
                .collect(Collectors.toList());
            if(otherdevices.size()>0)
                return false;
            else{
                this.getSpace().getCleaningDeviceList().remove(this);
                spaceHashMap.get(this.getSpaceId()).getCleaningDeviceList().remove(this);
                spaceHashMap.get(UUID.fromString(destination)).getCleaningDeviceList().add(this);
                this.spaceID = validconnection.get(0).getDestinationSpaceId();
                this.CleaningDeviceCoordinate = new Coordinate(validconnection.get(0).getDestinationCoordinate());
            return true;
            }
        }else return false;
    }

    public boolean moveWest(Integer distanceParameter, Space space){
        if(this.spaceID.compareTo(space.getId()) == 0) {
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isVertical)
                    .filter(obstacle -> obstacle.inYrange(this.getCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getId().compareTo(this.getId()) != 0)
                    .filter(cleaningDevice -> cleaningDevice.getCoordinate().shareYAxis(this.getCoordinate()))
                    .collect(Collectors.toList());

            Coordinate tmpCoordinate = new Coordinate(this.getCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCoordinate());

            boolean isvalid = true;
            for (int step = 0; step < distanceParameter; step++) {
                try {
                    calcCoordinate = new Coordinate(tmpCoordinate.getX() - 1, tmpCoordinate.getY());
                } catch (Exception e) {
                    isvalid = false;

                }


                if (!space.inBounds(calcCoordinate)) isvalid = false;
                for (CleaningDevice colideable : collideableCleaningDevices
                ) {
                    if (colideable.getCoordinate().equals(calcCoordinate)) {
                        isvalid = false;
                        break;
                    }
                }
                for (Obstacle obstacle : collideableObstacles) {
                    if (obstacle.getStart().getX() - 1 == calcCoordinate.getX()) {
                        isvalid = false;
                        break;
                    }
                }
                if (!space.inBounds(calcCoordinate)) isvalid = false;
                if (isvalid) tmpCoordinate = new Coordinate(calcCoordinate);
                else break;
            }
            this.CleaningDeviceCoordinate = new Coordinate(tmpCoordinate);

        }
        return true;
    }


    public boolean moveEast(Integer distanceParameter, Space space){
        if(this.spaceID.compareTo(space.getId()) == 0){
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isVertical)
                    .filter(obstacle -> obstacle.inYrange(this.getCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getId().compareTo(this.getId()) != 0 )
                    .filter(cleaningDevice -> cleaningDevice.getCoordinate().shareYAxis(this.getCoordinate()))
                    .collect(Collectors.toList());
            Coordinate tmpCoordiante = new Coordinate(this.getCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCoordinate());
            boolean isvalid = true;

                for(int step = 0 ; step < distanceParameter ; step++ ){
                    try {
                    calcCoordinate = new Coordinate(tmpCoordiante.getX()+1 , tmpCoordiante.getY() );
                    } catch (Exception e) {
                        isvalid = false;

                    }

                    if(!space.inBounds(calcCoordinate)) isvalid = false;
                    for (CleaningDevice colideable : collideableCleaningDevices
                         ) {
                        if(colideable.getCoordinate().equals(calcCoordinate)){
                            isvalid = false;
                            break;
                        }
                    }
                    for (Obstacle obstacle: collideableObstacles) {
                        if(obstacle.getStart().getX() == calcCoordinate.getX()){
                            isvalid = false;
                            break;
                        }
                    }
                    if(!space.inBounds(calcCoordinate)) isvalid = false;
                    if(isvalid) tmpCoordiante = new Coordinate(calcCoordinate);
                    else break;
                }

            this.CleaningDeviceCoordinate = new Coordinate(tmpCoordiante);

        }
        return true;
    }


    public boolean moveNorth(Integer distanceParameter, Space space){
        if(this.spaceID.compareTo(space.getId()) == 0){
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isHorizontal)
                    .filter(obstacle -> obstacle.inXrange(this.getCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getId().compareTo(this.getId()) != 0 )
                    .filter(cleaningDevice -> cleaningDevice.getCoordinate().shareXAxis(this.getCoordinate()))
                    .collect(Collectors.toList());
            Coordinate tmpCoordiante = new Coordinate(this.getCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCoordinate());
            boolean isvalid = true;
                for(int step = 0 ; step < distanceParameter ; step++ ){
                    try {
                        calcCoordinate = new Coordinate(tmpCoordiante.getX() , tmpCoordiante.getY()+1 );
                    } catch (Exception e) {
                        isvalid = false;
                    }

                    if(!space.inBounds(calcCoordinate)) isvalid = false;
                    for (CleaningDevice colideable : collideableCleaningDevices
                    ) {
                        if(colideable.getCoordinate().equals(calcCoordinate)){
                            isvalid = false;
                            break;
                        }
                    }
                    for (Obstacle obstacle: collideableObstacles) {
                        if(obstacle.getStart().getY() == calcCoordinate.getY()){
                            isvalid = false;
                            break;
                        }
                    }
                    if(!space.inBounds(calcCoordinate)) isvalid = false;
                    if(isvalid) tmpCoordiante = new Coordinate(calcCoordinate);
                    else break;

            }
            this.CleaningDeviceCoordinate = new Coordinate(tmpCoordiante);

        }
        return true;
    }
    public boolean moveSouth(Integer distanceParameter, Space space){
        if(this.spaceID.compareTo(space.getId()) == 0){
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isHorizontal)
                    .filter(obstacle -> obstacle.inXrange(this.getCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getId().compareTo(this.getId()) != 0 )
                    .filter(cleaningDevice -> cleaningDevice.getCoordinate().shareXAxis(this.getCoordinate()))
                    .collect(Collectors.toList());

            Coordinate tmpCoordiante = new Coordinate(this.getCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCoordinate());

            boolean isvalid = true;

            for(int step = 0 ; step < distanceParameter ; step++ ){
                try {
                    calcCoordinate = new Coordinate(tmpCoordiante.getX() , tmpCoordiante.getY()-1 );
                } catch (Exception e) {
                    isvalid = false;
            }


                    if(!space.inBounds(calcCoordinate)) isvalid = false;
                    for (CleaningDevice colideable : collideableCleaningDevices
                    ) {
                        if(colideable.getCoordinate().equals(calcCoordinate)){
                            isvalid = false;
                            break;
                        }
                    }
                    for (Obstacle obstacle: collideableObstacles) {
                        if(obstacle.getStart().getY()-1 == calcCoordinate.getY()){
                            isvalid = false;
                            break;
                        }
                    }
                    if(!space.inBounds(calcCoordinate)) isvalid = false;
                    if(isvalid) tmpCoordiante = new Coordinate(calcCoordinate);
                    else break;
            }
            this.CleaningDeviceCoordinate = new Coordinate(tmpCoordiante);

        }
        return true;
    }

    public boolean enable(Space space){
        Coordinate spawn = new Coordinate(0,0);
        long colliding = space.getCleaningDeviceList().stream()
                .filter(cleaningDevice -> cleaningDevice.getCoordinate().equals(spawn))
                .count();
        if(colliding > 0) return false;
        else {
            this.spaceID = space.getId();
            this.space = space;
            this.CleaningDeviceCoordinate = spawn;
            space.getCleaningDeviceList().add(this);
            return true;
        }
    }


}

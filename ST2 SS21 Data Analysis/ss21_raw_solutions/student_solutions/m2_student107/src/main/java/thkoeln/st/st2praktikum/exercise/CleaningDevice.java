package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CleaningDevice implements Unique , Movable{

    public UUID getID() {
        return this.uuid;
    }

    public Coordinate getCleaningDeviceCoordinate() {
        return this.CleaningDeviceCoordinate;
    }

    public String getName() {
        return this.name;
    }

    public UUID getSpaceID() {
        return this.spaceID;
    }

    public Space getSpace() {return this.space;}

    private UUID uuid;
    private UUID spaceID;
    private Space space;
    private Coordinate CleaningDeviceCoordinate;
    private String name;
    private boolean collideable;
    CleaningDevice(){
        this.collideable = true;
        this.uuid = UUID.randomUUID();
    }

    CleaningDevice(String name){
        this();
        this.name = name;
    }

    public boolean canCollide(){
        return this.collideable;
    }

    public boolean command(String commandString, HashMap<UUID,Connection> connectionHashMap, HashMap<UUID,Space> spaceHashMap){
        String[] parameter = commandString.replace("[", "").replace("]","").split(",");
        switch (parameter[0]){
            case "tr":
                return this.transport(connectionHashMap, parameter[1] , spaceHashMap);
            case "no":
                return this.moveNorth(Integer.parseInt(parameter[1]),spaceHashMap.get(this.getSpaceID()));
            case "ea":
                return this.moveEast(Integer.parseInt(parameter[1]),spaceHashMap.get(this.getSpaceID()));
            case "so":
                return this.moveSouth(Integer.parseInt(parameter[1]),spaceHashMap.get(this.getSpaceID()));
            case "we":
                return this.moveWest(Integer.parseInt(parameter[1]),spaceHashMap.get(this.getSpaceID()));
            case "en":
                return this.enable(spaceHashMap.get(UUID.fromString(parameter[1])));
            default:
                return false;
        }
    };

    public boolean command(Order order, HashMap<UUID,Connection> connectionHashMap, HashMap<UUID,Space> spaceHashMap){
        switch (order.getOrderType()){
            case TRANSPORT:
                return this.transport(connectionHashMap, order.getGridId().toString(), spaceHashMap);
            case NORTH:
                return this.moveNorth(order.getNumberOfSteps(),spaceHashMap.get(this.getSpaceID()));
            case EAST:
                return this.moveEast(order.getNumberOfSteps(),spaceHashMap.get(this.getSpaceID()));
            case SOUTH:
                return this.moveSouth(order.getNumberOfSteps(),spaceHashMap.get(this.getSpaceID()));
            case WEST:
                return this.moveWest(order.getNumberOfSteps(),spaceHashMap.get(this.getSpaceID()));
            case ENTER:
                return this.enable(spaceHashMap.get(order.getGridId()));
            default:
                return false;
        }
    };






    public boolean transport(HashMap<UUID,Connection> connectionHashMap , String destination , HashMap<UUID,Space> spaceHashMap){
        List<Connection> validconnection = connectionHashMap.entrySet().stream().map(Map.Entry::getValue)
            .filter(x -> x.getDestinationSpaceId().equals(UUID.fromString(destination)))
            .filter(x -> x.getSourceSpaceId().equals(this.getSpaceID()))
            .filter(x -> x.getSourceCoordinate().equals(this.getCleaningDeviceCoordinate()))
            .collect(Collectors.toList());
        if(validconnection.size()>0){
            List<CleaningDevice> otherdevices = spaceHashMap.get(UUID.fromString(destination)).getCleaningDeviceList().stream()
                .filter(x -> x.getCleaningDeviceCoordinate().equals(validconnection.get(0).getDestinationCoordinate()))
                .collect(Collectors.toList());
            if(otherdevices.size()>0)
                return false;
            else{
                this.getSpace().getCleaningDeviceList().remove(this);
                spaceHashMap.get(this.getSpaceID()).getCleaningDeviceList().remove(this);
                spaceHashMap.get(UUID.fromString(destination)).getCleaningDeviceList().add(this);
                this.spaceID = validconnection.get(0).getDestinationSpaceId();
                this.CleaningDeviceCoordinate = new Coordinate(validconnection.get(0).getDestinationCoordinate());
            return true;
            }
        }else return false;
    }

    public boolean moveWest(Integer distanceParameter, Space space){
        if(this.spaceID.compareTo(space.getID()) == 0) {
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isVertical)
                    .filter(obstacle -> obstacle.inYrange(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getID().compareTo(this.getID()) != 0)
                    .filter(cleaningDevice -> cleaningDevice.getCleaningDeviceCoordinate().shareYAxis(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());

            Coordinate tmpCoordinate = new Coordinate(this.getCleaningDeviceCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCleaningDeviceCoordinate());

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
                    if (colideable.getCleaningDeviceCoordinate().equals(calcCoordinate)) {
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
        if(this.spaceID.compareTo(space.getID()) == 0){
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isVertical)
                    .filter(obstacle -> obstacle.inYrange(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getID().compareTo(this.getID()) != 0 )
                    .filter(cleaningDevice -> cleaningDevice.getCleaningDeviceCoordinate().shareYAxis(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());
            Coordinate tmpCoordiante = new Coordinate(this.getCleaningDeviceCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCleaningDeviceCoordinate());
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
                        if(colideable.getCleaningDeviceCoordinate().equals(calcCoordinate)){
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
        if(this.spaceID.compareTo(space.getID()) == 0){
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isHorizontal)
                    .filter(obstacle -> obstacle.inXrange(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getID().compareTo(this.getID()) != 0 )
                    .filter(cleaningDevice -> cleaningDevice.getCleaningDeviceCoordinate().shareXAxis(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());
            Coordinate tmpCoordiante = new Coordinate(this.getCleaningDeviceCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCleaningDeviceCoordinate());
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
                        if(colideable.getCleaningDeviceCoordinate().equals(calcCoordinate)){
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
        if(this.spaceID.compareTo(space.getID()) == 0){
            List<Obstacle> collideableObstacles = space.getObstacles().stream()
                    .filter(Obstacle::isHorizontal)
                    .filter(obstacle -> obstacle.inXrange(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());
            List<CleaningDevice> collideableCleaningDevices = space.getCleaningDeviceList().stream()
                    .filter(cleaningDevice -> cleaningDevice.getID().compareTo(this.getID()) != 0 )
                    .filter(cleaningDevice -> cleaningDevice.getCleaningDeviceCoordinate().shareXAxis(this.getCleaningDeviceCoordinate()))
                    .collect(Collectors.toList());

            Coordinate tmpCoordiante = new Coordinate(this.getCleaningDeviceCoordinate());
            Coordinate calcCoordinate = new Coordinate(this.getCleaningDeviceCoordinate());

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
                        if(colideable.getCleaningDeviceCoordinate().equals(calcCoordinate)){
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
                .filter(cleaningDevice -> cleaningDevice.getCleaningDeviceCoordinate().equals(spawn))
                .count();
        if(colliding > 0) return false;
        else {
            this.spaceID = space.getID();
            this.space = space;
            this.CleaningDeviceCoordinate = spawn;
            space.getCleaningDeviceList().add(this);
            return true;
        }
    }


}

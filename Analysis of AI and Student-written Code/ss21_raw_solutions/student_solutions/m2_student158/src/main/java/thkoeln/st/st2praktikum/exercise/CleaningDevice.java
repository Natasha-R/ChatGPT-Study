package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class CleaningDevice extends AbstractEntity implements DeviceInterface {

    private String name;

    private Coordinate coordinate;

    private SpaceInterface iSpace;

    public CleaningDevice(String name) {
        this.name = name;
    }

    public Boolean executeCommand(Task task){
        switch (task.getTaskType()){
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                move(task);
                System.out.println(coordinate.toString());
                return true;
            case TRANSPORT:
                return transport(task);
            case ENTER:
                UUID spaceId = task.getGridId();
                if(SpaceFactory.getSpaces().get(spaceId)!=null &&
                    SpaceFactory.getSpaces().get(spaceId).isDefaultPositionFree()) {
                    iSpace = SpaceFactory.getSpaces().get(spaceId);
                    place();
                    return true;
                }
                return false;
            default:
                throw new IllegalStateException("Should never be possible");
        }


    }


    public void move(Task task){
        Boolean moveAwayFromDefault = coordinate != null && coordinate.toString().equals(Coordinate.DEFAULT);
        int steps = task.getNumberOfSteps();
        while(steps>0){
            switch (task.getTaskType()){
                case NORTH:
                    if(iSpace.horizontalTileFree(coordinate, 1)){
                        coordinate.incrementY();
                    } else {
                        steps=0;
                    }
                    break;
                case SOUTH:
                    if(iSpace.horizontalTileFree(coordinate, -1)){
                        coordinate.decrementY();
                    } else {
                        steps=0;
                    }
                    break;
                case EAST:
                    if(iSpace.verticalTileFree(coordinate, 1)){
                        coordinate.incrementX();
                    } else {
                        steps=0;
                    }
                    break;
                case WEST:
                    if(iSpace.verticalTileFree(coordinate, -1)){
                        coordinate.decrementX();
                    } else {
                        steps=0;
                    }
                    break;
            }
            steps--;
        }
        if(moveAwayFromDefault){
            iSpace.setDefaultPositionFree(true);
        }
        if(coordinate.toString().equals(Coordinate.DEFAULT)){
            iSpace.setDefaultPositionFree(false);
        }
    }

    private boolean transport(Task task){
        UUID spaceId = task.getGridId();
        for (Connection connection : iSpace.getConnections()){
            if (connection.getDestinationSpaceId().equals(spaceId) && connection.getSourceSpaceCoordinate().equals(getCoordinate())){
                place(connection.getDestinationSpaceCoordinate());
                iSpace = SpaceFactory.getSpaces().get(spaceId);
                return true;
            }
        }
        return false;
    }

    private void place(){
        place("(0,0)");
        iSpace.setDefaultPositionFree(false);
    }

    private void place(String position){
        this.coordinate = new Coordinate(position);
    }

    private void place(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public UUID getSpaceId(){
        return iSpace.getId();
    }

}

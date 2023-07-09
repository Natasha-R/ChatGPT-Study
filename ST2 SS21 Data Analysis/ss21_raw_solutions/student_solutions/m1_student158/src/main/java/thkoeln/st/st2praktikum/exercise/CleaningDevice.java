package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class CleaningDevice extends AbstractEntity implements DeviceInterface{

    private String name;

    private Position position;

    private SpaceInterface iSpace;

    public CleaningDevice(String name) {
        this.name = name;
    }

    public Boolean executeCommand(String[] command){
        switch (command[0]){
            case "no":
            case "so":
            case "we":
            case "ea":
                move(command);
                return true;
            case "tr":
                return transport(command);
            case "en":
                UUID spaceId = UUID.fromString(command[1]);
                if(SpaceFactory.getSpaces().get(spaceId)!=null &&
                    SpaceFactory.getSpaces().get(spaceId).isDefaultPositionFree()) {
                    iSpace = SpaceFactory.getSpaces().get(spaceId);
                    place();
                    return true;
                }
                return false;
            default:
                throw new IllegalArgumentException("Initial command \""+command[0]+"\" is unknown");
        }


    }

    public void move(String[] commandString){
        String direction = commandString[0];
        Integer steps = Integer.parseInt(commandString[1]);
        Boolean moveAwayFromDefault = position.getPositionString().equals(Position.DEFAULT);
        while(steps>0){
            switch (direction){
                case "no":
                    if(iSpace.horizontalTileFree(position, 1)){
                        position.incrementY();
                    } else {
                        steps=0;
                    }
                    break;
                case "so":
                    if(iSpace.horizontalTileFree(position, -1)){
                        position.decrementY();
                    } else {
                        steps=0;
                    }
                    break;
                case "ea":
                    if(iSpace.verticalTileFree(position, 1)){
                        position.incrementX();
                    } else {
                        steps=0;
                    }
                    break;
                case "we":
                    if(iSpace.verticalTileFree(position, -1)){
                        position.decrementX();
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
        if(position.getPositionString().equals(Position.DEFAULT)){
            iSpace.setDefaultPositionFree(false);
        }
    }

    private boolean transport(String[] command){
        UUID spaceId = UUID.fromString(command[1]);
        for (Connection connection : iSpace.getConnections()){
            if (connection.getDestinationSpaceId().equals(spaceId) && connection.getSourceSpaceCoordinate().equals(getPosition().getPositionString())){
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
        this.position = new Position(position);
    }

    public Position getPosition() {
        return position;
    }

    public UUID getSpaceId(){
        return iSpace.getId();
    }

}

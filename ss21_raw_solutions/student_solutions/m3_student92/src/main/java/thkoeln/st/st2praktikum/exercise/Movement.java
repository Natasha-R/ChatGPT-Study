package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Movement {
    public Boolean executeCommand(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        switch (task.getTaskType()) {
            case ENTER:
                return entry(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId);

            case TRANSPORT:
                return transport(maintenanceDroidId, spaceShipDeckRepository, getMaintenanceDroidSpaceShipDeckId, maintenanceDroidRepository);

            case NORTH:
                return moveNorth(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId);

            case EAST:
                return moveEast(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId);

            case SOUTH:
                return moveSouth(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId);

            case WEST:
                return moveWest(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId);

            default:
                throw new UnsupportedOperationException("should not happened!");
        }
    }

    public Boolean entry(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        if (spaceShipDeckRepository.findById(task.getGridId()).get().isFieldFree(0, 0, maintenanceDroidRepository)) {
            maintenanceDroidRepository.findById(maintenanceDroidId).get().setVector2D(new Vector2D(0,0));
            maintenanceDroidRepository.findById(maintenanceDroidId).get().setSpaceShipDeckId(UUID.fromString(task.getGridId().toString()));

            spaceShipDeckRepository.findById(UUID.fromString(task.getGridId().toString())).get().addBlocked(new Vector2D(0,0));

            return true;
        }
        else
           return false;
    }


    public Boolean transport(UUID maintenanceDroidId, SpaceShipDeckRepository spaceShipDeckRepository, UUID getMaintenanceDroidSpaceShipDeckId, MaintenanceDroidRepository maintenanceDroidRepository) {

        for (int i = 0; i < spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().size(); i++) {
            int xPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getY();

            if (xPosition == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getSourceVector2D().getX())) && yPosition == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getSourceVector2D().getY()))) {
                if (spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().isFieldFree(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationVector2D().getX(), spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationVector2D().getY(), maintenanceDroidRepository)) {
                    spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().removeBlocked(new Vector2D(xPosition,yPosition));
                    spaceShipDeckRepository.findById(  getMaintenanceDroidSpaceShipDeckId).get().addBlocked(new Vector2D(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationVector2D().getX(), spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationVector2D().getY()));
                    maintenanceDroidRepository.findById(maintenanceDroidId).get().setVector2D(new Vector2D(Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationVector2D().getX())), Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationVector2D().getY()))));

                    maintenanceDroidRepository.findById(maintenanceDroidId).get().setSpaceShipDeckId(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getConnections().get(i).getDestinationSpaceShipDeckId());

                    return true;
                }
            }
        }
        return false;
    }



    public Boolean moveNorth(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getY();

            if(!spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().isFieldFree(xPosition, yPosition + 1, maintenanceDroidRepository)) {
                return true;
            }

            for (int j = 0; j < spaceShipDeckRepository.findById(maintenanceDroidRepository.findById(maintenanceDroidId).get().getSpaceShipDeckId()).get().getxWalls().size(); j++) {
                if (yPosition + 1 == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().get(j).getStart().getY())) && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().get(j).getStart().getX())) <= xPosition && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().get(j).getEnd().getX())) > xPosition) {
                    return true;
                }
            }

            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().removeBlocked(new Vector2D(xPosition, yPosition));
            maintenanceDroidRepository.findById(maintenanceDroidId).get().setVector2D(new Vector2D(xPosition, yPosition+1));
            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().addBlocked(new Vector2D(xPosition, yPosition+1));
        }
        return true;
    }

    public Boolean moveEast(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getY();

            if (!spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().isFieldFree(xPosition + 1, yPosition, maintenanceDroidRepository)) {
                return true;
            }

            for (int j = 0; j < spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().size(); j++) {
                if (xPosition == spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().get(j).getStart().getX() - 1 && spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().get(j).getStart().getY() <= yPosition && spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().get(j).getEnd().getY() > yPosition)
                    return true;
            }

            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().removeBlocked(new Vector2D(xPosition, yPosition));
            maintenanceDroidRepository.findById(maintenanceDroidId).get().setVector2D(new Vector2D(xPosition+1, yPosition));
            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().addBlocked(new Vector2D(xPosition+1, yPosition));
        }
        return true;
    }

    public Boolean moveSouth(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getY();

            if(!spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().isFieldFree(xPosition, yPosition - 1, maintenanceDroidRepository)) {
                return true;
            }

            for (int j = 0; j < spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().size(); j++) {
                if (yPosition - 1 == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().get(j).getStart().getY())) && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().get(j).getStart().getX())) <= xPosition && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getxWalls().get(j).getEnd().getX())) > xPosition) {
                    return true;
                }
            }

            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().removeBlocked(new Vector2D(xPosition, yPosition));
            maintenanceDroidRepository.findById(maintenanceDroidId).get().setVector2D(new Vector2D(xPosition, yPosition-1));
            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().addBlocked(new Vector2D(xPosition, yPosition-1));
        }
        return true;
    }

    public Boolean moveWest(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findById(maintenanceDroidId).get().getVector2D().getY();

            if (!spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().isFieldFree(xPosition - 1, yPosition, maintenanceDroidRepository)) {
                return true;
            }

            for (int j = 0; j < spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().size(); j++) {
                if (xPosition == spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().get(j).getStart().getX() + 1 && spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().get(j).getStart().getY() <= yPosition && spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().getyWalls().get(j).getEnd().getY() > yPosition)
                    return true;
            }

            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().removeBlocked(new Vector2D(xPosition, yPosition));
            maintenanceDroidRepository.findById(maintenanceDroidId).get().setVector2D(new Vector2D(xPosition-1, yPosition));
            spaceShipDeckRepository.findById(getMaintenanceDroidSpaceShipDeckId).get().addBlocked(new Vector2D(xPosition-1, yPosition));
        }
        return true;
    }
}

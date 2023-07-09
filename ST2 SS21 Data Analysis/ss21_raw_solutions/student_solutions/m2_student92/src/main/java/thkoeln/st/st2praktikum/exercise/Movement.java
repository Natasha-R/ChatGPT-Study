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
        if (spaceShipDeckRepository.findSpaceShipDeckById(task.getGridId().toString()).isFieldFree(0, 0, maintenanceDroidRepository)) {
            maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setVector2D(0 ,0);
            maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setSpaceShipDeckId(UUID.fromString(task.getGridId().toString()));
            return true;
        }
        else
            return false;
    }

    public Boolean transport(UUID maintenanceDroidId, SpaceShipDeckRepository spaceShipDeckRepository, UUID getMaintenanceDroidSpaceShipDeckId, MaintenanceDroidRepository maintenanceDroidRepository) {

        for (int i = 0; i < spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().size(); i++) {
            int xPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getY();

            if (xPosition == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getSourceVector2D().getX())) && yPosition == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getSourceVector2D().getY()))) {
                if (spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString())
                        .isFieldFree(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getDestinationVector2D().getX(), spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getDestinationVector2D().getY(), maintenanceDroidRepository)) {
                    maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setVector2D(Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getDestinationVector2D().getX())), Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getDestinationVector2D().getY())));
                    maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setSpaceShipDeckId(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getConnections().get(i).getDestinationSpaceShipDeckId());
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean moveNorth(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getY();

            if(!spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).isFieldFree(xPosition, yPosition + 1, maintenanceDroidRepository)) {
                return true;
            }
            for (int j = 0; j < spaceShipDeckRepository.findSpaceShipDeckById(maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getSpaceShipDeckId().toString()).getxWalls().size(); j++) {
                if (yPosition + 1 == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().get(j).getStart().getY())) && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().get(j).getStart().getX())) <= xPosition && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().get(j).getEnd().getX())) > xPosition) {
                    return true;
                }
            }
            maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setVector2D(xPosition, yPosition+1);
        }
        return true;
    }

    public Boolean moveEast(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getY();

            if (!spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).isFieldFree(xPosition + 1, yPosition, maintenanceDroidRepository)) {
                return true;
            }
            for (int j = 0; j < spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().size(); j++) {
                if (xPosition == spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().get(j).getStart().getX() - 1 && spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().get(j).getStart().getY() <= yPosition && spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().get(j).getEnd().getY() > yPosition)
                    return true;
            }
            maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setVector2D(xPosition+1, yPosition);
        }
        return true;
    }

    public Boolean moveSouth(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getY();

            if(!spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).isFieldFree(xPosition, yPosition - 1, maintenanceDroidRepository)) {
                return true;
            }
            for (int j = 0; j < spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().size(); j++) {
                if (yPosition - 1 == Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().get(j).getStart().getY())) && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().get(j).getStart().getX())) <= xPosition && Integer.parseInt(String.valueOf(spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getxWalls().get(j).getEnd().getX())) > xPosition) {
                    return true;
                }
            }
            maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setVector2D(xPosition, yPosition-1);
        }
        return true;
    }

    public Boolean moveWest(UUID maintenanceDroidId, Task task, SpaceShipDeckRepository spaceShipDeckRepository, MaintenanceDroidRepository maintenanceDroidRepository, UUID getMaintenanceDroidSpaceShipDeckId) {
        for (int i = 0; i < task.getNumberOfSteps(); i++) {
            int xPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getX();
            int yPosition = maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D().getY();

            if (!spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).isFieldFree(xPosition - 1, yPosition, maintenanceDroidRepository)) {
                return true;
            }
            for (int j = 0; j < spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().size(); j++) {
                if (xPosition == spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().get(j).getStart().getX() + 1 && spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().get(j).getStart().getY() <= yPosition && spaceShipDeckRepository.findSpaceShipDeckById(getMaintenanceDroidSpaceShipDeckId.toString()).getyWalls().get(j).getEnd().getY() > yPosition)
                    return true;
            }
            maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).setVector2D(xPosition-1, yPosition);
        }
        return true;
    }
}
